package com.aditi_midterm.financemanager.user;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id // @Id → primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // database auto-increments ID
    private Long id;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING) // @Enumerated(EnumType.STRING) → stores enum name
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private Boolean isActive;

    @Column(nullable = false)
    private Instant createdAt; // Instant = UTC time (timezone-safe)

    @Column(nullable = false)
    private Instant updatedAt;

    //   @PrePersist =  Runs before INSERT
//    Sets timestamps automatically, Assigns default role, and Activates.
    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
        if (role == null) role = Role.USER;
        if (isActive == null) isActive = true;
    }

    //  @PreUpdate = Runs before updating field.
//  Keeps updatedAt always correct. No manual update needed in service layer
    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }
}
