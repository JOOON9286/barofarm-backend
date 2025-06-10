package com.example.barofarm_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;              // 농산물 이름
    @Column(columnDefinition = "TEXT")
    private String description;       // 상세 설명 Lob는 긴 텍스트

    private int price;                // 가격
    // String > int로 수정.
    // OrderService 32Line
    private int stockQuantity;        // 재고 수량
    private String category;          // 과일, 채소

    @Column(columnDefinition = "TEXT")
    private String imageUrl;          // 이미지

    @CreatedDate
    private LocalDateTime createdAt;    //생성일
    @LastModifiedDate
    private LocalDateTime updatedAt;    //업뎃일
    private String origin;    // 원산지
    private String salesUnit;                // 판매 단위 (예: 1팩)
    private String weight;                   // 중량 (예: 1~5kg)
    
    //User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")   
    private User user;

    //Product 1대 다 관계
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wishlist> likedUsers = new ArrayList<>();


    // 판매내역 조회 위해서 필요함.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User seller;

}
