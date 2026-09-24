package com.tek.swiftcarts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class InventoryMovementsDTO {

    private String id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String reason;
    private UsersDTO createdBy;
    private UsersDTO updatedBy;
    private StatusDTO statusDTO;



}
