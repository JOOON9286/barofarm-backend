package com.example.barofarm_backend.controller;

import com.example.barofarm_backend.dto.request.ProductQuestionRequestDto;
import com.example.barofarm_backend.dto.response.ProductQuestionResponseDto;
import com.example.barofarm_backend.security.UserPrincipal;
import com.example.barofarm_backend.service.ProductQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class ProductQuestionController {

    private final ProductQuestionService questionService;

    @PostMapping
    public ResponseEntity<ProductQuestionResponseDto> create(@RequestBody ProductQuestionRequestDto dto, @AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.ok(questionService.create(user.getId(), dto));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductQuestionResponseDto>> getByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(questionService.getByProduct(productId));
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> delete(@PathVariable Long questionId, @AuthenticationPrincipal UserPrincipal user) {
        questionService.delete(questionId, user.getId());
        return ResponseEntity.noContent().build();
    }
}