package com.example.barofarm_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String addressName; //배송지명
    private String recipientName;      // 수령인 이름
    private String recipientPhone;     // 수령인 연락처
    private String deliveryRequest;    // 수령 요청사항
    private String zipCode;
    private String streetAddress;   // ex 덕릉로777
    private String detailAddress;   // 101-102호
    private Boolean isDefault;         // 기본 배송지 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
