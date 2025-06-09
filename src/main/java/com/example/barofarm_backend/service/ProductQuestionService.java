package com.example.barofarm_backend.service;

import com.example.barofarm_backend.dto.request.ProductQuestionRequestDto;
import com.example.barofarm_backend.dto.response.ProductQuestionResponseDto;
import com.example.barofarm_backend.entity.Product;
import com.example.barofarm_backend.entity.ProductQuestion;
import com.example.barofarm_backend.entity.User;
import com.example.barofarm_backend.repository.ProductQuestionRepository;
import com.example.barofarm_backend.repository.ProductRepository;
import com.example.barofarm_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductQuestionService {

    private final ProductQuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public void createQuestion(Long userId, ProductQuestionRequestDto dto) {
        User user = userRepository.findById(userId).orElseThrow();
        Product product = productRepository.findById(dto.getProductId()).orElseThrow();

        ProductQuestion question = ProductQuestion.builder()
                .user(user)
                .product(product)
                .title(dto.getTitle())
                .content(dto.getContent())
                .isPrivate(dto.getIsPrivate())
                .answered(false)
                .build();

        questionRepository.save(question);
    }

    @Transactional
    public void deleteQuestion(Long questionId, Long userId) {
        ProductQuestion question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 문의가 존재하지 않습니다."));

        if (!question.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("본인만 삭제할 수 있습니다.");
        }

        questionRepository.delete(question);
    }

    public List<ProductQuestionResponseDto> getQuestionsByProduct(Long productId) {
        return questionRepository.findByProductProductId(productId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<ProductQuestionResponseDto> getQuestionsByUser(Long userId) {
        return questionRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ProductQuestionResponseDto toResponse(ProductQuestion q) {
        return ProductQuestionResponseDto.builder()
                .id(q.getId())
                .userId(q.getUser().getId())
                .username(q.getUser().getName())
                .title(q.getTitle())
                .content(q.getContent())
                .isPrivate(q.isPrivate())
                .answered(q.isAnswered())
                .createdAt(q.getCreatedAt())
                .build();
    }
}