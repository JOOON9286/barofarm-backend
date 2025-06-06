package com.example.barofarm_backend.service;
import com.example.barofarm_backend.dto.request.WishlistRequestDto;
import com.example.barofarm_backend.dto.response.WishlistResponseDto;
import com.example.barofarm_backend.entity.Product;
import com.example.barofarm_backend.entity.User;
import com.example.barofarm_backend.entity.Wishlist;
import com.example.barofarm_backend.repository.ProductRepository;
import com.example.barofarm_backend.repository.UserRepository;
import com.example.barofarm_backend.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public void addToWishlist(WishlistRequestDto requestDto) {
        if (wishlistRepository.existsByUserIdAndProductProductId(requestDto.getUserId(), requestDto.getProductId())) {
            throw new IllegalStateException("이미 찜한 상품입니다.");
        }

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        Wishlist wishlist = Wishlist.builder()
                .user(user)
                .product(product)
                .likedAt(LocalDateTime.now())
                .build();

        wishlistRepository.save(wishlist);
    }

    @Transactional
    public void removeFromWishlist(Long userId, Long productId) {
        wishlistRepository.deleteByUserIdAndProductProductId(userId, productId);
    }

    public List<WishlistResponseDto> getWishlistByUser(Long userId) {
        List<Wishlist> wishlists = wishlistRepository.findByUserId(userId);

        return wishlists.stream()
                .map(w -> {
                    Product p = w.getProduct();
                    return WishlistResponseDto.builder()
                            .id(w.getId())
                            .userId(userId)
                            .productId(p.getProductId())
                            .productName(p.getProductName())
                            .producerName(p.getUser().getName()) // User 엔티티에 name 필드 필요
                            .origin(p.getOrigin())
                            .price(p.getPrice())
                            .imageUrl(p.getImageUrl())
                            .likedAt(w.getLikedAt())
                            .build();
                })
                .collect(Collectors.toList());
    }

}
