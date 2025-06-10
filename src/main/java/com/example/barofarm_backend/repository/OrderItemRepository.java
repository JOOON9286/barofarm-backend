package com.example.barofarm_backend.repository;

import com.example.barofarm_backend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

        // 특정 판매자(user_id)가 등록한 상품들에 대한 OrderItem 조회
    List<OrderItem> findByProductIdIn(List<Long> productIds);


}
