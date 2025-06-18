package io.github.kitrinaludex.file_uploader.service;

import io.github.kitrinaludex.file_uploader.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadService {

    @Value("${uploadDirectory}")
    private String uploadDirectory;

    @Autowired
    FileRepository fileRepository;

    public ResponseEntity<?> uploadFile(MultipartFile file) throws IOException {

        String uuid = UUID.randomUUID().toString();
        file.transferTo(new File(uploadDirectory + uuid));
        fileRepository.save(file.getOriginalFilename(),uuid);

        return ResponseEntity.ok("file uploaded");
    }

}
