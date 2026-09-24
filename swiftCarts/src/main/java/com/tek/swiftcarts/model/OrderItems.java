package com.tek.swiftcarts.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @ManyToOne(targetEntity = Orders.class, optional = false)
    @JoinColumn(name = "order_id")
    private Orders order;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private String notes;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private BigDecimal total;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @ManyToOne(targetEntity = Users.class,optional = false)
    @JoinColumn(name = "createdBy")
    Users createdBy;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "updatedBy")
    Users updatedBy;

    @ManyToOne(targetEntity = Status.class)
    @JoinColumn(name = "status")
    Status status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product")
    private Products product;



}
