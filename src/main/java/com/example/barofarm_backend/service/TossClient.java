package com.example.barofarm_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class TossClient {

    private final WebClient tossWebClient;

    // 결제 승인
    public Map<String, Object> confirm(String paymentKey, String orderId, Integer amount) {
        return tossWebClient.post()
                .uri("/payments/confirm")
                .bodyValue(Map.of(
                        "paymentKey", paymentKey,
                        "orderId", orderId,
                        "amount", amount
                ))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }

    // 결제 취소
    public Map<String, Object> cancelByPaymentKey(String paymentKey, String cancelReason) {
        return tossWebClient.post()
                .uri("/payments/{paymentKey}/cancel", paymentKey)
                .bodyValue(Map.of("cancelReason", cancelReason))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }
}
