package com.example.barofarm_backend.repository;

import com.example.barofarm_backend.entity.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FarmerRepository extends JpaRepository<Farmer, Long> {

    Optional<Farmer> findByUsername(String username);

    boolean existsByUsername(String username);
}
