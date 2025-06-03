package com.example.barofarm_backend.service;

import com.example.barofarm_backend.dto.request.CheckoutRequest;
import com.example.barofarm_backend.dto.response.OrderDetailResponse;
import com.example.barofarm_backend.dto.response.OrderHistoryResponse;
import com.example.barofarm_backend.dto.response.OrderResponse;
import com.example.barofarm_backend.entity.*;
import com.example.barofarm_backend.repository.CartItemRepository;
import com.example.barofarm_backend.repository.CartRepository;
import com.example.barofarm_backend.repository.OrderRepository;
import com.example.barofarm_backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    // 장바구니 결제 > 주문 저장,재고 차감,장바구니 비우기
    public List<OrderResponse> checkout(User user, CheckoutRequest request) {
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("장바구니가 존재하지 않습니다."));

        List<CartItem> items = cartItemRepository.findByCart(cart);
        List<OrderItem> orderItems = new ArrayList<>();
        List<OrderResponse> result = new ArrayList<>();

        Order order = Order.builder()
                .user(user)
                .deliveryDate(request.getDeliveryDate())
                .deliveryTime(request.getDeliveryTime())
                .orderedAt(LocalDateTime.now())
                .status(OrderStatus.PAYMENT_COMPLETED)
                .build();

        for (CartItem item : items) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("상품 ID를 찾을 수 없습니다."));

            int itemQuantity = item.getQuantity();

            if (product.getStockQuantity() < itemQuantity) {
                throw new IllegalArgumentException("재고가 부족한 상품이 있습니다: " + product.getProductName());
            }

            product.setStockQuantity(product.getStockQuantity() - itemQuantity);
            productRepository.save(product);

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .productId(product.getProductId())
                    .productName(product.getProductName())
                    .price(product.getPrice())
                    .quantity(itemQuantity)
                    .totalPrice(product.getPrice() * itemQuantity)
                    .build();

            orderItems.add(orderItem);

            result.add(OrderResponse.builder()
                    .productId(product.getProductId())
                    .productName(product.getProductName())
                    .price(product.getPrice())
                    .quantity(itemQuantity)
                    .totalPrice(product.getPrice() * itemQuantity)
                    .build());
        }

        order.setItems(orderItems);
        orderRepository.save(order);
        cartItemRepository.deleteAll(items);

        return result;
    }

    // 사용자 주문내역 조회
    public List<OrderHistoryResponse> getOrderHistory(User user) {
        List<Order> orders = orderRepository.findByUser(user);

        return orders.stream().map(order -> {
            List<OrderHistoryResponse.OrderItemSummary> itemSummaries = order.getItems().stream().map(item ->
                    OrderHistoryResponse.OrderItemSummary.builder()
                            .productName(item.getProductName())
                            .quantity(item.getQuantity())
                            .price(item.getPrice())
                            .totalPrice(item.getTotalPrice())
                            .build()
            ).toList();

            return OrderHistoryResponse.builder()
                    .orderId(order.getId())
                    .deliveryDate(order.getDeliveryDate())
                    .deliveryTime(order.getDeliveryTime())
                    .orderedAt(order.getOrderedAt())
                    .status(order.getStatus())
                    .items(itemSummaries)
                    .build();
        }).toList();
    }
    public void updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다."));
        order.setStatus(status);
        orderRepository.save(order);
    }
    // 사용자 정보 상세 조회
    public OrderDetailResponse getOrderDetail(Long orderId, User user) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다."));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("해당 주문에 접근할 수 없습니다.");
        }

        List<OrderDetailResponse.OrderItemDetail> items = order.getItems().stream()
                .map(item -> OrderDetailResponse.OrderItemDetail.builder()
                        .productName(item.getProductName())
                        .price(item.getPrice())
                        .quantity(item.getQuantity())
                        .totalPrice(item.getTotalPrice())
                        .build())
                .toList();

        return OrderDetailResponse.builder()
                .orderId(order.getId())
                .deliveryDate(order.getDeliveryDate())
                .deliveryTime(order.getDeliveryTime())
                .orderedAt(order.getOrderedAt())
                .status(order.getStatus())
                .items(items)
                .build();
    }

    // 주문 취소
    @Transactional
    public void cancelOrder(Long orderId, User user) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("해당 주문이 존재하지 않습니다."));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("자신의 주문만 취소할 수 있습니다.");
        }

        if (order.getStatus() != OrderStatus.PAYMENT_COMPLETED) {
            throw new RuntimeException("결제 완료 상태에서만 주문을 취소할 수 있습니다.");
        }

        for (OrderItem item : order.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("상품이 존재하지 않습니다."));

            product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
            productRepository.save(product);
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

}
