package com.tek.swiftcarts.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductMetricsDTO {

    private long totalProducts;
    private long totalProductCategories;
    private long productsInStock;
    private long productsOutOfStock;

    public ProductMetricsDTO(long productsInStock, long productQuantity, long productsOutOfStock, long productsLowStock) {
    }

}
