package com.example.barofarm_backend.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderResponse {
    private Long productId;
    private String productName;
    private int quantity;
    private int price;
    private int totalPrice;
}