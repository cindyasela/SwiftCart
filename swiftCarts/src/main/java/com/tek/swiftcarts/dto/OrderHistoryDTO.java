package com.tek.swiftcarts.dto;

import com.tek.swiftcarts.model.HistoryOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
public class OrderHistoryDTO {


    private String id;
    private OrdersDTO orders;
    private String description;
    private String notes;
    private UsersDTO entryBy;
    private LocalDateTime createdAt;
    private HistoryOperation operation;
    private String comment;


    public OrderHistoryDTO() {

    }
}
