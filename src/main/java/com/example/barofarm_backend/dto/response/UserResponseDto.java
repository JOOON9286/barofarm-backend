package com.example.barofarm_backend.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {

    // 마이페이지 정보를 위한 UserResponse
    private String email;
    private String name;
    private String phone;
    private String address;
    private LocalDateTime createdAt;
    private String description;
}
