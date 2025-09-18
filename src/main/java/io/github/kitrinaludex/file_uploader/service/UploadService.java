package io.github.kitrinaludex.file_uploader.service;

import io.github.kitrinaludex.file_uploader.dto.UserFile;
import io.github.kitrinaludex.file_uploader.exception.FileDeletionException;
import io.github.kitrinaludex.file_uploader.exception.FileNotFoundException;
import io.github.kitrinaludex.file_uploader.exception.FolderNotFoundException;
import io.github.kitrinaludex.file_uploader.exception.NoFolderAccessException;
import io.github.kitrinaludex.file_uploader.model.Folder;
import io.github.kitrinaludex.file_uploader.repository.FileRepository;
import io.github.kitrinaludex.file_uploader.repository.PermissionRepository;
import io.github.kitrinaludex.file_uploader.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class UploadService {

    @Value("${uploadDirectory}")
    private String uploadDirectory;

    FileRepository fileRepository;
    UserRepository userRepository;
    PermissionRepository permissionRepository;

    public UploadService(FileRepository fileRepository, PermissionRepository permissionRepository,UserRepository userRepository) {
        this.fileRepository = fileRepository;
        this.permissionRepository = permissionRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> saveFile(MultipartFile file, String parentUuid) throws IOException {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        String rootUuid = fileRepository.getRootByUsername(username);

        String parentPath = "/";

        if (parentUuid == null) {
            parentUuid = rootUuid;
        }

        if (parentUuid != null) {
            Folder parent = fileRepository.findFolderByUuid(parentUuid).orElseThrow(()
                    -> new FolderNotFoundException("Parent folder not found"));

            if (!(permissionRepository.hasAccessToFolder(parentUuid))) {
                throw new NoFolderAccessException("No access to parent folder");
            }

            parentPath = parent.getPath();
        }

        String uuid = UUID.randomUUID().toString();

        try {

            file.transferTo(new File(uploadDirectory + rootUuid + parentPath + uuid));
            fileRepository.saveFile(file.getOriginalFilename(),uuid,username,parentUuid,parentPath,file.getSize());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok("file uploaded");
    }

    public String createFolder(String name,String parentUuid) throws Exception {

        String uuid = java.util.UUID.randomUUID().toString();

        String parentPath = "/";

        if (parentUuid != null) {

            Folder parent = fileRepository.findFolderByUuid(parentUuid).orElseThrow(()
                    -> new FolderNotFoundException("Parent folder not found"));

            if (!(permissionRepository.hasAccessToFolder(parentUuid))) {
                    throw new NoFolderAccessException("No access to parent folder");
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
            System.out.println(e.getMessage());
        }

        return uuid;
}

    public void renameFolder(String name,String uuid) {
        if (!(permissionRepository.hasAccessToFolder(uuid))) {
            throw new NoFolderAccessException("No access to folder");
        }

        fileRepository.renameFolder(name,uuid);

        //if folder exists and user has access, rename it
    }

    public void renameFile(String name,String uuid) {
        if (!(permissionRepository.hasAccessToFolder(fileRepository.getFileFolder(uuid).get()))) {
            throw new NoFolderAccessException("No access to folder");
        }

        fileRepository.renameFile(name,uuid);
    }

    public void deleteFile(String uuid) {

        String parentUuid = fileRepository.getFileFolder(uuid).orElseThrow(()
                -> new FileNotFoundException("File not found"));

        if (!(permissionRepository.hasAccessToFolder(parentUuid))) {
            throw new NoFolderAccessException("No access to folder");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        File fileToDelete = new File(uploadDirectory + fileRepository.getRootByUsername(username) + "/" + fileRepository.getFilePath(uuid).get() + uuid);
        System.out.println(uploadDirectory + fileRepository.getRootByUsername(username) + "/" + fileRepository.getFilePath(uuid).get() + uuid);

        System.out.println(fileToDelete.canRead());

        if (fileToDelete.delete()) {
            fileRepository.deleteFile(uuid);
        }else {
            throw new FileDeletionException("unable to delete file");
        }

    }

    public void deleteFolder(String uuid) {

        if (!(permissionRepository.hasAccessToFolder(uuid))) {
            throw new NoFolderAccessException("No access to folder");
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        File fileToDelete = new File(uploadDirectory + fileRepository.getRootByUsername(username) + "/" + fileRepository.getFolderPath(uuid).get());

        if (fileToDelete.delete()) {
            fileRepository.deleteFolder(uuid);
        }else {
            throw new FileDeletionException("unable to delete folder");
        }
    }




 }
