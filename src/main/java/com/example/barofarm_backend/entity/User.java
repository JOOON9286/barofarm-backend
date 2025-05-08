package com.example.barofarm_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String name;

    @Column
    private String nickname;

    private String phone;

    @Column(length = 500)
    private String refreshToken;

    @Column(nullable = false)
    private String role = "ROLE_USER";

}