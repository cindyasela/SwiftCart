package com.tek.swiftcarts.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String keyCloakId;

    @CreationTimestamp
    @Column(updatable = false,nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "createdBy")
    Users createdBy;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "updatedBy")
    Users updatedBy;

    @ManyToOne(targetEntity = Roles.class)
    @JoinColumn(name = "Status")
    Status status;

}
