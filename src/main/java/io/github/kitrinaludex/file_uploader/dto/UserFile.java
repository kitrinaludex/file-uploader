package io.github.kitrinaludex.file_uploader.dto;

public class UserFile {
    private String name;
    private String type;
    private String url;
    private String uuid;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public UserFile(String name, String type, String url, String uuid) {
        this.name = name;
        this.type = type;
        this.url = url;
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

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
