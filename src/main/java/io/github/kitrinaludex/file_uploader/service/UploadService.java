package io.github.kitrinaludex.file_uploader.service;

import io.github.kitrinaludex.file_uploader.model.Folder;
import io.github.kitrinaludex.file_uploader.repository.FileRepository;
import io.github.kitrinaludex.file_uploader.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UploadService {

    @Value("${uploadDirectory}")
    private String uploadDirectory;

    @Autowired
    FileRepository fileRepository;
    @Autowired
    UserRepository userRepository;

    public ResponseEntity<?> saveFile(MultipartFile file, String parentUuid) throws IOException {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        String rootUuid = fileRepository.getRootByUsername(username);

        String parentPath = "/";

        if (parentUuid == null) {
            parentUuid = rootUuid;
        }

        if (parentUuid != null) {
            Folder parent = fileRepository.findFolderByUuid(parentUuid).orElseThrow(()
                    -> new ResourceAccessException("Parent folder not found"));
            parentPath = parent.getPath();
        }

        String uuid = UUID.randomUUID().toString();

        try {

            file.transferTo(new File(uploadDirectory + rootUuid + parentPath + uuid));
            fileRepository.saveFile(file.getOriginalFilename(),uuid,username,parentUuid,parentPath);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }

        return ResponseEntity.ok("file uploaded");
    }

    public String createFolder(String name,String parentUuid) throws Exception {

        //todo:validate name
        //todo:check if user has access

        String uuid = java.util.UUID.randomUUID().toString();

        String parentPath = "/";
        if (parentUuid != null) {
            Folder parent = fileRepository.findFolderByUuid(parentUuid).orElseThrow(()
                    -> new ResourceAccessException("Parent folder not found"));
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            if (!(parent.getOwnerUuid().equals(userRepository.getUserByUsername(username).getUsername()))){
                throw new ResourceAccessException("Access Denied");
            }
            parentPath = parent.getPath();
        }



        if (parentUuid == null) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            parentUuid = fileRepository.getRootByUsername(username);
        }

        String newPath = parentPath + uuid + "/";

        if (name == null) {
            name = "new folder";
        }

        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            new File( uploadDirectory + fileRepository.getRootByUsername(username) + newPath).mkdir();

            fileRepository.createFolder(name,uuid,parentUuid,newPath);
        }catch (Exception e) {
            return "folder creation error";
        }

        return uuid;
}

}
