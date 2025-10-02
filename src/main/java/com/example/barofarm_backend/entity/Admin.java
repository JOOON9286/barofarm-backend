package com.example.barofarm_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password; // 실제로는 암호화 필수

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String role; // SUPER_ADMIN, GENERAL_ADMIN 등

    // 생성자 (Admin 생성 시 사용)
    public Admin(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    // 업데이트 로직 (비밀번호 제외)
    public void update(String username, String email, String role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }
}