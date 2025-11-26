package com.example.barofarm_backend.dto.response;

public record CreateOrderResponse(
        String orderId,
        Integer amount,
        String paymentUrl
) {}