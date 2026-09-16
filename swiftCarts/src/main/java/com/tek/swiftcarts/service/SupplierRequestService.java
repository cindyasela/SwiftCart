package com.tek.swiftcarts.service;

import com.tek.swiftcarts.dto.SupplierRequestDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.security.SwiftCartPrincipal;

import java.util.List;

public interface SupplierRequestService {

    SystemResponse createRequest(SupplierRequestDTO dto, SwiftCartPrincipal principal);

    List<SupplierRequestDTO> findAll();

    List<SupplierRequestDTO> findBySupplier(String supplierId);

}
