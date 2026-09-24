
package com.tek.swiftcarts.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class ShiftAssignmentDTO {

    private String id;
    private ShiftDto shift;
    private StaffDto staff;
    private LocalDateTime assignedAt;
    private UsersDTO assignedBy;
    private  StatusDTO status;
}