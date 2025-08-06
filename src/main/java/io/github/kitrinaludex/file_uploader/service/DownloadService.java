package io.github.kitrinaludex.file_uploader.service;

import io.github.kitrinaludex.file_uploader.exception.FileNotFoundException;
import io.github.kitrinaludex.file_uploader.exception.NoFolderAccessException;
import io.github.kitrinaludex.file_uploader.repository.FileRepository;
import io.github.kitrinaludex.file_uploader.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;

@Service
public class DownloadService {
    @Value("${uploadDirectory}")
    private String uploadDirectory;

    UserService userService;
    FileRepository fileRepository;
    PermissionRepository permissionRepository;

    public DownloadService(FileRepository fileRepository, UserService userService, PermissionRepository permissionRepository) {
        this.fileRepository = fileRepository;
        this.userService = userService;
        this.permissionRepository = permissionRepository;
    }

    public Resource download(String uuid) throws MalformedURLException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        String path = fileRepository.getFilePath(uuid)
                .orElseThrow(() -> new FileNotFoundException("File not found"));

        if (permissionRepository.hasAccessToFolder(fileRepository.getFileFolder(uuid).get())) {

            Path pathToFile = Path.of(uploadDirectory + "/" +
                    userService.getRootUuid(username) + "/" + path + uuid);

            return new UrlResource(pathToFile.toUri());
        }

        throw new NoFolderAccessException("No folder access");

    }

}
