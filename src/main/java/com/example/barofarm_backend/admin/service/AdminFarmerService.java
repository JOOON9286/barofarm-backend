// 📁 admin/service/AdminFarmerService.java (전체 코드)

package com.example.barofarm_backend.admin.service;

import com.example.barofarm_backend.admin.dto.AdminFarmerDto;
import com.example.barofarm_backend.entity.Farmer;
import com.example.barofarm_backend.entity.User;
import com.example.barofarm_backend.repository.FarmerRepository;
import com.example.barofarm_backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminFarmerService {

    private final FarmerRepository farmerRepository; // ⭐️ Farmer 엔티티 기반
    private final UserRepository userRepository;

    // 1. 승인 상태에 따른 농부 목록 조회
    @Transactional(readOnly = true)
    public List<AdminFarmerDto> getFarmersByVerificationStatus(boolean isVerified) {

        // isVerified 상태에 따라 Farmer 엔티티 조회
        List<Farmer> farmers = farmerRepository.findByIsVerified(isVerified);

        return farmers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 2. 농부 승인 처리 (isVerified = true, User role = ROLE_FARMER)
    @Transactional
    public void approveFarmer(Long farmerId) {
        Farmer farmer = farmerRepository.findById(farmerId)
                .orElseThrow(() -> new EntityNotFoundException("농부 정보를 찾을 수 없습니다. ID: " + farmerId));

        // 1. Farmer 엔티티 승인 상태 변경
        farmer.setIsVerified(true);

        // 2. User 엔티티의 권한을 ROLE_FARMER로 변경 (로그인 권한 부여)
        User user = farmer.getUser();
        user.setRole("ROLE_FARMER");

        farmerRepository.save(farmer);
        userRepository.save(user); // User 정보도 업데이트
    }

    // 3. 농부 거절 처리 (isVerified = false, User role = ROLE_USER로 강등)
    @Transactional
    public void rejectFarmer(Long farmerId) {
        Farmer farmer = farmerRepository.findById(farmerId)
                .orElseThrow(() -> new EntityNotFoundException("농부 정보를 찾을 수 없습니다. ID: " + farmerId));

        // 1. Farmer 엔티티 승인 상태 변경
        farmer.setIsVerified(false);

        // 2. User 권한을 일반 유저로 강등 (ROLE_USER가 기본 권한이라 가정)
        User user = farmer.getUser();
        user.setRole("ROLE_USER");

        farmerRepository.save(farmer);
        userRepository.save(user);
    }

    // DTO 변환 헬퍼 메서드
    private AdminFarmerDto convertToDto(Farmer farmer) {
        User user = farmer.getUser();
        return AdminFarmerDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .farmerId(farmer.getId())
                .certificationNumber(farmer.getCertificationNumber())
                .description(farmer.getDescription())
                .isVerified(farmer.getIsVerified())
                .build();
    }
}