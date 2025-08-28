package io.github.kitrinaludex.file_uploader.repository;

import io.github.kitrinaludex.file_uploader.model.ShareLink;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;

@Repository
public class PermissionRepository {

    JdbcTemplate jdbcTemplate;

    public PermissionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean hasAccessToFolder(String folderUuid) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        while (folderUuid != null) {

            String sql = "SELECT EXISTS ( SELECT * FROM folder_permissions WHERE username = ? AND folder_uuid = ? )";
            System.out.println(folderUuid);
            if (Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, username, folderUuid))) {
                return true;
            }

            try {

                String sql2 = "SELECT parent_uuid FROM folders WHERE uuid = ?";
                folderUuid = jdbcTemplate.queryForObject(sql2,String.class,folderUuid);
            }catch (EmptyResultDataAccessException e) {
                return false;
            }
        }

        return true;
    }

    public void giveAccessToFolder(String username,String folderUuid) {

        String sql = "INSERT INTO folder_permissions(username,folder_uuid) VALUES(?,?)";

        jdbcTemplate.update(sql,username,folderUuid);
    }

    public boolean tokenExists(String token) {

        String sql = "SELECT EXISTS ( SELECT * FROM invite_links WHERE token = ?)";

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, token));
    }

    public void saveShareLink(ShareLink shareLink) {

        String sql = "INSERT INTO invite_links(token,created_by,folder_uuid)" +
                "VALUES (?,?,?)";
        jdbcTemplate.update(sql,shareLink.getToken(),shareLink.getCreatedBy(),
                shareLink.getFolderUuid());
    }

    public ShareLink getShareLink(String token) {

        String sql = "SELECT * FROM invite_links WHERE token = ?";
        return jdbcTemplate.queryForObject(sql,new ShareLinkMapper(),token);
    }
}
