package com.example.barofarm_backend.controller;
import com.example.barofarm_backend.dto.request.WishlistRequestDto;
import com.example.barofarm_backend.dto.response.WishlistResponseDto;
import com.example.barofarm_backend.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping
    public ResponseEntity<String> addToWishlist(@RequestBody WishlistRequestDto requestDto) {
        wishlistService.addToWishlist(requestDto);
        return ResponseEntity.ok("찜 목록에 추가되었습니다.");
    }

    @DeleteMapping
    public ResponseEntity<String> removeFromWishlist(@RequestParam Long userId,
                                                     @RequestParam Long productId) {
        wishlistService.removeFromWishlist(userId, productId);
        return ResponseEntity.ok("찜 목록에서 삭제되었습니다.");
    }

    @GetMapping
    public ResponseEntity<List<WishlistResponseDto>> getWishlist(@RequestParam Long userId) {
        return ResponseEntity.ok(wishlistService.getWishlistByUser(userId));
    }
}
