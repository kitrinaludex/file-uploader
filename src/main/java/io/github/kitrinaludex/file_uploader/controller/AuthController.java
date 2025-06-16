package io.github.kitrinaludex.file_uploader.controller;

import io.github.kitrinaludex.file_uploader.dto.SignupRequest;
import io.github.kitrinaludex.file_uploader.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest signupRequest) {
        return userService.registerUser(signupRequest);
    }
}
