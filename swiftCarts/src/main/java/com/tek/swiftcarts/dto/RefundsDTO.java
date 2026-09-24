package com.tek.swiftcarts.dto;

import com.tek.swiftcarts.model.RefundStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RefundsDTO {

    private String id;
    private OrdersDTO order;
    private Double amount;
    private String reason;
    private RefundStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;
    private UsersDTO createdBy;
    private String notes;


}
