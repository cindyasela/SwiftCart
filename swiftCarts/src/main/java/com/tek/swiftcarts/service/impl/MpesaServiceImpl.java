package com.tek.swiftcarts.service.impl;

import com.tek.swiftcarts.client.SafaricomClient;
import com.tek.swiftcarts.dto.MpesaCallbackDTO;
import com.tek.swiftcarts.dto.MpesaStkPushResponse;
import com.tek.swiftcarts.dto.PaymentsDTO;
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
import com.tek.swiftcarts.utils.DTOConverters;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
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

    private String generateTransactionId() {
        return "TXN-" + UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
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
      throw  new AppException(ErrorCodes.INTERNAL_ERROR) ;       }

        Payments payment = paymentOpt.get();

        if (payment.getStatus() != TransactionStatus.PENDING) {
            return;
        }

        boolean success = stkCallback.getResultCode() != null && stkCallback.getResultCode() == 0;

        if (success) {
            String receiptNumber = extractMetadataValue(stkCallback.getCallbackMetadata(), "MpesaReceiptNumber");
            payment.setStatus(TransactionStatus.PAID);
            payment.setOperatorReceipt(receiptNumber);
            payment.setPaidAt(LocalDateTime.now());


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

    @Override
    public PaymentsDTO checkPaymentStatus(String paymentId) {
        Payments payment = paymentsRepository.findById(paymentId)
                .orElseThrow(() -> new AppException(ErrorCodes.RESOURCE_NOT_FOUND, "Payment not found"));

        // If payment is already finalized (PAID/FAILED), no need to check with operator
        if (payment.getStatus() != TransactionStatus.PENDING) {
            return DTOConverters.convertToDTO(payment);
        }

        // Query M-Pesa for status if payment is still pending
        if (payment.getCheckoutRequestId() != null) {
            try {
                String statusResponse = safaricomClient.queryTransactionStatus(payment.getCheckoutRequestId());

                // Parse response and update payment status if needed
                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                com.fasterxml.jackson.databind.JsonNode responseNode = mapper.readTree(statusResponse);

                int resultCode = responseNode.get("ResultCode").asInt(-1);

                if (resultCode == 0) {
                    // Payment successful
                    payment.setStatus(TransactionStatus.PAID);
                    payment.setPaidAt(LocalDateTime.now());

                    // Update order status to PAID
                    Orders order = payment.getOrder();
                    if (order != null) {
                        Optional<PaymentStatus> paidStatus = paymentsStatusRepository.findByName("PAID");
                        paidStatus.ifPresent(order::setPaymentStatus);

                        Optional<Status> completedStatus = statusRepository.findByName("Completed");
                        completedStatus.ifPresent(order::setStatus);

                        orderRepository.save(order);
                    }

                    paymentsRepository.save(payment);
                } else if (resultCode != 0) {
                    // Payment failed
                    payment.setStatus(TransactionStatus.FAILED);
                    String resultDesc = responseNode.has("ResultDesc") ?
                            responseNode.get("ResultDesc").asText("Unknown error") : "Payment failed";
                    payment.setNotes((payment.getNotes() != null ? payment.getNotes() + " | " : "") + "Operator status check: " + resultDesc);

                    paymentsRepository.save(payment);
                }
            } catch (Exception e) {
                throw new AppException(ErrorCodes.INTERNAL_ERROR, "Failed to check payment status with operator: " + e.getMessage());
            }
        }

        return DTOConverters.convertToDTO(payment);
    }

    @Scheduled(fixedDelay = 30000)
    public void pollPendingPayments() {
        try {
            List<Payments> pendingPayments = paymentsRepository.findByStatus(TransactionStatus.PENDING);

            for (Payments payment : pendingPayments) {
                if (payment.getCheckoutRequestId() == null) {
                    continue;
                }

                try {
                    String statusResponse = safaricomClient.queryTransactionStatus(payment.getCheckoutRequestId());
                    com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                    com.fasterxml.jackson.databind.JsonNode responseNode = mapper.readTree(statusResponse);

                    int resultCode = responseNode.get("ResultCode").asInt(-1);

                    if (resultCode == 0) {
                        payment.setStatus(TransactionStatus.PAID);
                        payment.setPaidAt(LocalDateTime.now());

                        Orders order = payment.getOrder();
                        if (order != null) {
                            Optional<PaymentStatus> paidStatus = paymentsStatusRepository.findByName("PAID");
                            paidStatus.ifPresent(order::setPaymentStatus);

                            Optional<Status> completedStatus = statusRepository.findByName("Completed");
                            completedStatus.ifPresent(order::setStatus);

                            orderRepository.save(order);
                        }

                        paymentsRepository.save(payment);
                        log.info("Payment {} auto-updated to PAID from M-Pesa poll", payment.getId());
                    } else if (resultCode != 0) {
                        payment.setStatus(TransactionStatus.FAILED);
                        String resultDesc = responseNode.has("ResultDesc") ?
                                responseNode.get("ResultDesc").asText("Unknown error") : "Payment failed";
                        payment.setNotes((payment.getNotes() != null ? payment.getNotes() + " | " : "") + "Poll check: " + resultDesc);
                        paymentsRepository.save(payment);
                        log.info("Payment {} auto-updated to FAILED from M-Pesa poll", payment.getId());
                    }
                } catch (Exception e) {
                    log.warn("Error polling status for payment {}: {}", payment.getId(), e.getMessage());
                }
            }
        } catch (Exception e) {
            log.error("Error in pollPendingPayments scheduled task", e);
        }
    }
}
