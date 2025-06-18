package io.github.kitrinaludex.file_uploader.repository;

import io.github.kitrinaludex.file_uploader.dto.UserFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FileRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void save(String filename,String uuid,String username) {

        String sql = "INSERT INTO files(name,uuid,owner,folder) VALUES(?,?,?,'/')";
        jdbcTemplate.update(sql,filename,uuid,username);
    }

    public List<UserFile> getRoot(String username) {

        String sql = "SELECT * FROM files WHERE owner = ? AND folder = '/'";
        return jdbcTemplate.query(sql,new FileMapper(),username);
    }
}
