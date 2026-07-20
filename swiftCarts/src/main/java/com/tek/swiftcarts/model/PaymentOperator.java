package com.tek.swiftcarts.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class PaymentOperator {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false,unique = true)
    private String name;

    private String description;

    @ManyToOne(targetEntity = Users.class,optional = false)
    @JoinColumn(name = "createdBy")
    Users createdBy;

    @ManyToOne(targetEntity = Status.class,optional = false)
    @JoinColumn(name = "status")
    Status status;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "updatedBy")
    Users updatedBy;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;


    private String updatedAt;

}
