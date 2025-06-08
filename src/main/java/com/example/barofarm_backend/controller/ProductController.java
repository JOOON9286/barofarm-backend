package com.example.barofarm_backend.controller;

import com.example.barofarm_backend.dto.request.ProductRequest;
import com.example.barofarm_backend.dto.response.ProductResponse;
import com.example.barofarm_backend.entity.User;
import com.example.barofarm_backend.util.JwtTokenProvider;
import com.example.barofarm_backend.service.ProductService;
import com.example.barofarm_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/my")
    public ResponseEntity<List<ProductResponse>> getMyProducts(@RequestHeader("Authorization") String token) {
        String userEmail = jwtTokenProvider.getEmail(token.replace("Bearer ", ""));
        User user = userService.getUserByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        List<ProductResponse> products = productService.getProductsByUser(user);
        return ResponseEntity.ok(products);
    }

    //농부 본인이 등록한 상품만 등록 가능
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest request,
                                           @RequestHeader("Authorization") String token) {
        try {
            String userEmail = jwtTokenProvider.getEmail(token.replace("Bearer ", ""));
            User user = userService.getUserByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

            ProductResponse response = productService.createProduct(request, user);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("상품 등록 중 오류 발생: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
    
    //상품 조회
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
                                                         @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    //농부 본인이 등록한 상품만 수정 가능
    @PutMapping("/my/{id}")
    public ResponseEntity<?> updateMyProduct(@PathVariable Long id,
                                             @RequestBody ProductRequest request,
                                             @RequestHeader("Authorization") String token) {
        try {
            String userEmail = jwtTokenProvider.getEmail(token.replace("Bearer ", ""));
            User user = userService.getUserByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

            ProductResponse updated = productService.updateMyProduct(id, request, user);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(403).body("수정 권한이 없습니다: " + e.getMessage());
        }
    }
}