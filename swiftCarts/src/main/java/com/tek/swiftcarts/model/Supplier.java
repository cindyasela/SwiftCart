package com.tek.swiftcarts.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private  String id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false,length = 300)
    private String description;

    // Contact details - not used for anything yet, but this is exactly what
    // a future "notify supplier when a product goes out of stock" feature
    // would need, so it's captured here from the start.
    private String phoneNumber;

    private String email;
}
