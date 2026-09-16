package com.tek.swiftcarts.service.impl;

import com.tek.swiftcarts.client.SafaricomClient;
import com.tek.swiftcarts.dto.MpesaCallbackDTO;
import com.tek.swiftcarts.dto.MpesaStkPushResponse;
import com.tek.swiftcarts.exception.AppException;
import com.tek.swiftcarts.exception.ErrorCodes;
import com.tek.swiftcarts.model.Orders;
import com.tek.swiftcarts.model.PaymentMethods;
import com.tek.swiftcarts.model.PaymentStatus;
import com.tek.swiftcarts.model.Payments;
import com.tek.swiftcarts.model.Status;
import com.tek.swiftcarts.model.TransactionStatus;
import com.tek.swiftcarts.model.Users;
import com.tek.swiftcarts.repository.OrderRepository;
import com.tek.swiftcarts.repository.PaymentMethodsRepository;
import com.tek.swiftcarts.repository.PaymentsRepository;
import com.tek.swiftcarts.repository.PaymentsStatusRepository;
import com.tek.swiftcarts.repository.StatusRepository;
import com.tek.swiftcarts.repository.UsersRepository;
import com.tek.swiftcarts.security.SwiftCartPrincipal;
import com.tek.swiftcarts.service.MpesaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MpesaServiceImpl implements MpesaService {

    private final SafaricomClient safaricomClient;
    private final PaymentsRepository paymentsRepository;
    private final PaymentMethodsRepository paymentMethodsRepository;
    private final OrderRepository orderRepository;
    private final UsersRepository usersRepository;
    private final PaymentsStatusRepository paymentsStatusRepository;
    private final StatusRepository statusRepository;

    @Override
    public MpesaStkPushResponse initiatePush(String phoneNumber, String amount, String accountReference, String orderId, SwiftCartPrincipal principal) {

        MpesaStkPushResponse response = safaricomClient.initiateStkPush(phoneNumber, amount, accountReference);

        if (response != null && "0".equals(response.getResponseCode())) {
            savePendingPayment(orderId, amount, response.getCheckoutRequestId(), principal);
        }

        return response;
    }

    private void savePendingPayment(String orderId, String amount, String checkoutRequestId, SwiftCartPrincipal principal) {

        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCodes.RESOURCE_NOT_FOUND, "Order Not Found"));

        PaymentMethods mobileMoney = paymentMethodsRepository.findByName("MOBILE MONEY")
                .orElseThrow(() -> new AppException(ErrorCodes.RESOURCE_NOT_FOUND, "Payment Method Not Found"));

        Users receivedBy = usersRepository.findByKeyCloakId(principal.getKeyCloakId())
                .orElseThrow(() -> new AppException(ErrorCodes.USER_NOT_FOUND));

        Payments payment = new Payments();
        payment.setAmount(new BigDecimal(amount));
        payment.setStatus(TransactionStatus.PENDING);
        payment.setPaymentMethods(mobileMoney);
        payment.setNotes("M-Pesa STK push sent. CheckoutRequestID: " + checkoutRequestId);
        payment.setPaidAt(LocalDateTime.now());
        payment.setCreatedAt(LocalDateTime.now());
        payment.setOrder(order);
        payment.setReceivedBy(receivedBy);
        payment.setTransactionId(generateTransactionId());
        payment.setCheckoutRequestId(checkoutRequestId);

        paymentsRepository.save(payment);
    }

    private String generateTransactionId() {
        return "TXN-" + UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
    }

    @Override
    public void handleCallback(MpesaCallbackDTO callback) {

        if (callback == null || callback.getBody() == null || callback.getBody().getStkCallback() == null) {
            return;
        }

        MpesaCallbackDTO.StkCallback stkCallback = callback.getBody().getStkCallback();

        String checkoutRequestId = stkCallback.getCheckoutRequestId();
        if (checkoutRequestId == null) {
            return;
        }

        Optional<Payments> paymentOpt = paymentsRepository.findByCheckoutRequestId(checkoutRequestId);
        if (paymentOpt.isEmpty()) {
            return;
        }



        Payments payment = paymentOpt.get();

        // Already resolved by an earlier callback delivery — Safaricom can retry.
        if (payment.getStatus() != TransactionStatus.PENDING) {
            return;
        }

        boolean success = stkCallback.getResultCode() != null && stkCallback.getResultCode() == 0;

        if (success) {
            String receiptNumber = extractMetadataValue(stkCallback.getCallbackMetadata(), "MpesaReceiptNumber");
            payment.setStatus(TransactionStatus.PAID);
            payment.setOperatorReceipt(receiptNumber);
            payment.setPaidAt(LocalDateTime.now());

            // Same gap PaymentsServiceImpl.recordPayment had: resolving the
            // Payments row on its own never touched the order it belongs to,
            // so a successfully paid M-Pesa order still showed PENDING
            // everywhere else in the app.
            Orders order = payment.getOrder();
            if (order != null) {
                Optional<PaymentStatus> paidStatus = paymentsStatusRepository.findByName("PAID");
                paidStatus.ifPresent(order::setPaymentStatus);

                Optional<Status> completedStatus = statusRepository.findByName("Completed");
                completedStatus.ifPresent(order::setStatus);

                orderRepository.save(order);
            }
        } else {
            payment.setStatus(TransactionStatus.FAILED);
            payment.setNotes(payment.getNotes() + " | Failed: " + stkCallback.getResultDesc());
        }

        paymentsRepository.save(payment);
    }

    private String extractMetadataValue(MpesaCallbackDTO.CallbackMetadata metadata, String name) {
        if (metadata == null || metadata.getItem() == null) {
            return null;
        }

        List<MpesaCallbackDTO.CallbackItem> items = metadata.getItem();

        for (MpesaCallbackDTO.CallbackItem item : items) {
            if (name.equals(item.getName())) {
                return item.getValue() == null ? null : item.getValue().toString();
            }
        }

        return null;
    }
}
