package io.github.kitrinaludex.file_uploader.controller;

import io.github.kitrinaludex.file_uploader.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;

@RestController
public class UploadController {

    @Autowired
    UploadService uploadService;

    @PostMapping("/files")
    public ResponseEntity<?> uploadFile(@RequestParam("file")MultipartFile
                                    file, @RequestParam(required = false, value="parent")String parentUuid) throws IOException {

        return uploadService.uploadFile(file,parentUuid);
    }

    @GetMapping("/files")
    public ResponseEntity<?> getFolder(@RequestParam(required = false,value = "folderUuid") String uuid) {

        return ResponseEntity.ok(uploadService.getFolder(uuid));
    }

    @PostMapping("/folders")
    public ResponseEntity<?> createFolder(@RequestParam String name,@RequestParam(required = false) String parentUuid){
        uploadService.createFolder(name,parentUuid);
       return ResponseEntity.ok("folder created");
    }


}
