package io.github.kitrinaludex.file_uploader.service;

import io.github.kitrinaludex.file_uploader.dto.UserFile;
import io.github.kitrinaludex.file_uploader.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class UploadService {

    @Value("${uploadDirectory}")
    private String uploadDirectory;

    @Autowired
    FileRepository fileRepository;

    public ResponseEntity<?> uploadFile(MultipartFile file,String parentUuid) throws IOException {

        if (parentUuid == null) {
            parentUuid = "";
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String uuid = UUID.randomUUID().toString();
        file.transferTo(new File(uploadDirectory + uuid));
        fileRepository.save(file.getOriginalFilename(),uuid,username,parentUuid);

        return ResponseEntity.ok("file uploaded");
    }

    public void createFolder(String name,String parentUuid){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (parentUuid == null) {
            parentUuid = "";
        }

        fileRepository.createFolder(name,username,parentUuid);
    }


    public List<UserFile> getFolder(String uuid) {
        if (uuid == null) {
            uuid = "";
        }
        return fileRepository.getFolder(uuid);
    }
}
