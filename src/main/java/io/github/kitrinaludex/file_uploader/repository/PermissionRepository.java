package io.github.kitrinaludex.file_uploader.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class PermissionRepository {

    JdbcTemplate jdbcTemplate;

    public PermissionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean hasAccessToFolder(String folderUuid) {

        while (folderUuid != null) {

            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            String sql = "SELECT EXISTS ( SELECT * FROM folder_permissions WHERE username = ? AND folder_uuid = ? )";

            if (Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, username, folderUuid))) {
                return true;
            }

            String sql2 = "SELECT parent_uuid FROM folders WHERE uuid = ?";
            folderUuid = jdbcTemplate.queryForObject(sql2,String.class,folderUuid);
        }

        return false;
    }

    public void giveAccessToFolder(String username,String folderUuid) {

        String sql = "INSERT INTO folder_permissions(username,folder_uuid) VALUES(?,?)";

        jdbcTemplate.update(sql,username,folderUuid);
    }
}
