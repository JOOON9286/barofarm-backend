package com.example.barofarm_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileUploadService {
    private final String uploadDir = "uploads/review-images/";

    public String upload(MultipartFile file) {
        try {
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(uploadDir + filename);
            dest.getParentFile().mkdirs();
            file.transferTo(dest);
            return "/" + uploadDir + filename;
        } catch (IOException e) {
            throw new RuntimeException("이미지 업로드 실패", e);
        }
    }
}