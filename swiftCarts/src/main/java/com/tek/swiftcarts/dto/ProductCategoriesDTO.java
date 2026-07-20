package com.tek.swiftcarts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class ProductCategoriesDTO {

    private String id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private UsersDTO createdBy;
    private LocalDateTime updatedAt;
    private Set<String> supplier;
    private StatusDTO status;
}
