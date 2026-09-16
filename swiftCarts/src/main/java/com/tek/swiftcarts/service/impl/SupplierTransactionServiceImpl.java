package com.tek.swiftcarts.service.impl;

import com.tek.swiftcarts.dto.SupplierSpendDTO;
import com.tek.swiftcarts.dto.SupplierTransactionDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.exception.AppException;
import com.tek.swiftcarts.exception.ErrorCodes;
import com.tek.swiftcarts.model.*;
import com.tek.swiftcarts.repository.*;
import com.tek.swiftcarts.security.SwiftCartPrincipal;
import com.tek.swiftcarts.service.SupplierTransactionService;
import com.tek.swiftcarts.utils.DTOConverters;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class SupplierTransactionServiceImpl implements SupplierTransactionService {

    private final SupplierTransactionRepository transactionRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final UsersRepository usersRepository;

    private Users resolveActor(SwiftCartPrincipal principal) {

        return usersRepository.findByKeyCloakId(principal.getKeyCloakId())
                .orElseThrow(() -> new AppException(ErrorCodes.USER_NOT_FOUND));
    }

    @Override
    public SystemResponse registerTransaction(SupplierTransactionDTO dto, SwiftCartPrincipal principal) {

        if (dto.getSupplier() == null || dto.getSupplier().getId() == null) {
            throw new AppException(ErrorCodes.INVALID_REQUEST, "Supplier Is Required");
        }

        if (dto.getAmount() == null) {
            throw new AppException(ErrorCodes.INVALID_REQUEST, "Amount Is Required");
        }

        SupplierTransaction transaction = new SupplierTransaction();

        Supplier supplier = supplierRepository.findById(dto.getSupplier().getId())
                .orElseThrow(() -> new AppException(ErrorCodes.RESOURCE_NOT_FOUND, "Supplier Not Found"));
        transaction.setSupplier(supplier);

        if (dto.getProduct() != null && dto.getProduct().getId() != null) {
            Products product = productRepository.findById(dto.getProduct().getId())
                    .orElseThrow(() -> new AppException(ErrorCodes.RESOURCE_NOT_FOUND, "Product Not Found"));
            transaction.setProduct(product);
        }

        transaction.setAmount(dto.getAmount());
        transaction.setQuantity(dto.getQuantity());
        transaction.setNotes(dto.getNotes() != null ? dto.getNotes() : "");
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setCreatedBy(resolveActor(principal));

        transactionRepository.save(transaction);

        return new SystemResponse("OK", transaction.getId(), "Supplier transaction recorded", ResponseState.SUCCESS);
    }

    @Override
    public List<SupplierTransactionDTO> findAll() {

        return transactionRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(DTOConverters::convertToDTO)
                .toList();
    }

    @Override
    public List<SupplierTransactionDTO> findBySupplier(String supplierId) {

        return transactionRepository.findBySupplierIdOrderByCreatedAtDesc(supplierId)
                .stream()
                .map(DTOConverters::convertToDTO)
                .toList();
    }

    @Override
    public List<SupplierSpendDTO> getSpendPerSupplier() {

        return transactionRepository.findSpendPerSupplier()
                .stream()
                .map(row -> new SupplierSpendDTO(
                        row.getSupplierId(),
                        row.getSupplierName(),
                        row.getTotalSpent(),
                        row.getTransactionCount()))
                .toList();
    }
}
