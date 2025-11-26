package com.example.barofarm_backend.repository; // ⭐️ 경로가 변경되었습니다.

import com.example.barofarm_backend.entity.B2BRequest;
import com.example.barofarm_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface B2BRequestRepository extends JpaRepository<B2BRequest, Long> {
    // 특정 회원의 B2B 신청 내역 조회
    List<B2BRequest> findByUser(User user);
}