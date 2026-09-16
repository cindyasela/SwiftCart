package com.tek.swiftcarts.controller;

import com.tek.swiftcarts.dto.SupplierRequestDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.security.SwiftCartPrincipal;
import com.tek.swiftcarts.service.SupplierRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Supplier Requests", description = "Ask a supplier to bring in more stock - distinct from spend tracking")
@RequestMapping("/api/v1/supplier-requests")
@AllArgsConstructor
public class SupplierRequestController {

    private final SupplierRequestService requestService;

    @Operation(summary = "Request more stock from a supplier", description = "Registers a new stock request against a supplier")
    @PostMapping("/new")
    public ResponseEntity<SystemResponse> createRequest(@RequestBody SupplierRequestDTO dto, SwiftCartPrincipal principal) {

        SystemResponse response = requestService.createRequest(dto, principal);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Fetch all supplier requests", description = "Get every stock request ever sent to a supplier")
    @GetMapping("/all")
    public ResponseEntity<List<SupplierRequestDTO>> findAll() {

        return ResponseEntity.ok(requestService.findAll());
    }

    @Operation(summary = "Fetch requests for one supplier", description = "Get every stock request sent to a specific supplier")
    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<List<SupplierRequestDTO>> findBySupplier(@PathVariable String supplierId) {

        return ResponseEntity.ok(requestService.findBySupplier(supplierId));
    }
}
