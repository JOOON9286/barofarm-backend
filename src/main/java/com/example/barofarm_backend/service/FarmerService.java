package com.example.barofarm_backend.service;

import com.example.barofarm_backend.domain.Farmer;
import com.example.barofarm_backend.dto.request.FarmerSignupRequest;
import com.example.barofarm_backend.dto.response.FarmerResponse;
import com.example.barofarm_backend.repository.FarmerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FarmerService {

    private final FarmerRepository farmerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

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

        return FarmerResponse.builder()
                .id(saved.getId())
                .username(saved.getUsername())
                .name(saved.getName())
                .phone(saved.getPhone())
                .address(saved.getAddress())
                .certificationNumber(saved.getCertificationNumber())
                .isVerified(saved.getIsVerified())
                .build();
    }
}
