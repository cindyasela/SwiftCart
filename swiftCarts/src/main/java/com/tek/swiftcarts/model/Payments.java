package com.tek.swiftcarts.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @ManyToOne(targetEntity = PaymentMethods.class)
    @JoinColumn(name = "PaymentMethods")
    PaymentMethods paymentMethods;

    private String operatorReceipt;

    @Column(updatable = false)
    private String checkoutRequestId;

    @Column(nullable = false)
    private LocalDateTime paidAt;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String notes;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "receivedBy")
    Users receivedBy;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    @Column(nullable = false, unique = true, updatable = false)
    private String transactionId;

}
