package com.example.barofarm_backend.service;

import com.example.barofarm_backend.entity.Farmer;
import com.example.barofarm_backend.dto.request.FarmerSignupRequest;
import com.example.barofarm_backend.dto.response.FarmerResponse;
import com.example.barofarm_backend.repository.FarmerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FarmerService {

    private final FarmerRepository farmerRepository;
    private final PasswordEncoder passwordEncoder;

    public FarmerResponse signup(FarmerSignupRequest request) {
        if (farmerRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 사용 중인 사용자명입니다.");
        }

        Farmer farmer = Farmer.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .phone(request.getPhone())
                .address(request.getAddress())
                .certificationNumber(request.getCertificationNumber())
                .isVerified(false)
                .build();

        Farmer saved = farmerRepository.save(farmer);

        return toResponse(saved);
    }

    // 로그인된 농부 정보 조회
    public FarmerResponse getFarmerInfo(String username) {
        Farmer farmer = farmerRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));
        return toResponse(farmer);
    }

    // 공통 응답 DTO 변환 로직
    private FarmerResponse toResponse(Farmer farmer) {
        return FarmerResponse.builder()
                .id(farmer.getId())
                .username(farmer.getUsername())
                .name(farmer.getName())
                .phone(farmer.getPhone())
                .address(farmer.getAddress())
                .certificationNumber(farmer.getCertificationNumber())
                .isVerified(farmer.getIsVerified())
                .build();
    }
}
