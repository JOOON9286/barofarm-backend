package com.example.barofarm_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "order_payment", indexes = {
        @Index(name = "idx_order_payment_order_id", columnList = "orderId", unique = true)
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class OrderPayment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String orderId;                 // ex: order_1719050123456

    @Column(nullable = false)
    private Integer amount;                 // 원화 정수

    @Column(nullable = false, length = 120)
    private String orderName;               // 주문명(장바구니/상품명)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status;             // PENDING, PAID, FAILED, CANCELLED

    @Column(nullable = false)
    private Long userId;

    // 승인 후 저장되는 값들
    private String paymentKey;
    private String method;                  // CARD/ACCOUNT
    private String receiptUrl;
    private Instant approvedAt;

    @Column(updatable = false)
    private Instant createdAt;
    private Instant updatedAt;

    @PrePersist void prePersist() { createdAt = Instant.now(); updatedAt = createdAt; }
    @PreUpdate  void preUpdate()  { updatedAt = Instant.now(); }

    public enum OrderStatus { PENDING, PAID, FAILED, CANCELLED }
}
