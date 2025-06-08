package com.example.barofarm_backend.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishlistRequestDto {
    private Long userId;
    private Long productId;
}
