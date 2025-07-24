package io.github.kitrinaludex.file_uploader.dto;

public class UserFile {
    private String name;
    private String type;
    private String uuid;

    public UserFile(String name, String type, String uuid) {
        this.name = name;
        this.type = type;
        this.uuid = uuid;
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
