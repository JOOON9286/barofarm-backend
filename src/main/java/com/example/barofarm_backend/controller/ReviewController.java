package com.example.barofarm_backend.controller;

import com.example.barofarm_backend.dto.request.ReviewRequestDto;
import com.example.barofarm_backend.dto.response.ReviewResponseDto;
import com.example.barofarm_backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponseDto> create(@RequestBody ReviewRequestDto dto, @AuthenticationPrincipal com.example.barofarm_backend.security.UserPrincipal user) {
        return ResponseEntity.ok(reviewService.createReview(user.getId(), dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponseDto> update(@PathVariable Long id, @RequestBody ReviewRequestDto dto, @AuthenticationPrincipal com.example.barofarm_backend.security.UserPrincipal user) {
        return ResponseEntity.ok(reviewService.updateReview(id, dto, user.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal com.example.barofarm_backend.security.UserPrincipal user) {
        reviewService.deleteReview(id, user.getId());
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