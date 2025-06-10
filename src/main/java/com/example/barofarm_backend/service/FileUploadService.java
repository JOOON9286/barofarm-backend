package com.example.barofarm_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileUploadService {

    private final String uploadDir = System.getProperty("user.dir") + "/uploads/review-images/";

    public String upload(MultipartFile file) {
        try {
            // 디렉토리 없으면 생성
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(uploadDir + filename);

            file.transferTo(dest);

            // 프론트에서 접근 가능한 URL 경로 리턴
            return "/uploads/review-images/" + filename;

        } catch (IOException e) {
            throw new RuntimeException("이미지 업로드 실패", e);
        }
    }
}
