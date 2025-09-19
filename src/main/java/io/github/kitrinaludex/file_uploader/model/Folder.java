package io.github.kitrinaludex.file_uploader.model;

import io.github.kitrinaludex.file_uploader.dto.UserFile;
import java.sql.Timestamp;

public class Folder {

  private String uuid;
  private String name;
  private String parentUuid;
  private String ownerUuid;
  private String path;
  private Timestamp creationDate;
  private Timestamp editDate;

  public Folder() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getOwnerUuid() {
    return ownerUuid;
  }

  public void setOwnerUuid(String ownerUuid) {
    this.ownerUuid = ownerUuid;
  }

  public String getParentUuid() {
    return parentUuid;
  }

  public void setParentUuid(String parentUuid) {
    this.parentUuid = parentUuid;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public Timestamp getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Timestamp creationDate) {
    this.creationDate = creationDate;
  }

  public Timestamp getEditDate() {
    return editDate;
  }

  public void setEditDate(Timestamp editDate) {
    this.editDate = editDate;
  }

  public UserFile toDto() {
    return new UserFile(getName(), "folder", getUuid(), getCreationDate(), getEditDate(), 0L);
  }
}
