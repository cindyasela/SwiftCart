package com.tek.swiftcarts.dto;

import com.tek.swiftcarts.model.TransactionStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter

public class PaymentsDTO {

    private String id;
    private BigDecimal amount;
    private TransactionStatus status;
    private PaymentMethodsDTO paymentMethods;
    private String operatorReceipt;
    private LocalDateTime paidAt;
    private LocalDateTime createdAt;
    private String notes;
    private UsersDTO receivedBy;
    private OrdersDTO order;
    private String transactionId;
    private String checkoutRequestId;


}
