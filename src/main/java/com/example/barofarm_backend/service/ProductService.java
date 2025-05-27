package com.example.barofarm_backend.service;

import com.example.barofarm_backend.dto.request.ProductRequest;
import com.example.barofarm_backend.dto.response.ProductResponse;
import com.example.barofarm_backend.entity.Product;
import com.example.barofarm_backend.entity.User;
import com.example.barofarm_backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest request, User user) {
        Product product = Product.builder()
                .productName(request.getProductName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .category(request.getCategory())
                .imageUrl(request.getImageUrl())
                .origin(request.getOrigin())
                .salesUnit(request.getSalesUnit())
                .weight(request.getWeight())
                .createdAt(LocalDateTime.now()) // auditing 적용시 생략 가능
                .updatedAt(LocalDateTime.now())
                .user(user)
                .build();

        Product saved = productRepository.save(product);

        return ProductResponse.builder()
                .productId(saved.getProductId())
                .productName(saved.getProductName())
                .description(saved.getDescription())
                .price(saved.getPrice())
                .stockQuantity(saved.getStockQuantity())
                .category(saved.getCategory())
                .imageUrl(saved.getImageUrl())
                .origin(saved.getOrigin())
                .salesUnit(saved.getSalesUnit())
                .weight(saved.getWeight())
                .createdAt(saved.getCreatedAt().toString())
                .updatedAt(saved.getUpdatedAt().toString())
                .userId(saved.getUser().getId())
                .build();
    }
}
