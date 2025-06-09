package com.example.barofarm_backend.dto.response;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponseDto {
    private Long reviewId;
    private String username;
    private int rating;
    private String comment;
    private List<String> imageUrls;
    private boolean purchased;
    private LocalDateTime createdAt;
}