package com.example.barofarm_backend.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.barofarm_backend.dto.request.ProductQuestionRequestDto;
import com.example.barofarm_backend.dto.response.ProductQuestionResponseDto;
import com.example.barofarm_backend.service.ProductQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class ProductQuestionController {

    private final ProductQuestionService questionService;

    // 문의 등록
    @PostMapping
    public ResponseEntity<String> create(@RequestBody ProductQuestionRequestDto requestDto) {
        questionService.createQuestion(requestDto.getUserId(), requestDto);
        System.out.println(requestDto.getIsPrivate());
        return ResponseEntity.ok("문의가 등록되었습니다.");
    }

    // 문의 삭제
    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam Long userId,
                                         @RequestParam Long questionId) {
        questionService.deleteQuestion(questionId, userId);
        return ResponseEntity.ok("문의가 삭제되었습니다.");
    }

    // 특정 상품의 문의 조회
    @GetMapping("/product")
    public ResponseEntity<List<ProductQuestionResponseDto>> getByProduct(@RequestParam Long productId) {
        return ResponseEntity.ok(questionService.getQuestionsByProduct(productId));
    }

    // 특정 사용자의 문의 조회
    @GetMapping("/user")
    public ResponseEntity<List<ProductQuestionResponseDto>> getByUser(@RequestParam Long userId) {
        return ResponseEntity.ok(questionService.getQuestionsByUser(userId));
    }
}
