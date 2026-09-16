package com.tek.swiftcarts.service.impl;

import com.tek.swiftcarts.dto.ProductCategoriesDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.model.ProductCategories;
import com.tek.swiftcarts.model.ResponseState;
import com.tek.swiftcarts.model.Status;
import com.tek.swiftcarts.model.Users;
import com.tek.swiftcarts.repository.StatusRepository;
import com.tek.swiftcarts.repository.SupplierRepository;
import com.tek.swiftcarts.repository.UsersRepository;
import com.tek.swiftcarts.security.SwiftCartPrincipal;
import com.tek.swiftcarts.service.ProductCategoriesService;
import com.tek.swiftcarts.exception.AppException;
import com.tek.swiftcarts.exception.ErrorCodes;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductCategoriesServiceImpl implements ProductCategoriesService {

     private final StatusRepository statusRepository;
     private final UsersRepository usersRepository;
     private final SupplierRepository supplierRepository;


    public SystemResponse ProductCategoriesDTO(ProductCategoriesDTO dto, SwiftCartPrincipal principal){

        ProductCategories categories=new ProductCategories();

        categories.setId(dto.getId());
        categories.setName(dto.getName());
        categories.setDescription(dto.getDescription());

        categories.setUpdatedAt(LocalDateTime.now());

        Users user = usersRepository.findByKeyCloakId(principal.getKeyCloakId())
                .orElseThrow(() -> new AppException(ErrorCodes.USER_NOT_FOUND));

        categories.setCreatedBy(user);

       Optional <Status>activeStatus=statusRepository.findByName("Active");

        categories.setStatus(activeStatus.get());

        return new SystemResponse("200","Categories is Saved","Your Categories is Saved", ResponseState.SUCCESS);


    }



}
