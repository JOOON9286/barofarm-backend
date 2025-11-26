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
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OrderPaymentRepository repo;
    private final TossClient tossClient;

    /** 결제창 생성 + URL 반환 */
    @Transactional
    public CreateOrderResponse createOrder(CreateOrderRequest req) {
        String newOrderId = "order_" + System.currentTimeMillis();

        //  Toss 결제 준비 요청
        Map<String, Object> tossResponse = tossClient.requestPayment(
                newOrderId,
                req.orderName(),
                req.amount(),
                "http://localhost:3000/success", // 결제 성공 시 이동 URL
                "http://localhost:3000/fail"     // 결제 실패 시 이동 URL
        );

        //  Toss 응답에서 결제창 URL 추출
        String paymentUrl = null;
        if (tossResponse != null) {
            if (tossResponse.containsKey("checkout")) {
                Map<String, Object> checkout = (Map<String, Object>) tossResponse.get("checkout");
                paymentUrl = (String) checkout.get("url");
            } else if (tossResponse.containsKey("next_redirect_pc_url")) {
                paymentUrl = (String) tossResponse.get("next_redirect_pc_url");
            }
        }

        // DB 저장
        OrderPayment order = OrderPayment.builder()
                .orderId(newOrderId)
                .orderName(req.orderName())
                .amount(req.amount())
                .userId(req.userId())  // ✅ 어떤 유저 결제인지 저장
                .status(OrderPayment.OrderStatus.PENDING)
                .build();

        repo.save(order);

        //  결제창 URL 포함 응답 반환
        return new CreateOrderResponse(order.getOrderId(), order.getAmount(), paymentUrl);
    }

    /**  결제 승인 (successUrl 이후 호출됨) */
    @Transactional
    public ConfirmResponse confirm(ConfirmRequest req) {
        OrderPayment order = repo.findByOrderId(req.orderId())
                .orElseThrow(() -> new IllegalArgumentException("order not found"));

        // 프론트 조작 방지: 서버 금액과 일치 확인
        if (!Objects.equals(order.getAmount(), req.amount())) {
            throw new IllegalArgumentException("invalid amount");
        }

        //  Toss 결제 승인 요청
        Map<String, Object> data = tossClient.confirm(req.paymentKey(), req.orderId(), req.amount());

        String method = Objects.toString(data.get("method"), null);
        Integer totalAmount = (Integer) data.get("totalAmount");

        @SuppressWarnings("unchecked")
        Map<String, Object> receipt = (Map<String, Object>) data.get("receipt");
        String receiptUrl = receipt != null ? Objects.toString(receipt.get("url"), null) : null;

        String approvedAtStr = Objects.toString(data.get("approvedAt"), null);
        Instant approvedAt = approvedAtStr != null ? Instant.parse(approvedAtStr) : null;

        //  결제 완료로 상태 변경
        order.setStatus(OrderPayment.OrderStatus.PAID);
        order.setPaymentKey(req.paymentKey());
        order.setMethod(method);
        order.setReceiptUrl(receiptUrl);
        order.setApprovedAt(approvedAt);

        return new ConfirmResponse(order.getOrderId(), method, totalAmount, approvedAt, receiptUrl);
    }

    /**  결제 취소 (환불) */
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

    /**  마이페이지에서 내 결제 내역 조회 */
    @Transactional(readOnly = true)
    public List<ConfirmResponse> getMyPayments(Long userId) {
        List<OrderPayment> list = repo.findByUserId(userId);


        return list.stream()
                .filter(op -> op.getStatus() == OrderPayment.OrderStatus.PAID)
                .map(op -> new ConfirmResponse(
                        op.getOrderId(),
                        op.getMethod(),
                        op.getAmount(),
                        op.getApprovedAt(),
                        op.getReceiptUrl()
                ))
                .toList();
    }
}