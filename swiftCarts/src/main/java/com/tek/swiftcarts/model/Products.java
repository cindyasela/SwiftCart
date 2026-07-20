package com.tek.swiftcarts.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int numberOfProducts;

    @Column(nullable = false)
    private LocalDateTime purchasedOn;

    @ManyToOne(targetEntity = Users.class,optional = false)
    @JoinColumn(name = "createdBy")
    private Users createdBy;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "updatedBy")
    private Users updatedBy;

    @Column(unique = true)
    private String SKUNumber;

    @ManyToOne(targetEntity = Status.class,optional = false)
    @JoinColumn(name = "status" )
     Status status;

    @ManyToOne(targetEntity = ProductCategories.class,optional = false)
    @JoinColumn(name="categories")
    ProductCategories category;

    @ManyToOne(targetEntity = ProductBrand.class)
    @JoinColumn(name = "Brand")
    ProductBrand brand;

    @Column(nullable = false)
    private Double buyingPrice;

    @Column(nullable = false)
    private Double sellingPrice;


}
