package com.example.barofarm_backend.controller;

import com.example.barofarm_backend.dto.request.ProductAnswerRequestDto;
import com.example.barofarm_backend.dto.response.ProductAnswerResponseDto;
import com.example.barofarm_backend.service.ProductAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/answers")
@RequiredArgsConstructor
public class ProductAnswerController {

    private final ProductAnswerService answerService;

    @PostMapping
    public ResponseEntity<ProductAnswerResponseDto> create(@RequestBody ProductAnswerRequestDto dto) {
        return ResponseEntity.ok(answerService.create(dto));
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<ProductAnswerResponseDto> getByQuestion(@PathVariable Long questionId) {
        return ResponseEntity.ok(answerService.getByQuestionId(questionId));
    }

    @PutMapping("/{answerId}")
    public ResponseEntity<ProductAnswerResponseDto> update(@PathVariable Long answerId, @RequestBody ProductAnswerRequestDto dto) {
        return ResponseEntity.ok(answerService.update(answerId, dto.getContent()));
    }

    @DeleteMapping("/{answerId}")
    public ResponseEntity<Void> delete(@PathVariable Long answerId) {
        answerService.delete(answerId);
        return ResponseEntity.noContent().build();
    }
}