package com.example.barofarm_backend.admin.controller;

import com.example.barofarm_backend.admin.service.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/products") // 상품 관리
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminProductService adminProductService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        adminProductService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}