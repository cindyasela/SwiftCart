package com.tek.swiftcarts.service.impl;

import com.tek.swiftcarts.dto.SupplierDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.exception.AppException;
import com.tek.swiftcarts.exception.ErrorCodes;
import com.tek.swiftcarts.model.ResponseState;
import com.tek.swiftcarts.model.Supplier;
import com.tek.swiftcarts.repository.SupplierRepository;
import com.tek.swiftcarts.service.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public SystemResponse createSupplier(SupplierDTO dto) {

        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new AppException(ErrorCodes.INVALID_REQUEST, "Supplier Name Is Required");
        }

        Supplier supplier = new Supplier();

        supplier.setName(dto.getName());
        supplier.setDescription(dto.getDescription() != null ? dto.getDescription() : "");
        supplier.setPhoneNumber(dto.getPhoneNumber());
        supplier.setEmail(dto.getEmail());

        supplierRepository.save(supplier);

        return new SystemResponse("OK", supplier.getId(), "Supplier added successfully", ResponseState.SUCCESS);
    }

    @Override
    public List<SupplierDTO> findAll() {

        return supplierRepository.findAll()
                .stream()
                .map(s -> new SupplierDTO(s.getId(), s.getName(), s.getDescription(), s.getPhoneNumber(), s.getEmail()))
                .toList();
    }
}
