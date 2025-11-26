package com.example.barofarm_backend.repository;

import com.example.barofarm_backend.entity.B2BRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface B2BRequestRepository extends JpaRepository<B2BRequest, Long> {
    List<B2BRequest> findByUserId(Long userId);
}
