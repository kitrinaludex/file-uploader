package io.github.kitrinaludex.file_uploader.service;

import io.github.kitrinaludex.file_uploader.dto.SignupRequest;
import io.github.kitrinaludex.file_uploader.exception.FolderCreationException;
import io.github.kitrinaludex.file_uploader.exception.UsernameAlreadyExistsException;
import io.github.kitrinaludex.file_uploader.model.User;
import io.github.kitrinaludex.file_uploader.repository.FileRepository;
import io.github.kitrinaludex.file_uploader.repository.PermissionRepository;
import io.github.kitrinaludex.file_uploader.repository.UserRepository;
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
    FileRepository fileRepository;
    private final PasswordEncoder passwordEncoder;
    PermissionRepository permissionRepository;

    public UserService(FileRepository fileRepository, PasswordEncoder passwordEncoder, PermissionRepository permissionRepository,UserRepository userRepository) {
        this.fileRepository = fileRepository;
        this.passwordEncoder = passwordEncoder;
        this.permissionRepository = permissionRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> registerUser(SignupRequest signupRequest) throws Exception {
        if (userRepository.exists(signupRequest.getUsername())) {
            throw new UsernameAlreadyExistsException("username already exists");
        }

        String rootUuid = UUID.randomUUID().toString();
        try {
            new File(uploadDirectory + "/" + rootUuid).mkdir();
            permissionRepository.giveAccessToFolder(signupRequest.getUsername(),rootUuid);
        }catch (Exception e) {
            throw new FolderCreationException("couldn't create user root folder");
        }
        fileRepository.createRoot(signupRequest.getUsername(),"My files",
                rootUuid,"","/" );

        User user = new User(
                UUID.randomUUID().toString(),
          passwordEncoder.encode(signupRequest.getPassword()),rootUuid,
            signupRequest.getUsername()
    );

        userRepository.save(user);
        return ResponseEntity.ok("Registration successful");

    }


    public String getRootUuid(String username) {
        return userRepository.getUserByUsername(username).getRootUuid();
    }
}
