package io.github.kitrinaludex.file_uploader.model;

import java.time.LocalDateTime;

public class ShareLink {
  private String token;
  private String folderUuid;
  private String createdBy;
  private LocalDateTime createdAt;

  public ShareLink(LocalDateTime createdAt, String createdBy, String folderUuid, String token) {
    this.createdAt = createdAt;
    this.createdBy = createdBy;
    this.folderUuid = folderUuid;
    this.token = token;
  }

  public ShareLink() {
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public String getFolderUuid() {
    return folderUuid;
  }

  public String getToken() {
    return token;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public void setFolderUuid(String folderUuid) {
    this.folderUuid = folderUuid;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
