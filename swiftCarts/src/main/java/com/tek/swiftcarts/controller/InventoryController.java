package com.tek.swiftcarts.controller;

import com.tek.swiftcarts.dto.InventoryDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@AllArgsConstructor
@Tag(
        name = "Inventory Management",
        description = "APIs for managing product inventory"
)
public class InventoryController {

    private final InventoryService inventoryService;


    @Operation(
            summary = "Register new stock",
            description = "Adds new inventory stock into the system"
    )
    @PostMapping("register")
    public ResponseEntity<SystemResponse>addNewStock(@RequestBody InventoryDTO dto){
        SystemResponse inventoryDTO=inventoryService.createInventory(dto);
        return  ResponseEntity.ok(inventoryDTO);

    }

    @Operation(
            summary = "Fetch All Inventory Stock",
            description = "Fetch all inventory stock into the system"
    )
    @GetMapping("all")
    public ResponseEntity<List<InventoryDTO>> fetchStock(){
        List<InventoryDTO> dto=inventoryService.fetchAllStock();
        return ResponseEntity.ok(dto);

    }



}
