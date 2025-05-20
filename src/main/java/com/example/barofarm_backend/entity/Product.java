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
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;              // 농산물 이름
    private String description;       // 상세 설명
    private int price;                // 가격
    private int stockQuantity;        // 재고 수량
    private String category;          // 과일, 채소
    private String imageUrl;          // 이미지
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
