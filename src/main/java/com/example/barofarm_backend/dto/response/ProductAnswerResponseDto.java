package com.example.barofarm_backend.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAnswerResponseDto {
    private Long id;
    private Long questionId;
    private String content;
    private LocalDateTime createdAt;
}