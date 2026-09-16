package com.tek.swiftcarts.controller;

import com.tek.swiftcarts.dto.SupplierSpendDTO;
import com.tek.swiftcarts.dto.SupplierTransactionDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.security.SwiftCartPrincipal;
import com.tek.swiftcarts.service.SupplierTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Supplier Transactions", description = "Track spend against suppliers")
@RequestMapping("/api/v1/supplier-transactions")
@AllArgsConstructor
public class SupplierTransactionController {

    private final SupplierTransactionService transactionService;

    @Operation(summary = "Record a supplier transaction", description = "Registers a new transaction against a supplier")
    @PostMapping("/new")
    public ResponseEntity<SystemResponse> registerTransaction(@RequestBody SupplierTransactionDTO dto, SwiftCartPrincipal principal) {

        SystemResponse response = transactionService.registerTransaction(dto, principal);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Fetch all supplier transactions", description = "Get every recorded supplier transaction")
    @GetMapping("/all")
    public ResponseEntity<List<SupplierTransactionDTO>> findAll() {

        return ResponseEntity.ok(transactionService.findAll());
    }

    @Operation(summary = "Fetch transactions for one supplier", description = "Get every transaction recorded against a specific supplier")
    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<List<SupplierTransactionDTO>> findBySupplier(@PathVariable String supplierId) {

        return ResponseEntity.ok(transactionService.findBySupplier(supplierId));
    }

    @Operation(summary = "Total spend per supplier", description = "Cumulative transaction amount and count, ranked by spend")
    @GetMapping("/spend-summary")
    public ResponseEntity<List<SupplierSpendDTO>> getSpendPerSupplier() {

        return ResponseEntity.ok(transactionService.getSpendPerSupplier());
    }
}
