package com.tek.swiftcarts.service;

import com.tek.swiftcarts.dto.SupplierDTO;
import com.tek.swiftcarts.dto.SystemResponse;

import java.util.List;

public interface SupplierService {

    SystemResponse createSupplier(SupplierDTO dto);

    List<SupplierDTO> findAll();

}
