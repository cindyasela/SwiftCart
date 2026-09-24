package com.tek.swiftcarts.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class DeliveryType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne(targetEntity = Status.class)
    @JoinColumn(name = "status")
    Status status;

    @ManyToOne(targetEntity = Users.class,optional = false)
    @JoinColumn(name = "createdBy")
    Users createdBy;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "updatedBy")
    Users updatedBy;

}
