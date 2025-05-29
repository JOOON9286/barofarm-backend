package com.example.barofarm_backend.controller;

import com.example.barofarm_backend.dto.request.CartAddProductRequest;
import com.example.barofarm_backend.dto.request.CartUpdateProductRequest;
import com.example.barofarm_backend.dto.response.CartResponse;
import com.example.barofarm_backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // 장바구니에 상품 추가
    @PostMapping("/add")
    public ResponseEntity<String> addItem(@RequestBody CartAddProductRequest request) {
        cartService.addItem(request);
        return ResponseEntity.ok("장바구니에 상품이 추가되었습니다.");
    }

    // 장바구니 수량 변경
    @PutMapping("/update")
    public ResponseEntity<String> updateItem(@RequestBody CartUpdateProductRequest request) {
        cartService.updateItemQuantity(request);
        return ResponseEntity.ok("장바구니 항목이 수정되었습니다.");
    }

    // 장바구니 조회
    @GetMapping("/{userId}")
    public ResponseEntity<CartResponse> getCart(@PathVariable Long userId) {
        CartResponse cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }
}
