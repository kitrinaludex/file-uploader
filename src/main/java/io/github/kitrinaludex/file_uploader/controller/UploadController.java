package io.github.kitrinaludex.file_uploader.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UploadController {
    @PostMapping("/upload")
    public ResponseEntity<?> upload() {

    }
}
