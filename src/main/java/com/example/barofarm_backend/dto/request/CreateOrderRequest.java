package com.example.barofarm_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateOrderRequest(
        Long userId,
        @NotBlank String orderName,
        @NotNull Integer amount
) {}
