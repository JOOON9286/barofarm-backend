package com.example.barofarm_backend.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class SalesHistoryResponse {

    private Long orderId;
    private String orderNumber;
    private LocalDateTime orderedAt;
    private String buyerName;
    private int totalAmount;
    private String status;          // String으로 변경
    private String paymentMethod;   // String으로 유지
    private List<SalesItem> items;

    @Getter
    @Builder
    public static class SalesItem {
        private Long productId;
        private String productName;
        private int quantity;
        private int price;
        private int discountRate; // 없으면 0으로
        private int totalPrice;
    }
}

