package com.tek.swiftcarts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CartsDTO {

    private String id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UsersDTO createdBy;
    private UsersDTO updatedBy;
    private StatusDTO status;
    private List<CartItemsDTO> cartItems;


    public CartsDTO() {

    }
}
