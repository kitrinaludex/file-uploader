package io.github.kitrinaludex.file_uploader.dto;

public class UserFile {
    private String name;
    private String url;
    private String uuid;

    public UserFile(String name, String url,String uuid) {
        this.name = name;
        this.uuid = uuid;
        this.url = url;
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
