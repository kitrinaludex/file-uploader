package io.github.kitrinaludex.file_uploader.controller;

import io.github.kitrinaludex.file_uploader.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileViewController {

    @Autowired
    ViewService viewService;

    @GetMapping("/files")
    public ResponseEntity<?> getFolder(@RequestParam String folder){
        return ResponseEntity.ok(viewService.getFolder(folder));
    }



}
