package com.example.barofarm_backend.service;

import com.example.barofarm_backend.dto.request.CancelRequest;
import com.example.barofarm_backend.dto.request.ConfirmRequest;
import com.example.barofarm_backend.dto.request.CreateOrderRequest;
import com.example.barofarm_backend.dto.response.ConfirmResponse;
import com.example.barofarm_backend.dto.response.CreateOrderResponse;
import com.example.barofarm_backend.entity.OrderPayment;
import com.example.barofarm_backend.repository.OrderPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OrderPaymentRepository repo;
    private final TossClient tossClient;

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderRequest req) {
        String newOrderId = "order_" + System.currentTimeMillis();

        OrderPayment order = OrderPayment.builder()
                .orderId(newOrderId)
                .orderName(req.orderName())
                .amount(req.amount())
                .status(OrderPayment.OrderStatus.PENDING)
                .build();

        repo.save(order);
        return new CreateOrderResponse(order.getOrderId(), order.getAmount(), order.getOrderName());
    }

    @Transactional
    public ConfirmResponse confirm(ConfirmRequest req) {
        OrderPayment order = repo.findByOrderId(req.orderId())
                .orElseThrow(() -> new IllegalArgumentException("order not found"));

        // 프론트 조작 방지: 서버 금액과 일치 확인
        if (!Objects.equals(order.getAmount(), req.amount())) {
            throw new IllegalArgumentException("invalid amount");
        }

        Map<String, Object> data = tossClient.confirm(req.paymentKey(), req.orderId(), req.amount());

        String method = Objects.toString(data.get("method"), null);
        Integer totalAmount = (Integer) data.get("totalAmount");

        @SuppressWarnings("unchecked")
        Map<String, Object> receipt = (Map<String, Object>) data.get("receipt");
        String receiptUrl = receipt != null ? Objects.toString(receipt.get("url"), null) : null;

        String approvedAtStr = Objects.toString(data.get("approvedAt"), null);
        Instant approvedAt = approvedAtStr != null ? Instant.parse(approvedAtStr) : null;

        order.setStatus(OrderPayment.OrderStatus.PAID);
        order.setPaymentKey(req.paymentKey());
        order.setMethod(method);
        order.setReceiptUrl(receiptUrl);
        order.setApprovedAt(approvedAt);

        return new ConfirmResponse(order.getOrderId(), method, totalAmount, approvedAt, receiptUrl);
    }

    @Transactional
    public Map<String, Object> cancel(CancelRequest req) {
        OrderPayment order = repo.findByOrderId(req.orderId())
                .orElseThrow(() -> new IllegalArgumentException("order not found"));

        if (order.getStatus() != OrderPayment.OrderStatus.PAID) {
            throw new IllegalStateException("only PAID orders can be cancelled");
        }

        Map<String, Object> resp = tossClient.cancelByPaymentKey(order.getPaymentKey(), req.cancelReason());
        order.setStatus(OrderPayment.OrderStatus.CANCELLED);
        return resp;
    }
}
