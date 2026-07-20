package com.tek.swiftcarts.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DeliveryTypeDTO {

    private String id;
    private String name;
    private String description;
    private StatusDTO status;
    private UsersDTO createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UsersDTO updatedBy;

}
