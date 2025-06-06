package com.example.barofarm_backend.repository;

import com.example.barofarm_backend.entity.ProductQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductQuestionRepository extends JpaRepository<ProductQuestion, Long> {
    List<ProductQuestion> findByProductProductId(Long productId);
}