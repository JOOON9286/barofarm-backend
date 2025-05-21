package com.example.barofarm_backend.repository;

import com.example.barofarm_backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

//    // 카테고리별 상품 조회
//    List<Product> findByCategory(String category);
//
//    // 이름에 특정 키워드가 포함된 상품 검색
//    List<Product> findByNameContaining(String keyword);
//
//    // 재고가 0보다 큰 상품만 조회
//    List<Product> findByStockQuantityGreaterThan(int quantity);
}
