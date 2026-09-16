package com.tek.swiftcarts.service.impl;

import com.tek.swiftcarts.dto.StatusDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.exception.AppException;
import com.tek.swiftcarts.exception.ErrorCodes;
import com.tek.swiftcarts.model.ResponseState;
import com.tek.swiftcarts.model.Status;
import com.tek.swiftcarts.model.Users;
import com.tek.swiftcarts.repository.StatusRepository;
import com.tek.swiftcarts.repository.UsersRepository;
import com.tek.swiftcarts.security.SwiftCartPrincipal;
import com.tek.swiftcarts.service.StatusService;
import com.tek.swiftcarts.utils.DTOConverters;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor

public class StatusServiceImpl implements StatusService {

    private final UsersRepository usersRepository;
    private final StatusRepository statusRepository;


        public SystemResponse registerStatus(StatusDTO dto, SwiftCartPrincipal principal){

        Status status=new Status();

        status.setId(dto.getId());
        status.setName(dto.getName());
        status.setDescription(dto.getDescription());
        status.setCreatedAt(LocalDateTime.now());

        Users user = usersRepository.findByKeyCloakId(principal.getKeyCloakId())
                .orElseThrow(() -> new AppException(ErrorCodes.USER_NOT_FOUND));

        status.setCreatedBy(user);

        statusRepository.save(status);

        return new SystemResponse("200","Status saved","Your Status is saved Successfully", ResponseState.SUCCESS);
    }


    public  List<StatusDTO>fetchAllStatus(){

        List<Status>statusList=statusRepository.findAll();

        return statusList
                .stream()
                .map(DTOConverters::convertToDTO)
                .toList();


    }




}
