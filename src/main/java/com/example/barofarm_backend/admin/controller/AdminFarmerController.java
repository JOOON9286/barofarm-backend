// 📁 admin/controller/AdminFarmerController.java (전체 코드)

package com.example.barofarm_backend.admin.controller;

import com.example.barofarm_backend.admin.dto.AdminFarmerDto;
import com.example.barofarm_backend.admin.service.AdminFarmerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/farmers")
@RequiredArgsConstructor
public class AdminFarmerController {

    private final AdminFarmerService adminFarmerService;

    // 1. '농부 관리' 메뉴용 (승인 완료 목록)
    @GetMapping("/approved")
    public ResponseEntity<List<AdminFarmerDto>> getApprovedFarmers() {
        return ResponseEntity.ok(adminFarmerService.getFarmersByVerificationStatus(true));
    }

    // 2. '농부 승인' 메뉴용 (승인 대기 목록)
    @GetMapping("/pending")
    public ResponseEntity<List<AdminFarmerDto>> getPendingFarmers() {
        return ResponseEntity.ok(adminFarmerService.getFarmersByVerificationStatus(false));
    }

    // 3. 농부 승인 처리 (POST /api/admin/farmers/approve/{farmerId})
    @PostMapping("/approve/{farmerId}")
    public ResponseEntity<Void> approveFarmer(@PathVariable Long farmerId) {
        adminFarmerService.approveFarmer(farmerId);
        return ResponseEntity.ok().build();
    }

    // 4. 농부 거절 처리 (POST /api/admin/farmers/reject/{farmerId})
    @PostMapping("/reject/{farmerId}")
    public ResponseEntity<Void> rejectFarmer(@PathVariable Long farmerId) {
        adminFarmerService.rejectFarmer(farmerId);
        return ResponseEntity.ok().build();
    }
}