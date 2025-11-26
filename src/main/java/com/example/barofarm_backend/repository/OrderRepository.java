package com.example.barofarm_backend.repository;

import com.example.barofarm_backend.entity.Order;
import com.example.barofarm_backend.entity.User;
import com.example.barofarm_backend.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

    long countByStatus(OrderStatus status);
}