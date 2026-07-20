package com.tek.swiftcarts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderProccessingDTO {

    private String id;
    private OrdersDTO order;
    private StatusDTO status;
    private String remarks;
    private String comments;
    private UsersDTO assignedTo;
    private UsersDTO processedBy;
    private UsersDTO createdBy;
    private UsersDTO updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime processedAt;
    private LocalDateTime completedAt;
    private PaymentStatusDTO paymentStatus;
    private PaymentMethodsDTO paymentMethod;
    private OrderHistoryDTO historyDTO;
}