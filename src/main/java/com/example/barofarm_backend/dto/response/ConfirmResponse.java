package com.example.barofarm_backend.dto.response;

import java.time.Instant;

public record ConfirmResponse(
        String orderId,
        String method,
        Integer totalAmount,
        Instant approvedAt,
        String receiptUrl
) {}
