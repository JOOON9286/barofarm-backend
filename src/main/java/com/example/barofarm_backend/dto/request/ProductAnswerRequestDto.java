package com.example.barofarm_backend.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAnswerRequestDto {
    private Long questionId;
    private String content;
}