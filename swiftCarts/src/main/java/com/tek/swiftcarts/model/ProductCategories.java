package com.tek.swiftcarts.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class ProductCategories {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    private String description;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn( nullable = false)
    private Users createdBy;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Supplier.class)
    @JoinTable(
            name = "ProductCategories-Supplier",
            joinColumns = @JoinColumn(name = "ProductCategories"),
            inverseJoinColumns = @JoinColumn(name = "Supplier")
    )
    private Set<Supplier> supplier= new HashSet<>();

    @ManyToOne(targetEntity = Status.class)
    @JoinColumn(name = "status")
    Status status;


}
