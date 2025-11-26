package com.example.barofarm_backend.repository;

import com.example.barofarm_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<Object> findByName(String name);

    // 항상 id 기준 오름차순 정렬
    List<User> findAllByOrderByIdAsc();

    List<User> findByRole(String role);
}