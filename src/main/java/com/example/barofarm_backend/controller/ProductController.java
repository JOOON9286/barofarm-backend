package com.example.barofarm_backend.controller;

import com.example.barofarm_backend.dto.request.ProductRequest;
import com.example.barofarm_backend.dto.response.ProductResponse;
import com.example.barofarm_backend.entity.User;
import com.example.barofarm_backend.service.ProductService;
import com.example.barofarm_backend.service.UserService;
import com.example.barofarm_backend.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    // ✅ 상품 등록 - 인증된 사용자만 가능
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest request,
                                           @RequestHeader("Authorization") String token) {
        try {
            // JWT에서 이메일 추출
            String userEmail = jwtTokenProvider.getUserId(token.replace("Bearer ", ""));

            // 이메일로 사용자 조회
            User user = userService.getUserByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

            // 상품 등록
            ProductResponse response = productService.createProduct(request, user);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("상품 등록 중 오류 발생: " + e.getMessage());
        }
    }
}
