package com.example.barofarm_backend.repository;

import com.example.barofarm_backend.entity.Order;
import com.example.barofarm_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 사용자 기준 주문 내역 조회
    List<Order> findByUser(User user);

    // 판매자(farmer)가 올린 상품에 대해 들어온 주문 조회 
    @Query("""
        SELECT DISTINCT o FROM Order o
        JOIN o.items i
        WHERE i.productId IN (
            SELECT p.productId FROM Product p WHERE p.user.id = :farmerId
        )
        ORDER BY o.orderedAt DESC
    """)
    List<Order> findOrdersByFarmerId(@Param("farmerId") Long farmerId);
}