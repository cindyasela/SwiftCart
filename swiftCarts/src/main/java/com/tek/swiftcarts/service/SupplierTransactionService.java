package com.tek.swiftcarts.service;

import com.tek.swiftcarts.dto.SupplierSpendDTO;
import com.tek.swiftcarts.dto.SupplierTransactionDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.security.SwiftCartPrincipal;

import java.util.List;

public interface SupplierTransactionService {

    SystemResponse registerTransaction(SupplierTransactionDTO dto, SwiftCartPrincipal principal);

    List<SupplierTransactionDTO> findAll();

    List<SupplierTransactionDTO> findBySupplier(String supplierId);

    List<SupplierSpendDTO> getSpendPerSupplier();

}
