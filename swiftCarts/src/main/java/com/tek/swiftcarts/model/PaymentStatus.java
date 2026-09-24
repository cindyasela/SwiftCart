package com.tek.swiftcarts.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class PaymentStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private  String id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false,length = 300)
    private String description;

}
