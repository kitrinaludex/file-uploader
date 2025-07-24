package io.github.kitrinaludex.file_uploader.service;

import io.github.kitrinaludex.file_uploader.dto.FolderDto;
import io.github.kitrinaludex.file_uploader.dto.UserFile;
import io.github.kitrinaludex.file_uploader.exception.FolderCreationException;
import io.github.kitrinaludex.file_uploader.exception.NoFolderAccessException;
import io.github.kitrinaludex.file_uploader.model.Folder;
import io.github.kitrinaludex.file_uploader.repository.FileRepository;
import io.github.kitrinaludex.file_uploader.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ViewService {

    FileRepository fileRepository;

    public ViewService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public FolderDto getFolder(String uuid) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (uuid == null) {
           uuid = fileRepository.getRootByUsername(username);
        }



        Folder folder = fileRepository.findFolderByUuid(uuid)
                .orElseThrow(() -> new FolderCreationException("Folder not found"));



        if (!(folder.getOwnerUuid().equals(username))) {
            throw new NoFolderAccessException("No folder access");
        }

        String name = folder.getName();
        List<Folder> folders = fileRepository.getFolderList(uuid);;
        List<UserFile> files = fileRepository.getFileList(uuid);

        List<UserFile> convertedFolders = new ArrayList<>();

        for (Folder f: folders) {
            convertedFolders.add(f.toDto());
        }

        convertedFolders.addAll(files);

        return new FolderDto(name,convertedFolders);

    }

}
