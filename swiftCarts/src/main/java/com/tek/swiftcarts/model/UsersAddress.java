package com.tek.swiftcarts.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class UsersAddress {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    private String city;

    @Column(nullable = false)
    private String town;

    private String postAddress;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "users")
    Users users;


}
