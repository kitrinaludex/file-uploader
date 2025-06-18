package io.github.kitrinaludex.file_uploader.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class FileRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void save(String filename,String uuid) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        String sql = "INSERT INTO files(name,uuid,owner) VALUES(?,?,?)";
        jdbcTemplate.update(sql,filename,uuid,username);
    }
}
