package io.github.kitrinaludex.file_uploader.service;

import io.github.kitrinaludex.file_uploader.dto.UserFile;
import io.github.kitrinaludex.file_uploader.model.Folder;
import io.github.kitrinaludex.file_uploader.repository.FileRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ViewService {

    @Autowired
    FileRepository fileRepository;


    public List<UserFile> getFolder(String uuid) {

        if (uuid == null) {
            uuid = "";
        }

        List<Folder> folders = fileRepository.getFolderList(uuid);;
        List<UserFile> files = fileRepository.getFileList(uuid);

        List<UserFile> convertedFolders = new ArrayList<>();

        for (Folder folder: folders) {
            convertedFolders.add(folder.toDto());
        }

        convertedFolders.addAll(files);

        return convertedFolders;

    }
}
