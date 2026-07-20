package com.tek.swiftcarts.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ItemHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private  String id;

    @ManyToOne(targetEntity = Products.class, optional = false)
    @JoinColumn(name = "products")
    Products products;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private int quantityAfter;

    private int quantityBefore;





}
