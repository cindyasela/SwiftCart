package com.tek.swiftcarts.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String firstName;

    @Column (nullable = false)
    private String LastName;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(targetEntity = Status.class, optional = false)
    @JoinColumn(name = "status")
    Status status;

    private LocalDateTime updatedAt;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "created_by")
    Users createdBy;

    @Column(unique = true,nullable = false)
    private  String keyCloakId;

    @Column(nullable = false)
    private String email;


    @Column(length = 12)
    private String phoneNumber;


}
