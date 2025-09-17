package io.github.kitrinaludex.file_uploader.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class UserFile {
    private String name;
    private String type;
    private String uuid;
    private long fileSize;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Timestamp creationDate;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Timestamp editDate;

    public UserFile(String name, String type, String uuid,Timestamp creationDate,Timestamp editDate,Long fileSize) {
        this.name = name;
        this.type = type;
        this.uuid = uuid;
        this.creationDate = creationDate;
        this.editDate = editDate;
        this.fileSize = fileSize;
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

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public void setEditDate(Timestamp editDate) {
        this.editDate = editDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

}
