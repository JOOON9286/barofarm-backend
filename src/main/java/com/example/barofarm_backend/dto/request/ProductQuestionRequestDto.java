package com.example.barofarm_backend.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductQuestionRequestDto {
    private Long productId;
    private String title;
    private String content;
    private boolean isPrivate;
}
