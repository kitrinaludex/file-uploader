package io.github.kitrinaludex.file_uploader.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class PermissionRepository {
@Autowired
    JdbcTemplate jdbcTemplate;

    public boolean hasAccessToFolder(String folderUuid) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        String sql = "SELECT 1 FROM folder_permissions WHERE username = ? AND folderUuid = ?";

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, username, folderUuid));
    }

    public void giveAccessToFolder(String folderUuid) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        String sql = "INSERT INTO permissions(username,folder_uuid) VALUES(?,?)";

        jdbcTemplate.update(sql,username,folderUuid);
    }
}
