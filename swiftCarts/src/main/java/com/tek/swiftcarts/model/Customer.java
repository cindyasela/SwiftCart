package com.tek.swiftcarts.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @ManyToOne(targetEntity = UsersAddress.class)
    @JoinColumn(name = "usersAddress")
    UsersAddress address;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne(targetEntity = Status.class)
    @JoinColumn(name = "status")
    Status status;


    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "users")
    Users createdBy;


    @ManyToOne(targetEntity = Town.class)
    @JoinColumn(name = "town")
    Town town;

    @Column(length = 12,nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Orders.class)
    @JoinTable(
            name = "CustomerOrders",
            joinColumns = @JoinColumn(name = "Customers"),
            inverseJoinColumns = @JoinColumn(name = "Orders")
    )
    List<Orders> orders;

}
