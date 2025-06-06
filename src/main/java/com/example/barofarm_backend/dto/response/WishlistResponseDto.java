package com.example.barofarm_backend.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishlistResponseDto {
    private Long id;
    private Long userId;
    private Long productId;
    private LocalDateTime likedAt;

    //찜 등록 상품
    private String productName;     // 상품 이름
    private String producerName;    // 생산자 이름
    private String origin;          // 원산지
    private int price;              // 가격
    private String imageUrl;        // 이미지 URL
}