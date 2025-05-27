package com.example.barofarm_backend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateRequest {
    private String name;
    private String description;
    private int price;
    private int stockQuantity;
    private String category;
    private String imageUrl;
}