package com.example.barofarm_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 장바구니와 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;
    private Long productId;
    private String productName;
    private int quantity;
    private int price;
    private int totalPrice;  // 수량 * 단가

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
        this.totalPrice = this.price * quantity;
    }
}
