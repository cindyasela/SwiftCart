package com.tek.swiftcarts.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false,unique = true)
    private String name;

    @ManyToOne(targetEntity = ProductCategories.class)
    @JoinColumn(name = "ProductCategories")
    ProductCategories productCategories;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(targetEntity = Users.class,optional = false)
    @JoinColumn(name = "createdBy")
    Users createdBy;

    @ManyToOne(targetEntity = Users.class,optional = false)
    @JoinColumn(name = "updatedBy")
    Users updatedBy;

    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private int quantityInStock;

    @OneToOne(targetEntity = Products.class,optional = false)
    @JoinColumn(name = "Products")
    Products product;

    @Column(nullable = false)
    private String description;

    @ManyToOne(targetEntity = Status.class,optional = false)
    @JoinColumn(name = "Status")
    Status status;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @ManyToOne(targetEntity = InventoryMovements.class,optional = false)
    @JoinColumn(name = "InventoryMovements")
    InventoryMovements inventoryMovement;


}
