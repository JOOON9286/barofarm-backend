// 📁 admin/dto/AdminFarmerDto.java (수정)
package com.example.barofarm_backend.admin.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminFarmerDto {
    private Long id; // User ID
    private String name; // User 이름
    private String email; // User 이메일
    private String phone; // User 연락처
    private String role; // User 역할 (ROLE_FARMER)

    // ⭐️ Farmer 엔티티 정보
    private Long farmerId; // Farmer 엔티티의 ID
    private String certificationNumber; // 농가 인증 번호
    private String description; // 농부 설명
    private boolean isVerified; // 승인 여부 (핵심)
}