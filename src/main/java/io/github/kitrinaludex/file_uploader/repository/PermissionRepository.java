package io.github.kitrinaludex.file_uploader.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class PermissionRepository {
@Autowired
    JdbcTemplate jdbcTemplate;

    public boolean hasAccessToFolder(String folderUuid) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();


        String sql = "SELECT EXISTS ( SELECT * FROM folder_permissions WHERE username = ? AND folder_uuid = ? )";

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, username, folderUuid));
    }

    public void giveAccessToFolder(String username,String folderUuid) {

        String sql = "INSERT INTO folder_permissions(username,folder_uuid) VALUES(?,?)";

        jdbcTemplate.update(sql,username,folderUuid);
    }
}
