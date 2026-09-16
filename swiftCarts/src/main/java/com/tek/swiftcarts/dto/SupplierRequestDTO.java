package com.tek.swiftcarts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierRequestDTO {

    private String id;
    private SupplierDTO supplier;
    private ProductDTO product;
    private int quantity;
    private String notes;
    private String status;
    private LocalDateTime createdAt;
    private UsersDTO createdBy;

}
