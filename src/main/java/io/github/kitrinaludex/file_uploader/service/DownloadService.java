package io.github.kitrinaludex.file_uploader.service;

import io.github.kitrinaludex.file_uploader.exception.FileNotFoundException;
import io.github.kitrinaludex.file_uploader.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;

@Service
public class DownloadService {
    @Value("${uploadDirectory}")
    private String uploadDirectory;

    @Autowired
    UserService userService;
    @Autowired
    FileRepository fileRepository;


    public Resource download(String uuid) throws MalformedURLException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        String path = fileRepository.getFilePath(username,uuid)
                .orElseThrow(() -> new FileNotFoundException("File not found"));

        Path pathToFile = Path.of(uploadDirectory + "/" +
                userService.getRootUuid(username) + "/" + path + uuid);

        return new UrlResource(pathToFile.toUri());

    }

}
