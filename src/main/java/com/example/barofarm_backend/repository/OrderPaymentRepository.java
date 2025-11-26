package com.example.barofarm_backend.repository;

import com.example.barofarm_backend.entity.OrderPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderPaymentRepository extends JpaRepository<OrderPayment, Long> {
    Optional<OrderPayment> findByOrderId(String orderId);

    List<OrderPayment> findByUserId(Long userId);
}
