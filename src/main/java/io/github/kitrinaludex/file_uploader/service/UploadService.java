package io.github.kitrinaludex.file_uploader.service;

import io.github.kitrinaludex.file_uploader.dto.UserFile;
import io.github.kitrinaludex.file_uploader.model.Folder;
import io.github.kitrinaludex.file_uploader.repository.FileRepository;
import io.github.kitrinaludex.file_uploader.repository.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UploadService {

    @Value("${uploadDirectory}")
    private String uploadDirectory;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    FolderRepository folderRepository;

    public ResponseEntity<?> uploadFile(MultipartFile file,String parentUuid) throws IOException {

        if (parentUuid == null) {
            parentUuid = "";
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String uuid = UUID.randomUUID().toString();
        String parentPath = "/";
        Optional<Folder> parent = folderRepository.findByUuid(parentUuid);
        try {
            if (parent.isPresent()) {
                parentPath = parent.get().getPath();
            }

            file.transferTo(new File(uploadDirectory + parentPath + uuid));
            fileRepository.save(file.getOriginalFilename(),uuid,username,parentUuid);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }

        return ResponseEntity.ok("file uploaded");
    }



    public String createFolder(String name,String parentUuid) throws Exception {

        //todo:validate name
        //todo:check if user has access

        String parentPath = "/";

        if (parentUuid != null) {
            Folder parent = folderRepository.findByUuid(parentUuid).orElseThrow(
                    () -> new Exception("parent folder not found")
            );
            parentPath = parent.getPath();
        }

        String uuid = UUID.randomUUID().toString();
        String newPath = parentPath + uuid + "/";
        File newFolder = new File(uploadDirectory + newPath);

        if (newFolder.mkdir()) {
            folderRepository.createFolder(name,uuid,parentUuid,newPath);
            return uuid;
        }else throw new Exception("asd;'ldas;l'asdl;'ads");


}

}
