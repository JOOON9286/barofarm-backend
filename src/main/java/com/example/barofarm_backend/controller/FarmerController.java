package com.example.barofarm_backend.controller;

import com.example.barofarm_backend.dto.request.FarmerSignupRequest;
import com.example.barofarm_backend.dto.response.FarmerResponse;
import com.example.barofarm_backend.service.FarmerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/farmers")
@RequiredArgsConstructor
public class FarmerController {

    private final FarmerService farmerService;

    @PostMapping("/signup")
    public ResponseEntity<FarmerResponse> signup(@RequestBody FarmerSignupRequest request) {
        FarmerResponse response = farmerService.signup(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/mypage")
    public ResponseEntity<FarmerResponse> getMyInfo(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        FarmerResponse response = farmerService.getFarmerInfo(username);
        return ResponseEntity.ok(response);
    }
}

