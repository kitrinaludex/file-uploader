package io.github.kitrinaludex.file_uploader.service;

import io.github.kitrinaludex.file_uploader.dto.SignupRequest;
import io.github.kitrinaludex.file_uploader.model.User;
import io.github.kitrinaludex.file_uploader.repository.FileRepository;
import io.github.kitrinaludex.file_uploader.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.UUID;

@Service
public class UserService {
    @Value("${uploadDirectory}")
    private String uploadDirectory;

    private final UserRepository userRepository;

    @Autowired
    FileRepository fileRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UploadService   uploadService;


    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> registerUser(SignupRequest signupRequest) throws Exception {
        if (userRepository.exists(signupRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }

        String rootUuid = UUID.randomUUID().toString();
        try {
            new File(uploadDirectory + "/" + rootUuid).mkdir();
        }catch (Exception e) {
            ResponseEntity.badRequest().body("couldnt create directory");
        }
        fileRepository.createRoot(signupRequest.getUsername(),"My files",
                rootUuid,"","/" );

        User user = new User(
            1,passwordEncoder.encode(signupRequest.getPassword()),rootUuid,
            signupRequest.getUsername()
    );

        userRepository.save(user);
        return ResponseEntity.ok("Registration successful");

    }


    public String getRootUuid(String username) {
        return userRepository.getUserByUsername(username).getRootUuid();
    }
}
