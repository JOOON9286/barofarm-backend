package com.example.barofarm_backend.dto.response;

import com.example.barofarm_backend.entity.Product;
import lombok.Getter;

@Getter
public class ProductResponse {
    private final Long id;
    private final String name;
    private final String description;
    private final int price;
    private final int stockQuantity;
    private final String category;
    private final String imageUrl;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.stockQuantity = product.getStockQuantity();
        this.category = product.getCategory();
        this.imageUrl = product.getImageUrl();
    }
}