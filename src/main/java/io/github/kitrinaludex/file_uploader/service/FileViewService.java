package io.github.kitrinaludex.file_uploader.service;

import io.github.kitrinaludex.file_uploader.dto.UserFile;
import io.github.kitrinaludex.file_uploader.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileViewService {

    @Autowired
    FileRepository fileRepository;

    public ResponseEntity<List<UserFile>> viewRoot() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return ResponseEntity.ok(fileRepository.getRoot(username));

    }
}
