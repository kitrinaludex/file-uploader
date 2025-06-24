package io.github.kitrinaludex.file_uploader.repository;

import io.github.kitrinaludex.file_uploader.model.Folder;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FolderMapper implements RowMapper<Folder> {

    @Override
    public Folder mapRow(ResultSet rs, int rowNum) throws SQLException {
        Folder folder = new Folder();
        folder.setUuid(rs.getString("uuid"));
        folder.setName(rs.getString("name"));
        folder.setOwnerUuid(rs.getString("owner"));
        folder.setParentUuid(rs.getString("parent_uuid"));
        folder.setPath(rs.getString("path"));
        return folder;
    }
}
