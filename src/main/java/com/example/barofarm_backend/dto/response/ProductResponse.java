package com.example.barofarm_backend.dto.response;

import com.example.barofarm_backend.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private Long productId;           // 상품 ID
    private String productName;              // 농산물 이름
    private String description;       // 상세 설명
    private int price;                // 가격
    private String stockQuantity;        // 재고 수량
    private String category;          // 과일, 채소 등
    private String imageUrl;          // 이미지 URL
    private String origin;            // 원산지
    private String salesUnit;         // 판매 단위 (예: 1팩)
    private String weight;            // 중량 정보 (예: 1~5kg)
    private String createdAt;         // 생성일 (String 형태로 반환)
    private String updatedAt;         // 수정일 (String 형태로 반환)
    private Long userId;    // 등록 농부 id
}
