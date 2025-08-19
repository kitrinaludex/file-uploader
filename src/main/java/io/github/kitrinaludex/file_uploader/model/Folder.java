package io.github.kitrinaludex.file_uploader.model;

import io.github.kitrinaludex.file_uploader.dto.UserFile;

public class Folder {

    private String uuid;
    private String name;
    private String parentUuid;
    private String ownerUuid;
    private String path;

    public Folder() {
    }

    public Folder(String name, String ownerUuid, String parentUuid, String path, String uuid) {
        this.name = name;
        this.ownerUuid = ownerUuid;
        this.parentUuid = parentUuid;
        this.path = path;
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwnerUuid(String ownerUuid) {
        this.ownerUuid = ownerUuid;
    }

    public void setParentUuid(String parentUuid) {
        this.parentUuid = parentUuid;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public String getOwnerUuid() {
        return ownerUuid;
    }

    public String getParentUuid() {
        return parentUuid;
    }

    public String getPath() {
        return path;
    }

    public String getUuid() {
        return uuid;
    }

    public UserFile toDto() {
        return new UserFile(getName(),"folder",getUuid());
    }
}
