package io.github.kitrinaludex.file_uploader.controller;

import io.github.kitrinaludex.file_uploader.service.UploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UploadController {

    UploadService uploadService;

    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping("/files")
    public ResponseEntity<?> uploadFile(@RequestParam("file")MultipartFile
                                    file, @RequestParam(required = false, value="parent")String parentUuid) throws IOException {

        return uploadService.saveFile(file,parentUuid);
    }



    @PostMapping("/folders")
    public ResponseEntity<?> createFolder(@RequestParam String name,@RequestParam(required = false) String parentUuid) throws Exception {
       String uuid = uploadService.createFolder(name,parentUuid);

       return ResponseEntity.ok(uuid);
    }

    @PutMapping("/folders")
    public  ResponseEntity<?> renameFolder(@RequestParam String name,@RequestParam String uuid) {
        uploadService.renameFolder(name,uuid);

        return ResponseEntity.ok().build();
    }


}
