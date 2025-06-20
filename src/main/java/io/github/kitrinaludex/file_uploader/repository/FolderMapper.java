package io.github.kitrinaludex.file_uploader.repository;

import io.github.kitrinaludex.file_uploader.dto.UserFile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FolderMapper implements RowMapper<UserFile> {

    @Override
    public UserFile mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserFile(rs.getString("name"),"folder","a",rs.getString("uuid"));
    }
}
