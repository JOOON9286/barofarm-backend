package com.example.barofarm_backend.controller;

import com.example.barofarm_backend.entity.B2BRequest;
import com.example.barofarm_backend.repository.B2BRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/b2b")
@RequiredArgsConstructor
public class B2BRequestController {

    private final B2BRequestRepository b2bRequestRepository;

    // B2B 요청 등록
    @PostMapping("/submit")
    public ResponseEntity<?> submitB2BRequest(@RequestBody B2BRequest request) {
        b2bRequestRepository.save(request);
        return ResponseEntity.ok("B2B 요청이 저장되었습니다.");
    }

    // 모든 B2B 요청 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<B2BRequest>> getAllRequests() {
        return ResponseEntity.ok(b2bRequestRepository.findAll());
    }

    // B2B 요청 거절
    @PatchMapping("/reject/{id}")
    public ResponseEntity<?> rejectB2BRequest(@PathVariable Long id) {
        return b2bRequestRepository.findById(id)
                .map(request -> {
                    request.setStatus("rejected");
                    b2bRequestRepository.save(request);
                    return ResponseEntity.ok("거절 처리 완료");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 승인 처리
    @PatchMapping("/approve/{id}")
    public ResponseEntity<?> approveB2BRequest(@PathVariable Long id) {
        return b2bRequestRepository.findById(id)
                .map(request -> {
                    request.setStatus("approved");
                    b2bRequestRepository.save(request);
                    return ResponseEntity.ok("승인 처리 완료");
                })
                .orElse(ResponseEntity.notFound().build());
    }


}