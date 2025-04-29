package com.example.barofarm_backend.repository;

import com.example.barofarm_backend.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
