package com.example.barofarm_backend.repository;

import com.example.barofarm_backend.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByUserId(Long userId);
    boolean existsByUserIdAndProductProductId(Long userId, Long productId);
    void deleteByUserIdAndProductProductId(Long userId, Long productId);

}
