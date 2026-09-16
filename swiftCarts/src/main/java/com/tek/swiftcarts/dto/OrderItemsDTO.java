package com.tek.swiftcarts.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class OrderItemsDTO {

    private String id;
    private ProductDTO product;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal total;
    private String notes;
    private LocalDateTime createdAt;

}
