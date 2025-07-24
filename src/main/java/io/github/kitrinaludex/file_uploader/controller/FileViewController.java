package io.github.kitrinaludex.file_uploader.controller;

import io.github.kitrinaludex.file_uploader.service.UserService;
import io.github.kitrinaludex.file_uploader.service.ViewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileViewController {

    ViewService viewService;
    UserService userService;

    public FileViewController(UserService userService, ViewService viewService) {
        this.userService = userService;
        this.viewService = viewService;
    }

    @GetMapping("/files")
    public ResponseEntity<?> getFolder(@RequestParam(required = false) String folder){

        if (folder == null) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            folder = userService.getRootUuid(username);
        }
        return ResponseEntity.ok(viewService.getFolder(folder));
    }



}
