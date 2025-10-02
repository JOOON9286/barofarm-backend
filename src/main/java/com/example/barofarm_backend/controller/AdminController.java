package com.example.barofarm_backend.controller;

import com.example.barofarm_backend.dto.request.AdminRequestDto; // 경로 수정
import com.example.barofarm_backend.dto.response.AdminResponseDto; // 경로 수정
import com.example.barofarm_backend.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // 관리자 생성 (POST /api/admins)
    @PostMapping
    public ResponseEntity<AdminResponseDto> createAdmin(@RequestBody AdminRequestDto requestDto) {
        AdminResponseDto responseDto = adminService.createAdmin(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 특정 관리자 조회 (GET /api/admins/{id})
    @GetMapping("/{id}")
    public ResponseEntity<AdminResponseDto> getAdminById(@PathVariable Long id) {
        AdminResponseDto responseDto = adminService.getAdminById(id);
        return ResponseEntity.ok(responseDto);
    }

    // 모든 관리자 조회 (GET /api/admins)
    @GetMapping
    public ResponseEntity<List<AdminResponseDto>> getAllAdmins() {
        List<AdminResponseDto> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    // 관리자 정보 업데이트 (PUT /api/admins/{id})
    @PutMapping("/{id}")
    public ResponseEntity<AdminResponseDto> updateAdmin(@PathVariable Long id, @RequestBody AdminRequestDto requestDto) {
        AdminResponseDto responseDto = adminService.updateAdmin(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 관리자 삭제 (DELETE /api/admins/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }
}