package com.tek.swiftcarts.service;

import com.tek.swiftcarts.dto.MpesaCallbackDTO;
import com.tek.swiftcarts.dto.MpesaStkPushResponse;
import com.tek.swiftcarts.dto.PaymentsDTO;
import com.tek.swiftcarts.security.SwiftCartPrincipal;

public interface MpesaService {

    MpesaStkPushResponse initiatePush(String phoneNumber, String amount, String accountReference, String orderId, SwiftCartPrincipal principal);

    void handleCallback(MpesaCallbackDTO callback);

    PaymentsDTO checkPaymentStatus(String paymentId);

}
