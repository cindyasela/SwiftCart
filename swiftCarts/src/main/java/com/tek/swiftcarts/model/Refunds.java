package com.tek.swiftcarts.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Refunds {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(targetEntity = Orders.class)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RefundStatus refundStatus;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime processedAt;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "created_by")
    private Users createdBy;

    @ManyToOne(targetEntity = Status.class)
    @JoinColumn(name = "status")
    private Status status;

    private String notes;


}
