package io.github.kitrinaludex.file_uploader.controller;

import io.github.kitrinaludex.file_uploader.dto.SignupRequest;
import io.github.kitrinaludex.file_uploader.service.JdbcUserDetailsService;
import io.github.kitrinaludex.file_uploader.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {

  UserService userService;
  JdbcUserDetailsService jdbcUserDetailsService;

  public AuthController(UserService userService, JdbcUserDetailsService jdbcUserDetailsService) {
    this.jdbcUserDetailsService = jdbcUserDetailsService;
    this.userService = userService;
  }

  @GetMapping("/login")
  public ResponseEntity<?> login() {
    jdbcUserDetailsService.loadUserByUsername(
        SecurityContextHolder.getContext().getAuthentication().getName());
    return ResponseEntity.ok().build();
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody SignupRequest signupRequest)
      throws Exception {
    return userService.registerUser(signupRequest);
  }
}
