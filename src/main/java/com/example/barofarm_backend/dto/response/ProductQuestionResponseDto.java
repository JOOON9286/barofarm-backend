package com.example.barofarm_backend.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductQuestionResponseDto {
    private Long id;
    private Long userId;
    private String username;
    private String title;
    private String content;
    private Boolean isPrivate;
    private Boolean answered;
    private LocalDateTime createdAt;
}