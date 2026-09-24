package com.tek.swiftcarts.controller;


import com.tek.swiftcarts.client.SafaricomClient;
import com.tek.swiftcarts.dto.MpesaCallbackDTO;
import com.tek.swiftcarts.dto.MpesaStkPushResponse;
import com.tek.swiftcarts.dto.MpesaTokenResponse;
import com.tek.swiftcarts.security.SwiftCartPrincipal;
import com.tek.swiftcarts.service.MpesaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/mpesa")
@Tag(name = "Mpesa Payment",
        description = "Endpoint to Handle Mpesa Request"
)
@AllArgsConstructor

public class MpesaController {

    private final SafaricomClient safaricomClient;
    private final MpesaService mpesaService;

    @GetMapping("/accessToken")
    public MpesaTokenResponse getAccessToken(){

        return safaricomClient.getAccessToken();

    }

    @PostMapping("/pay")
    public MpesaStkPushResponse initiatePush(@RequestParam String phoneNumber,
    @RequestParam String amount,@RequestParam String accountReference,
    @RequestParam String orderId, SwiftCartPrincipal principal){

        return mpesaService.initiatePush(phoneNumber, amount, accountReference, orderId, principal);
    }

    @PostMapping("/callback")
    public ResponseEntity<Void> mpesaCallback(
            @RequestBody MpesaCallbackDTO callback) {

        System.out.println("=================================");
        System.out.println("M-PESA CALLBACK RECEIVED");
        System.out.println(callback);
        System.out.println("=================================");

        // This used to only log the callback and never actually resolve the
        // payment - Safaricom confirming success on the phone never turned
        // into the Payments row (or the order) moving off PENDING.
        mpesaService.handleCallback(callback);

        return ResponseEntity.ok().build();
    }



}
