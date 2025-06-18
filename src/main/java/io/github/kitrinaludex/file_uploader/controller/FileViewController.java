package io.github.kitrinaludex.file_uploader.controller;

import io.github.kitrinaludex.file_uploader.service.FileViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileViewController {

    @Autowired
    FileViewService fileViewService;

    @GetMapping("/files")
    public ResponseEntity<?> viewRootFiles() {
        return fileViewService.viewRoot();
    }
}
