package com.example.barofarm_backend.repository;

import com.example.barofarm_backend.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    // 사용자 이름(username)을 통해 Admin 엔티티를 찾는 사용자 정의 메서드
    // Service에서 중복 확인이나 특정 관리자 조회 시 활용 가능
    Optional<Admin> findByUsername(String username);
}