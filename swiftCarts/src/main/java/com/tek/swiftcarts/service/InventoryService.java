package com.tek.swiftcarts.service;

import com.tek.swiftcarts.dto.InventoryDTO;
import com.tek.swiftcarts.dto.SystemResponse;

import java.util.List;

public interface InventoryService {

    SystemResponse createInventory(InventoryDTO dto);

    List<InventoryDTO> fetchAllStock();
}
