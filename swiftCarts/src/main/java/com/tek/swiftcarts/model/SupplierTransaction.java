package com.tek.swiftcarts.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * A single transaction (typically a stock purchase) against a supplier -
 * lets the store track cumulative spend per supplier over time. Not tied
 * 1:1 to an Inventory restock entry since a supplier transaction may cover
 * several products or none in particular (e.g. a standing account payment).
 */
@Entity
@Getter
@Setter
public class SupplierTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(targetEntity = Supplier.class, optional = false)
    @JoinColumn(name = "supplier")
    private Supplier supplier;

    @ManyToOne(targetEntity = Products.class)
    @JoinColumn(name = "product")
    private Products product;

    @Column(nullable = false)
    private BigDecimal amount;

    private int quantity;

    private String notes;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(targetEntity = Users.class, optional = false)
    @JoinColumn(name = "createdBy")
    private Users createdBy;

}
