package io.github.kitrinaludex.file_uploader.repository;

import io.github.kitrinaludex.file_uploader.dto.UserFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class FileRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void save(String filename,String uuid,String username,String folderUuid) {

        String sql = "INSERT INTO files(name,uuid,owner,folder) VALUES(?,?,?,?)";
        jdbcTemplate.update(sql,filename,uuid,username,folderUuid);
    }

    public List<UserFile> getRoot(String username) {

        String sql = "SELECT * FROM files WHERE owner = ? AND folder = ''";
        return jdbcTemplate.query(sql,new FileMapper(),username);
    }

    public void createFolder(String name, String username, String parentUuid) {

        String sql = "INSERT INTO folders(name,owner,uuid,parent_uuid) VALUES(?,?,?,?)";

        jdbcTemplate.update(sql,name,username,UUID.randomUUID(),parentUuid);
    }


}
