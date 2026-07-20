package com.tek.swiftcarts.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductBrandDTO {

    private String id;
    private String name;
    private String description;
    private String shortDescription;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UsersDTO createdBy;
    private UsersDTO updatedBy;


}
