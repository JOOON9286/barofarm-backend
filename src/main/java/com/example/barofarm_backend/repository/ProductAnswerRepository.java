package com.example.barofarm_backend.repository;

import com.example.barofarm_backend.entity.ProductAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductAnswerRepository extends JpaRepository<ProductAnswer, Long> {
    Optional<ProductAnswer> findByQuestionId(Long questionId);
}