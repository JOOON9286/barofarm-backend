package com.example.barofarm_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "b2b_request")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class B2BRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String position;
    private String businessType;
    private String businessName;
    private String phone;
    private String email;
    private String region1;
    private String region2;
    private String detailAddress;
    private String inquiry;
    private String password;


    private String status;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;

}