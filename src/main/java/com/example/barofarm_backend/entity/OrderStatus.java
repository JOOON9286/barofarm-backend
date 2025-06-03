package com.example.barofarm_backend.entity;

public enum OrderStatus {
    PAYMENT_COMPLETED,   // 결제 완료
    PREPARING,           // 배송 준비중
    SHIPPING,            // 배송중
    DELIVERED,           // 배송완료
    CANCELLED            // 주문 취소됨
}

