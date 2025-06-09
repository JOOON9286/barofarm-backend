package com.example.barofarm_backend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInfo {
    private Long productId;
    private int quantity;
}