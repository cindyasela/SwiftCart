package com.tek.swiftcarts.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * An ask sent TO a supplier for more stock of a product - distinct from
 * SupplierTransaction, which records money already spent once stock has
 * actually arrived. A request is forward-looking: it exists so the store
 * can track what it's waiting on a supplier for, and later (via automation)
 * so the supplier can actually be notified about it.
 */
@Entity
@Getter
@Setter
public class SupplierRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(targetEntity = Supplier.class, optional = false)
    @JoinColumn(name = "supplier")
    private Supplier supplier;

    @ManyToOne(targetEntity = Products.class)
    @JoinColumn(name = "product")
    private Products product;

    private int quantity;

    private String notes;

    // Plain string rather than a seeded lookup entity - kept intentionally
    // simple (PENDING / FULFILLED / CANCELLED) since this is the first cut
    // of the feature and the automation that will drive it further doesn't
    // exist yet.
    @Column(nullable = false)
    private String status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(targetEntity = Users.class, optional = false)
    @JoinColumn(name = "createdBy")
    private Users createdBy;

}
