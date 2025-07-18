package io.github.kitrinaludex.file_uploader.repository;

import io.github.kitrinaludex.file_uploader.dto.UserFile;
import io.github.kitrinaludex.file_uploader.model.Folder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FileRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void saveFile(String filename,String uuid,String username,String folderUuid,String path) {
        username = SecurityContextHolder.getContext().getAuthentication().getName();

        String sql = "INSERT INTO files(name,uuid,owner,folder,path) VALUES(?,?,?,?,?)";
        jdbcTemplate.update(sql,filename,uuid,username,folderUuid,path);
    }


    public void createFolder(String name,String uuid,String parentUuid,String path){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        String sql = "INSERT INTO folders(name,uuid,parent_uuid,path,owner) VALUES(?,?,?,?,?)";

        jdbcTemplate.update(sql,name,uuid,parentUuid,path,username);
    }

    public void createRoot(String username,String name,String uuid,String parentUuid,String path){

        String sql = "INSERT INTO folders(name,uuid,parent_uuid,path,owner) VALUES(?,?,?,?,?)";

        jdbcTemplate.update(sql,name,uuid,parentUuid,path,username);
    }

    public Optional<Folder> findFolderByUuid(String uuid) {

        String sql = "SELECT * FROM folders WHERE uuid = ?";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new FolderMapper(), uuid));
        }catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public String getRootByUsername(String username) {

        String sql = "SELECT root_folder_uuid FROM users WHERE username = ?";

        return jdbcTemplate.queryForObject(sql,String.class,username);
    }

    public List<Folder> getFolderList(String folderUuid) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        String sql = "SELECT * FROM folders WHERE owner = ? AND parent_uuid = ?";

        return jdbcTemplate.query(sql,new FolderMapper(),username,folderUuid);
    }

    public List<UserFile> getFileList(String folderUuid) {

        String sql = "SELECT * FROM files WHERE owner = ? AND folder = ?";

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return jdbcTemplate.query(sql,new FileMapper(),username,folderUuid);
    }

    public String getFilePath(String uuid) {

        String sql = "SELECT path FROM files WHERE uuid = ?";

        return jdbcTemplate.queryForObject(sql,String.class,uuid);
    }
}
