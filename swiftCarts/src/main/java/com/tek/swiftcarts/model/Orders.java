package com.tek.swiftcarts.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(targetEntity = Status.class)
    @JoinColumn(name = "status")
    Status status;

    @Column(updatable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "supportedBy")
    Users supportedBy;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "createdBy")
    Users createdBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderType type;

    private LocalDateTime closedAt;


    @ManyToOne(targetEntity = PaymentMethods.class)
    @JoinColumn(name = "paymentMethods")
    PaymentMethods paymentMethod;

    @ManyToOne(targetEntity = PaymentStatus.class)
    @JoinColumn(name = "paymentStatus")
    PaymentStatus paymentStatus;

    @Column(nullable = false)
    private String notes;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Payments.class)
    @JoinTable(
            name = "orderPayments",
            joinColumns = @JoinColumn(name = "Payments"),
            inverseJoinColumns = @JoinColumn(name = "orders")
    )
    List<Payments> payments;



    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL
    )
    private List<OrderItems> orderItems;



    private String comments;

    private BigDecimal totalAmount;

    private BigDecimal subtotal;


}
