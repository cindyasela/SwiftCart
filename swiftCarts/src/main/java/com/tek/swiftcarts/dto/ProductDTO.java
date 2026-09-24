package com.tek.swiftcarts.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductDTO {

    private String id;
    private String name;
    private int numberOfProducts;
    private LocalDateTime purchasedOn;
    private LocalDateTime lastRestockedAt;
    private UsersDTO createdBy;
    private UsersDTO updatedBy;
    private String skuNumber;
    private StatusDTO status;
    private ProductCategoriesDTO category;
    private ProductBrandDTO brand;
    private Double buyingPrice;
    private Double sellingPrice;
    private String imageBase64;
    private SupplierDTO supplier;

}
