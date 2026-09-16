package com.tek.swiftcarts.service.impl;

import com.tek.swiftcarts.dto.DeliveryTypeDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.exception.AppException;
import com.tek.swiftcarts.exception.ErrorCodes;
import com.tek.swiftcarts.model.DeliveryType;
import com.tek.swiftcarts.model.ResponseState;
import com.tek.swiftcarts.model.Status;
import com.tek.swiftcarts.model.Users;
import com.tek.swiftcarts.repository.DeliveryTypeRepository;
import com.tek.swiftcarts.repository.StatusRepository;
import com.tek.swiftcarts.repository.UsersRepository;
import com.tek.swiftcarts.security.SwiftCartPrincipal;
import com.tek.swiftcarts.service.DeliveryTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DeliveryTypeServiceImpl implements DeliveryTypeService {

    private final UsersRepository usersRepository;
    private final StatusRepository statusRepository;
    private final DeliveryTypeRepository typeRepository;

    public SystemResponse registerDeliveryType(DeliveryTypeDTO dto, SwiftCartPrincipal principal){

        DeliveryType type =new DeliveryType();

        type.setId(dto.getId());
        type.setName(dto.getName());
        type.setCreatedAt(LocalDateTime.now());
        type.setDescription(dto.getDescription());
        type.setUpdatedAt(LocalDateTime.now());

        Users user = usersRepository.findByKeyCloakId(principal.getKeyCloakId())
                .orElseThrow(() -> new AppException(ErrorCodes.USER_NOT_FOUND));

        type.setCreatedBy(user);

        type.setUpdatedBy(user);

        Optional<Status>active=statusRepository.findByName("ACTIVE");

        if (active.isEmpty()){throw new AppException(ErrorCodes.RESOURCE_NOT_FOUND,"Status is Empty");
        }

        type.setStatus(active.get());


        typeRepository.save(type);


        return new SystemResponse("Done","Delivery is SuccessFully","DeliveryType is Successfully Created", ResponseState.SUCCESS);
    }





}
