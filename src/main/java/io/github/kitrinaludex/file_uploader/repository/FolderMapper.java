package io.github.kitrinaludex.file_uploader.repository;

import io.github.kitrinaludex.file_uploader.model.Folder;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class FolderMapper implements RowMapper<Folder> {

  @Override
  public Folder mapRow(ResultSet rs, int rowNum) throws SQLException {
    Folder folder = new Folder();
    folder.setUuid(rs.getString("uuid"));
    folder.setName(rs.getString("name"));
    folder.setOwnerUuid(rs.getString("owner"));
    folder.setParentUuid(rs.getString("parent_uuid"));
    folder.setPath(rs.getString("path"));
    folder.setCreationDate(rs.getTimestamp("creation_date"));
    folder.setEditDate(rs.getTimestamp("edit_date"));
    return folder;
  }
}
