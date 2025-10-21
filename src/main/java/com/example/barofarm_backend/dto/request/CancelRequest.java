package com.example.barofarm_backend.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CancelRequest(
        @NotBlank String orderId,
        @NotBlank String cancelReason
) {}
