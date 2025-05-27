package com.example.barofarm_backend.controller;

import com.example.barofarm_backend.dto.response.ProductResponse;
import com.example.barofarm_backend.dto.request.ProductUpdateRequest;
import com.example.barofarm_backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "전체 상품 조회")
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(summary = "상품 단건 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(new ProductResponse(productService.getProductById(id)));
    }

    @Operation(summary = "상품 수정")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
                                                         @RequestBody ProductUpdateRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @Operation(summary = "상품 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}