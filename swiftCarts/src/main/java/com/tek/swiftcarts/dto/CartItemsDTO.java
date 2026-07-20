package com.tek.swiftcarts.dto;

import com.tek.swiftcarts.model.CartItemStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CartItemsDTO {

    private String id;
    private ProductDTO product;
    private String productName;
    private String productSku;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private BigDecimal discountAmount;
    private BigDecimal taxAmount;
    private CartsDTO cart;
    private UsersDTO updatedBy;
    private CartItemStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}