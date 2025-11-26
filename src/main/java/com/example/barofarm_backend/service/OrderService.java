package com.example.barofarm_backend.service;

import com.example.barofarm_backend.dto.request.CheckoutRequest;
import com.example.barofarm_backend.dto.response.OrderDetailResponse;
import com.example.barofarm_backend.dto.response.OrderHistoryResponse;
import com.example.barofarm_backend.dto.response.OrderResponse;
import com.example.barofarm_backend.dto.response.SalesHistoryResponse;
import com.example.barofarm_backend.entity.*;
import com.example.barofarm_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    // 장바구니 결제 > 주문 저장,재고 차감,장바구니 비우기
    public List<OrderResponse> checkout(User user, CheckoutRequest request) {
        List<OrderItem> orderItems = new ArrayList<>();
        List<OrderResponse> result = new ArrayList<>();

        Order order = Order.builder()
                .user(user)
                .deliveryDate(request.getDeliveryDate())
                .deliveryTime(request.getDeliveryTime())
                .receiverName(request.getReceiverName())
                .receiverPhone(request.getReceiverPhone())
                .zipcode(request.getZipcode())
                .address(request.getAddress())
                .addressDetail(request.getAddressDetail())
                .deliveryRequest(request.getDeliveryRequest())
                .paymentMethod(request.getPaymentMethod())
                .orderedAt(LocalDateTime.now())
                .totalAmount(request.getTotalAmount())
                .status(OrderStatus.PAYMENT_COMPLETED)
                .build();

        for (CheckoutRequest.ProductInfo productInfo : request.getProducts()) {
            Product product = productRepository.findById(productInfo.getProductId())
                    .orElseThrow(() -> new RuntimeException("상품 ID를 찾을 수 없습니다."));

            if (product.getStockQuantity() < productInfo.getQuantity()) {
                throw new RuntimeException("재고가 부족한 상품이 있습니다: " + product.getProductName());
            }

            product.setStockQuantity(product.getStockQuantity() - productInfo.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .productId(product.getProductId())
                    .productName(product.getProductName())
                    .price(product.getPrice())
                    .quantity(productInfo.getQuantity())
                    .totalPrice(product.getPrice() * productInfo.getQuantity())
                    .build();

            orderItems.add(orderItem);

            result.add(OrderResponse.builder()
                    .productId(product.getProductId())
                    .productName(product.getProductName())
                    .price(product.getPrice())
                    .quantity(productInfo.getQuantity())
                    .totalPrice(product.getPrice() * productInfo.getQuantity())
                    .build());
        }

        order.setItems(orderItems);
        orderRepository.save(order);

        return result;
    }

    // 사용자 주문내역 조회
    public List<OrderHistoryResponse> getOrderHistory(User user) {
        List<Order> orders = orderRepository.findByUser(user);

        return orders.stream().map(order -> {
            List<OrderHistoryResponse.OrderItemSummary> itemSummaries = order.getItems().stream().map(item ->
                    OrderHistoryResponse.OrderItemSummary.builder()
                            .productId(item.getProductId())
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


    // 농부 판매 이력 조회
    public List<SalesHistoryResponse> getSalesHistory(User seller) {
        // Step 1: 판매자가 등록한 상품 목록
        List<Product> sellerProducts = productRepository.findByUserId(seller.getId());

        // Step 2: productId만 추출
        List<Long> productIds = sellerProducts.stream()
                .map(Product::getProductId)
                .toList();

        // Step 3: 해당 productId에 해당하는 주문 아이템 조회
        List<OrderItem> items = orderItemRepository.findByProductIdIn(productIds);
        System.out.println(">>> [DB 조회 결과] 판매자가 등록한 OrderItem 수 = " + items.size());

        // Step 4: 주문별로 그룹화
        Map<Long, List<OrderItem>> orderMap = items.stream()
                .collect(Collectors.groupingBy(item -> item.getOrder().getId()));

        return orderMap.entrySet().stream().map(entry -> {
            Order order = entry.getValue().get(0).getOrder();
            String buyerName = order.getUser().getName();

            List<SalesHistoryResponse.SalesItem> productInfos = entry.getValue().stream()
                    .map(i -> SalesHistoryResponse.SalesItem.builder()
                            .productId(i.getProductId())
                            .productName(i.getProductName())
                            .quantity(i.getQuantity())
                            .price(i.getPrice())
                            .discountRate(0) // 없으면 0
                            .totalPrice(i.getTotalPrice())
                            .build())
                    .toList();

            return SalesHistoryResponse.builder()
                    .orderId(order.getId())
                    .orderNumber("ORD-" + order.getId())
                    .buyerName(buyerName)
                    .orderedAt(order.getOrderedAt())
                    .totalAmount(order.getTotalAmount())
                    .status(order.getStatus().name())
                    .paymentMethod(order.getPaymentMethod())
                    .items(productInfos)
                    .build();
        }).toList();
    }



    public List<Order> getOrdersByFarmer(User farmer) {
        return orderRepository.findOrdersByFarmerId(farmer.getId());
    }
}