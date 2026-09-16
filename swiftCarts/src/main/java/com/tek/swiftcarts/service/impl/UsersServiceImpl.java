package com.tek.swiftcarts.service.impl;

import com.tek.swiftcarts.client.SwiftsAuthClient;
import com.tek.swiftcarts.dto.RegisterRequest;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.dto.UsersDTO;
import com.tek.swiftcarts.exception.AppException;
import com.tek.swiftcarts.exception.ErrorCodes;
import com.tek.swiftcarts.model.Roles;
import com.tek.swiftcarts.model.ResponseState;
import com.tek.swiftcarts.model.Status;
import com.tek.swiftcarts.model.Users;
import com.tek.swiftcarts.repository.StatusRepository;
import com.tek.swiftcarts.repository.UsersRepository;
import com.tek.swiftcarts.security.SwiftCartPrincipal;
import com.tek.swiftcarts.service.UsersService;
import com.tek.swiftcarts.utils.DTOConverters;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final StatusRepository statusRepository;
    private final SwiftsAuthClient swiftsAuthClient;

    // Registering a staff member is one action from the caller's point of
    // view, but two systems: create the login identity in Keycloak (via
    // swiftsAuth) first, then save the matching swiftCarts staff row tied to
    // that same id via Users.keyCloakId (unique/not-null) - that's what lets
    // this row later be resolved from a JWT's subject. Does NOT store the
    // password anywhere on this side - Users has no password column since
    // that lives in Keycloak.
    @Override
    public SystemResponse createUser(RegisterRequest dto) {

        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new AppException(ErrorCodes.INVALID_REQUEST, "Password Is Required");
        }

        // The app's login screen only ever asks for email, so that has to be
        // both the Keycloak account's username/email AND this row's email -
        // whatever the person types at login must match exactly.
        String email = (dto.getEmail() != null && !dto.getEmail().isBlank())
                ? dto.getEmail()
                : dto.getUsername();

        if (email == null || email.isBlank()) {
            throw new AppException(ErrorCodes.INVALID_REQUEST, "Email Is Required");
        }

        String keyCloakId = swiftsAuthClient.registerUser(
                dto.getFirstName(), dto.getLastName(), email, dto.getPassword());

        if (keyCloakId == null || keyCloakId.isBlank()) {
            throw new AppException(ErrorCodes.INTERNAL_ERROR, "Keycloak did not return a user id");
        }

        Users user = new Users();

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(email);
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setKeyCloakId(keyCloakId);
        user.setCreatedAt(LocalDateTime.now());

        Status active = statusRepository.findByName("ACTIVE")
                .orElseThrow(() -> new AppException(ErrorCodes.INTERNAL_ERROR, "ACTIVE status not seeded"));
        user.setStatus(active);

        try {
            usersRepository.save(user);
        } catch (RuntimeException e) {
            // The Keycloak account above was already created and swiftsAuth
            // has no delete endpoint to compensate, so this account is now
            // orphaned (can log in, has no swiftCarts staff row). Surface
            // that clearly rather than a generic save failure.
            throw new AppException(ErrorCodes.INTERNAL_ERROR,
                    "Keycloak account " + keyCloakId + " was created but saving the staff record failed: "
                            + e.getMessage());
        }

        return new SystemResponse("OK", user.getId(), "Staff member added successfully", ResponseState.SUCCESS);
    }

    @Override
    public List<UsersDTO> findAll() {

        return usersRepository.findAll()
                .stream()
                .map(DTOConverters::convertToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UsersDTO getCurrentUser(SwiftCartPrincipal principal) {

        Users user = usersRepository.findByKeyCloakId(principal.getKeyCloakId())
                .orElseThrow(() -> new AppException(ErrorCodes.USER_NOT_FOUND));

        UsersDTO dto = DTOConverters.convertToDTO(user);
        dto.setEmail(user.getEmail());
        dto.setRoleIds(user.getRoles().stream().map(Roles::getName).collect(Collectors.toSet()));

        return dto;
    }
}
