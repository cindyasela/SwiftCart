package com.tek.swiftcarts.controller;

import com.tek.swiftcarts.dto.RefundsDTO;
import com.tek.swiftcarts.security.SwiftCartPrincipal;
import com.tek.swiftcarts.service.RefundService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Refunds",description = "Endpoints to Process Refunds")
@RequestMapping("/api/v1/refunds")
@AllArgsConstructor
public class RefundsController {

    private final RefundService refundService;

    @Operation(
            summary = "Register new Refund",
            description = "Adds new Refund"
    )
    @PostMapping("/register")
    public ResponseEntity<RefundsDTO>registerNewRefund(@RequestBody RefundsDTO dto, SwiftCartPrincipal principal){

        RefundsDTO refunds=refundService.registerRefund(dto, principal);

        return ResponseEntity.ok(refunds);

    }


    @Operation(
            summary = "Approve a Refund",
            description = "Approve a refund"
    )
    @PostMapping("/approve")
    public ResponseEntity<RefundsDTO>approveRefunds(@RequestBody RefundsDTO dto, SwiftCartPrincipal principal){

        RefundsDTO refunds=refundService.approveRefunds(dto, principal);

        return ResponseEntity.ok(refunds);

    }

    @Operation(
            summary = "Reject Refunds",
            description = "Reject Forwarded Refunds"
    )
    @PostMapping("/reject")
    public ResponseEntity<RefundsDTO>rejectRefunds(@RequestBody RefundsDTO dto, SwiftCartPrincipal principal){

        RefundsDTO refunds=refundService.rejectRefunds(dto, principal);

        return ResponseEntity.ok(refunds);

    }

    @Operation(
            summary = "Process A Refund",
            description = "Process A Refunds"
    )
    @PostMapping("/process")
    public ResponseEntity<RefundsDTO>processRefunds(@RequestBody RefundsDTO dto, SwiftCartPrincipal principal){

        RefundsDTO refundsDTO=refundService.processRefunds(dto, principal);

        return ResponseEntity.ok(refundsDTO);
    }


    @Operation(
            summary = "Fetch Refunds By Status",
            description = "Get Refunds By Status"
    )
    @GetMapping("/status")
    public ResponseEntity<List<RefundsDTO>>fetchRefundsByStatus(RefundsDTO dto){

        List<RefundsDTO> refundsDTO=refundService.fetchRefundsByStatus(dto.getStatus());

        return ResponseEntity.ok(refundsDTO);
    }

    @Operation(
            summary = "Fetch Refunds By Order",
            description = "Get Refunds By Orders"
    )
    @GetMapping("/order")
    public ResponseEntity<List<RefundsDTO>>fetchRefundsByOrder(RefundsDTO dto){

        List<RefundsDTO>refundsDTOS=refundService.fetchRefundsByOrder(dto);

        return ResponseEntity.ok(refundsDTOS);
    }


    @Operation(
            summary = "Fetch ALL Refunds",
            description = "Get All Refunds In The system"
    )
    @GetMapping("/all")
    public ResponseEntity<List<RefundsDTO>>fetchAllRefunds(){

        List<RefundsDTO>refundsDTOS=refundService.fetchAllRefunds();

        return ResponseEntity.ok(refundsDTOS);


    }




}
