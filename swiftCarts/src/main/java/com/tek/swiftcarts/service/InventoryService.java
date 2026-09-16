package com.tek.swiftcarts.service;

import com.tek.swiftcarts.dto.InventoryDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.security.SwiftCartPrincipal;

import java.util.List;

public interface InventoryService {

    SystemResponse createInventory(InventoryDTO dto, SwiftCartPrincipal principal);

    List<InventoryDTO> fetchAllStock();

    SystemResponse deleteInventory(String id);
}
