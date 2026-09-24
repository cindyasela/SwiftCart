package com.tek.swiftcarts.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(targetEntity = Orders.class)
    @JoinColumn(name = "orders")
    Orders orders;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String notes;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "users")
    Users entryBy;


    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HistoryOperation operation;

    private String comment;


}
