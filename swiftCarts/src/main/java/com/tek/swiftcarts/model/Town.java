package com.tek.swiftcarts.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Town {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne(targetEntity = Users.class,optional = false)
    @JoinColumn(name = "createdBy")
    Users createdBy;

    @ManyToOne(targetEntity = Users.class,optional = false)
    @JoinColumn(name = "updatedBy")
    Users updatedBy;

    @ManyToOne(targetEntity = Status.class,optional = false)
    @JoinColumn(name = "Status")
    Status status;

}
