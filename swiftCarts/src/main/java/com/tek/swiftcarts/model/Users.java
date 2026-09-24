package com.tek.swiftcarts.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String firstName;

    @Column (nullable = false)
    private String LastName;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(targetEntity = Status.class, optional = false)
    @JoinColumn(name = "status")
    Status status;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Roles.class)
    @JoinTable(
            name = "Users-Roles",
            joinColumns = @JoinColumn(name = "users"),
            inverseJoinColumns = @JoinColumn(name = "roles")
    )
    private Set<Roles> roles= new HashSet<>();

    private LocalDateTime updatedAt;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "created_by")
    Users createdBy;

    @Column(unique = true,nullable = false)
    private  String keyCloakId;

    @Column(nullable = false)
    private String email;



    @Column(length = 12)
    private String phoneNumber;


}
