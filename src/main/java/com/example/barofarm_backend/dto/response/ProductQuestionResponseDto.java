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
    private String username;
    private String title;
    private String content;
    private boolean isPrivate;
    private boolean answered;
    private LocalDateTime createdAt;
}