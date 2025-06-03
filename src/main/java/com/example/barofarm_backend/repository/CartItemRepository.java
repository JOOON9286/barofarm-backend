package com.example.barofarm_backend.repository;

import com.example.barofarm_backend.entity.Cart;
import com.example.barofarm_backend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart(Cart cart);
}
