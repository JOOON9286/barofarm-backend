package com.example.barofarm_backend.service;

import com.example.barofarm_backend.dto.response.ProductResponse;
import com.example.barofarm_backend.dto.request.ProductUpdateRequest;
import com.example.barofarm_backend.entity.Product;
import com.example.barofarm_backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품 ID " + id + "를 찾을 수 없습니다."));
    }

    public ProductResponse updateProduct(Long id, ProductUpdateRequest request) {
        Product product = getProductById(id);
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(request.getCategory());
        product.setImageUrl(request.getImageUrl());
        return new ProductResponse(productRepository.save(product));
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("상품 ID " + id + "가 존재하지 않습니다.");
        }
        productRepository.deleteById(id);
    }
}