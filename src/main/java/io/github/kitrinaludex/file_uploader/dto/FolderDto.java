package io.github.kitrinaludex.file_uploader.dto;

import java.util.List;

public class FolderDto {
  String name;
  List<UserFile> fileList;

  public FolderDto(String name, List<UserFile> fileList) {
    this.name = name;
    this.fileList = fileList;
  }

  public String getName() {
    return name;
  }

  public List<UserFile> getFileList() {
    return fileList;
  }


}
