package com.example.barofarm_backend.dto.response;

import com.example.barofarm_backend.entity.Admin;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminResponseDto {
    private Long id;
    private String username;
    private String email;
    private String role;
    private String createdAt; // 예시

    // Entity -> DTO 변환 생성자
    public AdminResponseDto(Admin admin) {
        this.id = admin.getId();
        this.username = admin.getUsername();
        this.email = admin.getEmail();
        this.role = admin.getRole();
        // 실제로는 생성 시간 포맷팅 로직 추가
        this.createdAt = "2025-01-01T00:00:00Z";
    }
}