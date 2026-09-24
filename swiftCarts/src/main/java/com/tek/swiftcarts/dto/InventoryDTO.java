package com.tek.swiftcarts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class InventoryDTO {

    private String id;
    private String name;
    private ProductCategoriesDTO productCategories;
    private LocalDateTime createdAt;
    private UsersDTO createdBy;
    private UsersDTO updatedBy;
    private LocalDateTime updatedAt;
    private int quantityInStock;
    private ProductDTO product;
    private String description;
    private StatusDTO status;
    private BigDecimal unitPrice;
    private InventoryMovementsDTO inventoryMovements;


    }

