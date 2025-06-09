package com.example.barofarm_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Farmer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(length = 50)
    private String certificationNumber;  // 농가 인증 번호

    @Column(columnDefinition = "TEXT")
    private String description;  // 농부 설명

    @Column(nullable = false)
    private Boolean isVerified = false;

}
