package com.example.barofarm_backend.controller;

import com.example.barofarm_backend.dto.request.CancelRequest;
import com.example.barofarm_backend.dto.request.ConfirmRequest;
import com.example.barofarm_backend.dto.request.CreateOrderRequest;
import com.example.barofarm_backend.dto.response.ConfirmResponse;
import com.example.barofarm_backend.dto.response.CreateOrderResponse;
import com.example.barofarm_backend.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}
