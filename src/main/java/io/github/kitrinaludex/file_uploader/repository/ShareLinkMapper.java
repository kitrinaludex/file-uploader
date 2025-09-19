package io.github.kitrinaludex.file_uploader.repository;

import io.github.kitrinaludex.file_uploader.model.ShareLink;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ShareLinkMapper implements RowMapper<ShareLink> {

  @Override
  public ShareLink mapRow(ResultSet rs, int rowNum) throws SQLException {
    ShareLink shareLink = new ShareLink();
//        shareLink.setUuid(rs.getString("uuid"));
//        folder.setName(rs.getString("name"));
//        folder.setOwnerUuid(rs.getString("owner"));
//        folder.setParentUuid(rs.getString("parent_uuid"));
//        folder.setPath(rs.getString("path"));
    shareLink.setFolderUuid(rs.getString("folder_uuid"));
    shareLink.setToken(rs.getString("token"));
    shareLink.setCreatedBy(rs.getString("created_by"));
    return shareLink;
  }
}
