package com.example.barofarm_backend.dto.response;

import com.example.barofarm_backend.entity.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class OrderHistoryResponse {

    private Long orderId;
    private String deliveryDate;
    private String deliveryTime;
    private LocalDateTime orderedAt;
    private OrderStatus status;
    private List<OrderItemSummary> items;

    @Getter
    @Builder
    public static class OrderItemSummary {
        private Long productId;
        private String productName;
        private int quantity;
        private int price;
        private int totalPrice;
    }
}
