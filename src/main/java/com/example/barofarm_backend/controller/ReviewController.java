package com.example.barofarm_backend.controller;

import com.example.barofarm_backend.dto.request.ReviewRequestDto;
import com.example.barofarm_backend.dto.response.ReviewResponseDto;
import com.example.barofarm_backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponseDto> create(@RequestBody ReviewRequestDto dto,
                                                    @AuthenticationPrincipal User user) {
        Long userId = Long.parseLong(user.getUsername());
        return ResponseEntity.ok(reviewService.createReview(userId, dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponseDto> update(@PathVariable Long id,
                                                    @RequestBody ReviewRequestDto dto,
                                                    @AuthenticationPrincipal User user) {
        Long userId = Long.parseLong(user.getUsername());
        return ResponseEntity.ok(reviewService.updateReview(id, dto, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @AuthenticationPrincipal User user) {
        Long userId = Long.parseLong(user.getUsername());
        reviewService.deleteReview(id, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewResponseDto>> getByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.getReviewsByProduct(productId));
    }

    @GetMapping("/product/{productId}/average")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.getAverageRating(productId));
    }
}