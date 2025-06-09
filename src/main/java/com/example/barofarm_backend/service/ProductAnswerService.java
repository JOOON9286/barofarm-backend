package com.example.barofarm_backend.service;

import com.example.barofarm_backend.dto.request.ProductAnswerRequestDto;
import com.example.barofarm_backend.dto.response.ProductAnswerResponseDto;
import com.example.barofarm_backend.entity.ProductAnswer;
import com.example.barofarm_backend.entity.ProductQuestion;
import com.example.barofarm_backend.repository.ProductAnswerRepository;
import com.example.barofarm_backend.repository.ProductQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductAnswerService {
    private final ProductAnswerRepository answerRepository;
    private final ProductQuestionRepository questionRepository;

    public ProductAnswerResponseDto create(ProductAnswerRequestDto dto) {
        ProductQuestion question = questionRepository.findById(dto.getQuestionId()).orElseThrow();

        ProductAnswer answer = ProductAnswer.builder()
                .question(question)
                .content(dto.getContent())
                .build();

        question.setAnswered(true);
        questionRepository.save(question);

        return toResponse(answerRepository.save(answer));
    }

    public ProductAnswerResponseDto getByQuestionId(Long questionId) {
        ProductAnswer answer = answerRepository.findByQuestionId(questionId).orElseThrow();
        return toResponse(answer);
    }

    public ProductAnswerResponseDto update(Long answerId, String content) {
        ProductAnswer answer = answerRepository.findById(answerId).orElseThrow();
        answer.setContent(content);
        return toResponse(answerRepository.save(answer));
    }

    public void delete(Long answerId) {
        ProductAnswer answer = answerRepository.findById(answerId).orElseThrow();
        ProductQuestion question = answer.getQuestion();
        question.setAnswered(false);
        questionRepository.save(question);
        answerRepository.delete(answer);
    }

    private ProductAnswerResponseDto toResponse(ProductAnswer answer) {
        return ProductAnswerResponseDto.builder()
                .id(answer.getId())
                .questionId(answer.getQuestion().getId())
                .content(answer.getContent())
                .createdAt(answer.getCreatedAt())
                .build();
    }
}
