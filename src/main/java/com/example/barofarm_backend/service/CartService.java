package com.example.barofarm_backend.service;

import com.example.barofarm_backend.domain.*;
import com.example.barofarm_backend.dto.request.CartAddItemRequest;
import com.example.barofarm_backend.dto.request.CartUpdateItemRequest;
import com.example.barofarm_backend.dto.response.CartResponse;
import com.example.barofarm_backend.dto.response.CartResponse.CartItemResponse;
import com.example.barofarm_backend.repository.CartItemRepository;
import com.example.barofarm_backend.repository.CartRepository;
import com.example.barofarm_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    // 장바구니에 상품 추가
    public void addItem(CartAddItemRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> cartRepository.save(Cart.builder().user(user).build()));

        CartItem item = CartItem.builder()
                .cart(cart)
                .productId(request.getProductId())
                .productName(request.getProductName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .totalPrice(request.getPrice() * request.getQuantity())
                .build();

        cartItemRepository.save(item);
    }

    // 장바구니 아이템 수량 수정
    public void updateItemQuantity(CartUpdateItemRequest request) {
        CartItem item = cartItemRepository.findById(request.getCartItemId())
                .orElseThrow(() -> new IllegalArgumentException("장바구니 아이템을 찾을 수 없습니다."));

        item.updateQuantity(request.getQuantity());
        cartItemRepository.save(item);
    }

    // 장바구니 조회
    public CartResponse getCartByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("장바구니가 존재하지 않습니다."));

        List<CartItemResponse> items = cart.getItems().stream().map(item ->
                CartItemResponse.builder()
                        .cartItemId(item.getId())
                        .productId(item.getProductId())
                        .productName(item.getProductName())
                        .price(item.getPrice())
                        .quantity(item.getQuantity())
                        .totalPrice(item.getTotalPrice())
                        .build()
        ).collect(Collectors.toList());

        return CartResponse.builder()
                .cartId(cart.getId())
                .userId(user.getId())
                .items(items)
                .build();
    }
}
