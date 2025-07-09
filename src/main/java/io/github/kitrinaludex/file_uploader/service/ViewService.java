package io.github.kitrinaludex.file_uploader.service;

import io.github.kitrinaludex.file_uploader.dto.FolderDto;
import io.github.kitrinaludex.file_uploader.dto.UserFile;
import io.github.kitrinaludex.file_uploader.model.Folder;
import io.github.kitrinaludex.file_uploader.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ViewService {


    @Autowired
    FileRepository fileRepository;
    @Autowired
    JdbcUserDetailsService jdbcUserDetailsService;

    public FolderDto getFolder(String uuid) {

        String name = "testName";
        List<Folder> folders = fileRepository.getFolderList(uuid);;
        List<UserFile> files = fileRepository.getFileList(uuid);

        List<UserFile> convertedFolders = new ArrayList<>();

        for (Folder folder: folders) {
            convertedFolders.add(folder.toDto());
        }

        convertedFolders.addAll(files);

        return new FolderDto(name,convertedFolders);

    }

}
