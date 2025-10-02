package com.example.barofarm_backend.service;

import com.example.barofarm_backend.dto.request.AdminRequestDto; // 경로 수정
import com.example.barofarm_backend.dto.response.AdminResponseDto; // 경로 수정
import com.example.barofarm_backend.entity.Admin;
import com.example.barofarm_backend.repository.AdminRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    // 1. 관리자 생성
    @Transactional
    public AdminResponseDto createAdmin(AdminRequestDto requestDto) {
        Admin admin = new Admin(
                requestDto.getUsername(),
                requestDto.getPassword(),
                requestDto.getEmail(),
                requestDto.getRole()
        );

        Admin savedAdmin = adminRepository.save(admin);
        return new AdminResponseDto(savedAdmin);
    }

    // 2. 특정 관리자 조회
    @Transactional(readOnly = true)
    public AdminResponseDto getAdminById(Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found with id: " + id));

        return new AdminResponseDto(admin);
    }

    // 3. 모든 관리자 조회
    @Transactional(readOnly = true)
    public List<AdminResponseDto> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(AdminResponseDto::new)
                .collect(Collectors.toList());
    }

    // 4. 관리자 업데이트
    @Transactional
    public AdminResponseDto updateAdmin(Long id, AdminRequestDto requestDto) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found with id: " + id));

        admin.update(requestDto.getUsername(), requestDto.getEmail(), requestDto.getRole());

        return new AdminResponseDto(admin);
    }

    // 5. 관리자 삭제
    @Transactional
    public void deleteAdmin(Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found with id: " + id));

        adminRepository.delete(admin);
    }
}