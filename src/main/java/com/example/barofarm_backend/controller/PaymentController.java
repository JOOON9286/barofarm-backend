package com.example.barofarm_backend.controller;

import com.example.barofarm_backend.dto.request.CancelRequest;
import com.example.barofarm_backend.dto.request.ConfirmRequest;
import com.example.barofarm_backend.dto.request.CreateOrderRequest;
import com.example.barofarm_backend.dto.response.ConfirmResponse;
import com.example.barofarm_backend.dto.response.CreateOrderResponse;
import com.example.barofarm_backend.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /** 1) 주문 생성 (결제 페이지 진입 시) */
    @PostMapping("/orders")
    public CreateOrderResponse createOrder(@Valid @RequestBody CreateOrderRequest req) {
        return paymentService.createOrder(req);
    }

    /** 2) 결제 승인 (successUrl 이후 프론트 → 서버) */
    @PostMapping("/payments/confirm")
    public ConfirmResponse confirm(@Valid @RequestBody ConfirmRequest req) {
        return paymentService.confirm(req);
    }

    /** 3) 결제 취소(환불) */
    @PostMapping("/payments/cancel")
    public Map<String, Object> cancel(@Valid @RequestBody CancelRequest req) {
        return paymentService.cancel(req);
    }

    /** 4) 마이페이지 - 내 결제 내역 조회 */
    @GetMapping("/payments/my")
    public List<ConfirmResponse> getMyPayments(
            // principal 안에 있는 id 프로퍼티를 그대로 꺼내오는 방식
            @AuthenticationPrincipal(expression = "id") Long userId
    ) {
        return paymentService.getMyPayments(userId);
    }
}