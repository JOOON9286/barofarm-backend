package com.example.barofarm_backend.dto.response;

import com.example.barofarm_backend.entity.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class OrderDetailResponse {

    private Long orderId;
    private String deliveryDate;
    private String deliveryTime;
    private LocalDateTime orderedAt;
    private OrderStatus status;

    private List<OrderItemDetail> items;

    @Getter
    @Builder
    public static class OrderItemDetail {
        private String productName;
        private int price;
        private int quantity;
        private int totalPrice;
    }
}
