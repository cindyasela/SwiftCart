package com.tek.swiftcarts.dto;

import com.tek.swiftcarts.model.CartItems;
import com.tek.swiftcarts.model.OrderItems;
import com.tek.swiftcarts.model.OrderType;
import com.tek.swiftcarts.model.Products;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrdersDTO {

    private String id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime closedAt;
    private String notes;
    private String description;
    private StatusDTO status;
    private UsersDTO createdBy;
    private UsersDTO updatedBy;
    private PaymentMethodsDTO paymentMethod;
    private PaymentStatusDTO paymentStatus;
    private List<PaymentsDTO> payments;
    private String comments;





}