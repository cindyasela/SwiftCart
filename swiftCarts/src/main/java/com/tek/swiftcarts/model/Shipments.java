package com.tek.swiftcarts.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@AllArgsConstructor
public class Shipments {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(targetEntity = Orders.class)
    @JoinColumn(name = "orders")
    Orders orders;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShipmentStatus shipmentStatus;

    @ManyToOne(targetEntity = DeliveryType.class)
    @JoinColumn(name = "deliveryType",nullable = false)
    DeliveryType deliveryType;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "entryBy",nullable = false)
    Users entryBy;

    @Column(nullable = false)
    private String notes;

    @ManyToOne(targetEntity = Status.class)
    @JoinColumn(name = "status")
    Status status;

    private LocalDateTime dispatchedAt;

    private String reason;

    private String deliveryNo;


    public Shipments() {

    }
}
