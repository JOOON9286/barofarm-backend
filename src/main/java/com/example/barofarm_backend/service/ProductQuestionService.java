package com.example.barofarm_backend.service;

import com.example.barofarm_backend.dto.request.ProductQuestionRequestDto;
import com.example.barofarm_backend.dto.response.ProductQuestionResponseDto;
import com.example.barofarm_backend.entity.Product;
import com.example.barofarm_backend.entity.ProductQuestion;
import com.example.barofarm_backend.entity.User;
import com.example.barofarm_backend.repository.ProductQuestionRepository;
import com.example.barofarm_backend.repository.ProductRepository;
import com.example.barofarm_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductQuestionService {
    private final ProductQuestionRepository questionRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductQuestionResponseDto create(Long userId, ProductQuestionRequestDto dto) {
        User user = userRepository.findById(userId).orElseThrow();
        Product product = productRepository.findById(dto.getProductId()).orElseThrow();

        ProductQuestion question = ProductQuestion.builder()
                .user(user)
                .product(product)
                .title(dto.getTitle())
                .content(dto.getContent())
                .isPrivate(dto.isPrivate())
                .answered(false)
                .build();

        return toResponse(questionRepository.save(question));
    }

    public List<ProductQuestionResponseDto> getByProduct(Long productId) {
        return questionRepository.findByProductProductId(productId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void delete(Long questionId, Long userId) {
        ProductQuestion question = questionRepository.findById(questionId).orElseThrow();
        if (!question.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("본인만 삭제할 수 있습니다.");
        }
        questionRepository.delete(question);
    }

    private ProductQuestionResponseDto toResponse(ProductQuestion q) {
        return ProductQuestionResponseDto.builder()
                .id(q.getId())
                .username(q.getUser().getName())
                .title(q.getTitle())
                .content(q.getContent())
                .isPrivate(q.isPrivate())
                .answered(q.isAnswered())
                .createdAt(q.getCreatedAt())
                .build();
    }
}