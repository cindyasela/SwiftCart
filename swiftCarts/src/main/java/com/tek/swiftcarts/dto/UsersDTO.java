package com.tek.swiftcarts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UsersDTO {
    private String id;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
    private StatusDTO statusId;
    private Set<String> roleIds;
    private LocalDateTime updatedAt;
    private UsersDTO createdById;
    private String keyCloakId;
    private String email;
    private CartsDTO cartId;
    private String phoneNumber;


}
