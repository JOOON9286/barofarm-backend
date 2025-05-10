package com.example.barofarm_backend.controller;

import com.example.barofarm_backend.dto.request.FarmerSignupRequest;
import com.example.barofarm_backend.dto.response.FarmerResponse;
import com.example.barofarm_backend.service.FarmerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
}
