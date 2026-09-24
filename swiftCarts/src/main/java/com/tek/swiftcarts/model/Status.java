package com.tek.swiftcarts.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    // Nullable: breaks the Users<->Status circular NOT NULL dependency (Users.status
    // is required, so the first Status rows must be seedable before any Users exist).
    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "created_by")
    Users createdBy;

    @Column (nullable = false)
    private String  description;

}
