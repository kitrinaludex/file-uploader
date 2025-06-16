package io.github.kitrinaludex.file_uploader.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
