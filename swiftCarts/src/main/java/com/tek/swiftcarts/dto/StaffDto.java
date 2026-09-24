
package com.tek.swiftcarts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class StaffDto {

    private String id;

    private String firstName;

    private String lastName;

    private LocalDateTime createdAt;

    private StatusDTO status;

    private LocalDateTime updatedAt;

    private UsersDTO createdBy;

    private String keyCloakId;

    private String email;

    private String phoneNumber;

}