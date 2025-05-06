package com.example.barofarm_backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

    private String name;
    private String description;
    private int price;
    private int stockQuantity;
    private String category;
    private String imageUrl;
}
