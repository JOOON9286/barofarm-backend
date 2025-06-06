package com.example.barofarm_backend.service;

import com.example.barofarm_backend.dto.request.ReviewRequestDto;
import com.example.barofarm_backend.dto.response.ReviewResponseDto;
import com.example.barofarm_backend.entity.Product;
import com.example.barofarm_backend.entity.Review;
import com.example.barofarm_backend.entity.User;
import com.example.barofarm_backend.repository.ProductRepository;
import com.example.barofarm_backend.repository.ReviewRepository;
import com.example.barofarm_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public ReviewResponseDto createReview(Long userId, ReviewRequestDto dto) {
        User user = userRepository.findById(userId).orElseThrow();
        Product product = productRepository.findById(dto.getProductId()).orElseThrow();

        Review review = Review.builder()
                .user(user)
                .product(product)
                .rating(dto.getRating())
                .comment(dto.getComment())
                .imageUrls(dto.getImageUrls())
                .purchased(true)
                .build();

        Review saved = reviewRepository.save(review);
        return toResponse(saved);
    }

    public ReviewResponseDto updateReview(Long reviewId, ReviewRequestDto dto, Long userId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow();
        if (!review.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("작성한 본인만 수정할 수 있습니다.");
        }
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setImageUrls(dto.getImageUrls());
        return toResponse(reviewRepository.save(review));
    }

    public void deleteReview(Long reviewId, Long userId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow();
        if (!review.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("작성한 본인만 삭제할 수 있습니다.");
        }
        reviewRepository.delete(review);
    }

    public List<ReviewResponseDto> getReviewsByProduct(Long productId) {
        return reviewRepository.findByProductProductId(productId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public double getAverageRating(Long productId) {
        List<Review> reviews = reviewRepository.findByProductProductId(productId);
        return reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
    }

    private ReviewResponseDto toResponse(Review review) {
        return ReviewResponseDto.builder()
                .reviewId(review.getId())
                .username(review.getUser().getName())
                .rating(review.getRating())
                .comment(review.getComment())
                .imageUrls(review.getImageUrls())
                .purchased(review.isPurchased())
                .createdAt(review.getCreatedAt())
                .build();
    }
}