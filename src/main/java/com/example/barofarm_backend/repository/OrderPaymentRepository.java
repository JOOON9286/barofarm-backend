package com.example.barofarm_backend.repository;

import com.example.barofarm_backend.entity.OrderPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;


import java.util.List;
import java.util.Optional;

public interface OrderPaymentRepository extends JpaRepository<OrderPayment, Long> {


    Optional<OrderPayment> findByOrderId(String orderId);
    List<OrderPayment> findByUserId(Long userId);

    @Query(value = "SELECT DATE_TRUNC('day', approved_at) AS date, SUM(amount) AS total " +
            "FROM order_payment " +
            "WHERE status = 'PAID' AND approved_at >= :startDate " +
            "GROUP BY date " +
            "ORDER BY date ASC", nativeQuery = true)
    List<Object[]> findDailySalesSince(@Param("startDate") Instant startDate);
}

    


