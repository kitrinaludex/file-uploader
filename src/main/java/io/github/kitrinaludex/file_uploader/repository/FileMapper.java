package io.github.kitrinaludex.file_uploader.repository;

import io.github.kitrinaludex.file_uploader.dto.UserFile;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class FileMapper implements RowMapper<UserFile> {

  @Override
  public UserFile mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new UserFile(rs.getString("name"), "file", rs.getString("uuid"),
        rs.getTimestamp("creation_date"), rs.getTimestamp("edit_date"), rs.getLong("file_size"));
  }
}
