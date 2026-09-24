
package com.tek.swiftcarts.dto;

import com.tek.swiftcarts.model.AttendanceStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttendanceDTO {

    private String id;
    private ShiftAssignmentDTO shiftAssignment;
    private StaffDto staff;
    private LocalDateTime clockIn;
    private LocalDateTime clockOut;
    private Boolean isPresent;
    private AttendanceStatus attendanceStatus;
    private String notes;
    private UsersDTO approvedBy;
}