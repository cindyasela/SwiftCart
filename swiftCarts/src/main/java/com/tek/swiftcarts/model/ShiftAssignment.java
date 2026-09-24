
package com.tek.swiftcarts.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class ShiftAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "shift")
    private Shift shift;

    @ManyToOne(optional = false)
    @JoinColumn(name = "staff")
    private Staff staff;

    private LocalDateTime assignedAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "assignedBy")
    private Users assignedBy;


    @ManyToOne(optional = false)
    @JoinColumn(name = "status")
    private Status status;
}