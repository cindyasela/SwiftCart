package com.tek.swiftcarts.controller;

import com.tek.swiftcarts.dto.SupplierDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Suppliers", description = "APIs for managing suppliers")
@RequestMapping("/api/v1/suppliers")
@AllArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @Operation(summary = "Register a new supplier", description = "Creates a new supplier")
    @PostMapping("/new")
    public ResponseEntity<SystemResponse> createSupplier(@RequestBody SupplierDTO dto) {

        SystemResponse response = supplierService.createSupplier(dto);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Fetch all suppliers", description = "Get all suppliers in the system")
    @GetMapping("/all")
    public ResponseEntity<List<SupplierDTO>> findAll() {

        List<SupplierDTO> suppliers = supplierService.findAll();

        return ResponseEntity.ok(suppliers);
    }
}
