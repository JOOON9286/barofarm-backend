package com.example.barofarm_backend.service;

import com.example.barofarm_backend.dto.request.ProductRequest;
import com.example.barofarm_backend.dto.response.ProductResponse;
import com.example.barofarm_backend.entity.Farmer;
import com.example.barofarm_backend.entity.Product;
import com.example.barofarm_backend.entity.User;
import com.example.barofarm_backend.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest request, User user) {
        Product product = Product.builder()
                .productName(request.getProductName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stockQuantity(Integer.parseInt(request.getStockQuantity()))
                .category(request.getCategory())
                .imageUrl(request.getImageUrl())
                .origin(request.getOrigin())
                .salesUnit(request.getSalesUnit())
                .weight(request.getWeight())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .user(user)
                .build();

        Product saved = productRepository.save(product);

        return toResponse(saved);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품 ID " + id + "를 찾을 수 없습니다."));
        return toResponse(product);
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품 ID " + id + "를 찾을 수 없습니다."));

        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(Integer.parseInt(request.getStockQuantity()));
        product.setCategory(request.getCategory());
        product.setImageUrl(request.getImageUrl());
        product.setOrigin(request.getOrigin());
        product.setSalesUnit(request.getSalesUnit());
        product.setWeight(request.getWeight());
        product.setUpdatedAt(LocalDateTime.now());

        return toResponse(productRepository.save(product));
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("상품 ID " + id + "가 존재하지 않습니다.");
        }
        productRepository.deleteById(id);
    }

    private ProductResponse toResponse(Product product) {
        User user = product.getUser();

        // user.getFarmer() 가 null일 수 있으니 null 체크
        String farmerDescription = (user.getFarmer() != null) ? user.getFarmer().getDescription() : null;

        return ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(String.valueOf(product.getStockQuantity()))
                .category(product.getCategory())
                .imageUrl(product.getImageUrl())
                .origin(product.getOrigin())
                .salesUnit(product.getSalesUnit())
                .weight(product.getWeight())
                .createdAt(product.getCreatedAt().toString())
                .updatedAt(product.getUpdatedAt().toString())
                .userId(user.getId())
                .userName(user.getName())  // 등록 농부 이름 추가
                .farmerDescription(farmerDescription)  // 농부 설명 추가
                .build();
    }


    public List<ProductResponse> getProductsByUser(User user) {
        return productRepository.findByUser(user).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse updateMyProduct(Long id, ProductRequest request, User user) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품 ID " + id + "를 찾을 수 없습니다."));

        if (!product.getUser().getId().equals(user.getId())) {
            throw new SecurityException("본인의 상품만 수정할 수 있습니다.");
        }

        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(Integer.parseInt(request.getStockQuantity()));
        product.setCategory(request.getCategory());
        product.setImageUrl(request.getImageUrl());
        product.setOrigin(request.getOrigin());
        product.setSalesUnit(request.getSalesUnit());
        product.setWeight(request.getWeight());
        product.setUpdatedAt(LocalDateTime.now());

        return toResponse(productRepository.save(product));
    }
}