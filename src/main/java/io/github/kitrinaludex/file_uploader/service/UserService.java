package io.github.kitrinaludex.file_uploader.service;

import io.github.kitrinaludex.file_uploader.dto.SignupRequest;
import io.github.kitrinaludex.file_uploader.model.User;
import io.github.kitrinaludex.file_uploader.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> registerUser(SignupRequest signupRequest) {
        if (userRepository.exists(signupRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }
    User user = new User(
            passwordEncoder.encode(signupRequest.getPassword()),
            signupRequest.getUsername()
    );
        userRepository.save(user);
        return ResponseEntity.ok("Registration successful");

    }
}
