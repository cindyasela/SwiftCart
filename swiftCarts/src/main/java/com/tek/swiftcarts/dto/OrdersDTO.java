package com.tek.swiftcarts.dto;

import com.tek.swiftcarts.model.OrderType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
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
    private OrderType type;
    private PaymentMethodsDTO paymentMethod;
    private PaymentStatusDTO paymentStatus;
    private List<PaymentsDTO> payments;
    private List<OrderItemsDTO> orderItems;
    private BigDecimal totalAmount;
    private BigDecimal subtotal;
    private String comments;





}