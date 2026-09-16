package com.tek.swiftcarts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierTransactionDTO {

    private String id;
    private SupplierDTO supplier;
    private ProductDTO product;
    private BigDecimal amount;
    private int quantity;
    private String notes;
    private LocalDateTime createdAt;
    private UsersDTO createdBy;

}
