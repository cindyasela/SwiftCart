package com.tek.swiftcarts.controller;

import com.tek.swiftcarts.dto.PaymentsDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.security.SwiftCartPrincipal;
import com.tek.swiftcarts.service.PaymentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Payments", description = "APIs for recording payments against orders")
@RequestMapping("/api/v1/payments")
@AllArgsConstructor
public class PaymentsController {

    private final PaymentsService paymentsService;

    @Operation(summary = "Record a payment", description = "Records a new payment against an order")
    @PostMapping("/new")
    public ResponseEntity<PaymentsDTO> recordPayment(@RequestBody PaymentsDTO dto, SwiftCartPrincipal principal) {

        PaymentsDTO response = paymentsService.recordPayment(dto, principal);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Fetch all payments", description = "Get all recorded payments")
    @GetMapping("/all")
    public ResponseEntity<List<PaymentsDTO>> findAll() {

        List<PaymentsDTO> payments = paymentsService.findAll();

        return ResponseEntity.ok(payments);
    }

    @Operation(summary = "Check payment status with operator", description = "Query M-Pesa to check if a pending payment has been completed and update its status")
    @PostMapping("/{paymentId}/check-status")
    public ResponseEntity<PaymentsDTO> checkPaymentStatus(@PathVariable String paymentId) {

        PaymentsDTO payment = paymentsService.checkPaymentStatusWithOperator(paymentId);

        return ResponseEntity.ok(payment);
    }
}
