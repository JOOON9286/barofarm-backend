package com.example.barofarm_backend.controller;

import com.example.barofarm_backend.dto.request.CheckoutRequest;
import com.example.barofarm_backend.dto.response.OrderDetailResponse;
import com.example.barofarm_backend.dto.response.OrderResponse;
import com.example.barofarm_backend.dto.response.OrderHistoryResponse;
import com.example.barofarm_backend.dto.response.SalesHistoryResponse;
import com.example.barofarm_backend.entity.OrderStatus;
import com.example.barofarm_backend.entity.User;
import com.example.barofarm_backend.service.OrderService;
import com.example.barofarm_backend.service.UserService;
import com.example.barofarm_backend.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/checkout")
    public ResponseEntity<List<OrderResponse>> checkout(@RequestHeader("Authorization") String token,
                                                        @RequestBody CheckoutRequest request) {
        String email = jwtTokenProvider.getEmail(token.replace("Bearer ", ""));
        User user = userService.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자 정보를 찾을 수 없습니다."));

        List<OrderResponse> result = orderService.checkout(user, request);
        return ResponseEntity.ok(result);
    }

    // user 마이페이지 결제 내역 조회
    @GetMapping("/history")
    public ResponseEntity<List<OrderHistoryResponse>> getOrderHistory(@RequestHeader("Authorization") String token) {
        String email = jwtTokenProvider.getEmail(token.replace("Bearer ", ""));
        User user = userService.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자 정보를 찾을 수 없습니다."));

        List<OrderHistoryResponse> history = orderService.getOrderHistory(user);
        return ResponseEntity.ok(history);
    }

    @PutMapping("/status/{orderId}")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId,
                                                    @RequestParam OrderStatus status) {
        orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok("주문 상태가 변경되었습니다.");
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailResponse> getOrderDetail(@PathVariable Long orderId,
                                                              @RequestHeader("Authorization") String token) {
        String email = jwtTokenProvider.getEmail(token.replace("Bearer ", ""));
        User user = userService.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자 정보를 찾을 수 없습니다."));

        OrderDetailResponse response = orderService.getOrderDetail(orderId, user);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId,
                                              @RequestHeader("Authorization") String token) {
        String email = jwtTokenProvider.getEmail(token.replace("Bearer ", ""));
        User user = userService.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자 정보를 찾을 수 없습니다."));

        orderService.cancelOrder(orderId, user);
        return ResponseEntity.ok("주문이 취소되었습니다.");
    }

    // 농부 판매내역 조회
    @GetMapping("/sales/history")
    public ResponseEntity<List<SalesHistoryResponse>> getSalesHistory(@RequestHeader("Authorization") String token) {
        System.out.println(">>> [요청 도착] /sales/history 호출됨");
        String email = jwtTokenProvider.getEmail(token.replace("Bearer ", ""));
        User seller = userService.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("판매자 정보를 찾을 수 없습니다."));

        List<SalesHistoryResponse> salesHistory = orderService.getSalesHistory(seller);
        return ResponseEntity.ok(salesHistory);
    }



}
