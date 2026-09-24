
package com.tek.swiftcarts.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "shift_assignment")
    private ShiftAssignment shiftAssignment;

    @ManyToOne(optional = false)
    @JoinColumn(name = "staff")
    private Staff staff;

    @Column(nullable = false)
    private LocalDateTime clockIn;

    private LocalDateTime clockOut;

    @Column(nullable = false)
    private Boolean isPresent = false;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus attendanceStatus;

    private String notes;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private Users approvedBy;

}