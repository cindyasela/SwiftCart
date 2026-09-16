package com.tek.swiftcarts.service.impl;

import com.tek.swiftcarts.dto.SupplierRequestDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.exception.AppException;
import com.tek.swiftcarts.exception.ErrorCodes;
import com.tek.swiftcarts.model.*;
import com.tek.swiftcarts.repository.*;
import com.tek.swiftcarts.security.SwiftCartPrincipal;
import com.tek.swiftcarts.service.SupplierRequestService;
import com.tek.swiftcarts.utils.DTOConverters;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class SupplierRequestServiceImpl implements SupplierRequestService {

    private final SupplierRequestRepository requestRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final UsersRepository usersRepository;

    private Users resolveActor(SwiftCartPrincipal principal) {

        return usersRepository.findByKeyCloakId(principal.getKeyCloakId())
                .orElseThrow(() -> new AppException(ErrorCodes.USER_NOT_FOUND));
    }

    @Override
    public SystemResponse createRequest(SupplierRequestDTO dto, SwiftCartPrincipal principal) {

        if (dto.getSupplier() == null || dto.getSupplier().getId() == null) {
            throw new AppException(ErrorCodes.INVALID_REQUEST, "Supplier Is Required");
        }

        if (dto.getQuantity() <= 0) {
            throw new AppException(ErrorCodes.INVALID_REQUEST, "Quantity Must Be Greater Than Zero");
        }

        SupplierRequest request = new SupplierRequest();

        Supplier supplier = supplierRepository.findById(dto.getSupplier().getId())
                .orElseThrow(() -> new AppException(ErrorCodes.RESOURCE_NOT_FOUND, "Supplier Not Found"));
        request.setSupplier(supplier);

        if (dto.getProduct() != null && dto.getProduct().getId() != null) {
            Products product = productRepository.findById(dto.getProduct().getId())
                    .orElseThrow(() -> new AppException(ErrorCodes.RESOURCE_NOT_FOUND, "Product Not Found"));
            request.setProduct(product);
        }

        request.setQuantity(dto.getQuantity());
        request.setNotes(dto.getNotes() != null ? dto.getNotes() : "");
        request.setStatus("PENDING");
        request.setCreatedAt(LocalDateTime.now());
        request.setCreatedBy(resolveActor(principal));

        requestRepository.save(request);

        return new SystemResponse("OK", request.getId(), "Request sent to supplier", ResponseState.SUCCESS);
    }

    @Override
    public List<SupplierRequestDTO> findAll() {

        return requestRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(DTOConverters::convertToDTO)
                .toList();
    }

    @Override
    public List<SupplierRequestDTO> findBySupplier(String supplierId) {

        return requestRepository.findBySupplierIdOrderByCreatedAtDesc(supplierId)
                .stream()
                .map(DTOConverters::convertToDTO)
                .toList();
    }
}
