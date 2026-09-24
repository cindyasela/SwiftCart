package com.tek.swiftcarts.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class WareHouses {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(targetEntity = Town.class,optional = false)
    @JoinColumn(name = "Town")
    Town location;

    private String latitude;

    private String longitude;

    @ManyToOne(targetEntity = Users.class,optional = false)
    @JoinColumn(name = "updatedBy")
    Users updatedBy;

    @ManyToOne(targetEntity = Users.class,optional = false)
    @JoinColumn(name = "createdBy")
    Users createdBy;

    @Column(updatable = false,nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
