package com.example.barofarm_backend.repository;

import com.example.barofarm_backend.entity.Product;
import com.example.barofarm_backend.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByUser(User user);
    List<Product> findByUserId(Long userId); // 판매자가 등록한 상품 찾기
}
    