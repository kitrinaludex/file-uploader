package io.github.kitrinaludex.file_uploader.model;

public class User {
  public String uuid;
  public String rootUuid;
  private String username;
  private String password;

  public User(String password, String username) {
    this.password = password;
    this.username = username;
  }

  public User(String uuid, String password, String rootUuid, String username) {
    this.uuid = uuid;
    this.password = password;
    this.rootUuid = rootUuid;
    this.username = username;
  }

  public void setRootUuid(String rootUuid) {
    this.rootUuid = rootUuid;
  }

  public String getRootUuid() {
    return rootUuid;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public String getUsername() {
    return username;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getUuid() {
    return uuid;
  }
}
