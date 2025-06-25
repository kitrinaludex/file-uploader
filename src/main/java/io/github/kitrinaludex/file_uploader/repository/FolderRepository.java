package io.github.kitrinaludex.file_uploader.repository;

import io.github.kitrinaludex.file_uploader.model.Folder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class FolderRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void createFolder(String name,String uuid,String parentUuid,String path){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        String sql = "INSERT INTO folders(name,uuid,parent_uuid,path,owner) VALUES(?,?,?,?,?)";

        jdbcTemplate.update(sql,name,uuid,parentUuid,path,username);
    }

    public Optional<Folder> findByUuid(String uuid) {

        String sql = "SELECT * FROM folders WHERE uuid = ?";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new FolderMapper(), uuid));
        }catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
