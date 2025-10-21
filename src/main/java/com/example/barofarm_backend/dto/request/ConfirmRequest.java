package com.example.barofarm_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConfirmRequest(
        @NotBlank String paymentKey,
        @NotBlank String orderId,
        @NotNull Integer amount
) {}
