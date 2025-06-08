package com.example.barofarm_backend.controller;

import com.example.barofarm_backend.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/reviews/upload")
@RequiredArgsConstructor
public class ReviewImageUploadController {

    private final FileUploadService fileUploadService; // 예: S3 또는 로컬 파일 저장 서비스

    @PostMapping("/image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        String imageUrl = fileUploadService.upload(file);
        return ResponseEntity.ok(imageUrl);
    }
}