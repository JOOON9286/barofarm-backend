package com.example.barofarm_backend.dto.request;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequestDto {
    private Long productId;
    private int rating;
    private String comment;
    private List<String> imageUrls;
}
