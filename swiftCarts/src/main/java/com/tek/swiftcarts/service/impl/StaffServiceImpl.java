package com.tek.swiftcarts.service.impl;

import com.tek.swiftcarts.dto.StaffDto;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.exception.AppException;
import com.tek.swiftcarts.exception.ErrorCodes;
import com.tek.swiftcarts.model.ResponseState;
import com.tek.swiftcarts.model.Staff;
import com.tek.swiftcarts.model.Status;
import com.tek.swiftcarts.model.Users;
import com.tek.swiftcarts.repository.StaffRepository;
import com.tek.swiftcarts.repository.StatusRepository;
import com.tek.swiftcarts.repository.UsersRepository;
import com.tek.swiftcarts.security.SwiftCartPrincipal;
import com.tek.swiftcarts.service.StaffService;
import com.tek.swiftcarts.utils.DTOConverters;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final UsersRepository usersRepository;
    private final StatusRepository statusRepository;
    private  final StaffRepository staffRepository;



    public SystemResponse registerStaffMembers(StaffDto dto, SwiftCartPrincipal principal){

        Staff staff=new Staff();

        staff.setFirstName(dto.getFirstName());

        Optional <Users>users=usersRepository.findByKeyCloakId(principal.getKeyCloakId());

         if (users.isEmpty()) {
             throw new AppException(ErrorCodes.USER_NOT_FOUND);}

        staff.setCreatedBy(users.get());

         staff.setEmail(dto.getEmail());

         staff.setCreatedAt(LocalDateTime.now());

         staff.setLastName(dto.getLastName());

         staff.setPhoneNumber(dto.getPhoneNumber());

         Optional <Status>status=statusRepository.findByName("ACTIVE");

         if(status.isEmpty()) throw new AppException(ErrorCodes.RESOURCE_NOT_FOUND);

         staff.setStatus(status.get());

         return new SystemResponse("Staff Uploaded Successfully","Success","200", ResponseState.SUCCESS);

    }

    @Override
    public List <StaffDto> fetchAllStaffMembers(){

        List<Staff>members=staffRepository.findAll();

        return members.stream()
                .map(DTOConverters::convertToDTO)
                .toList();

    }




}
