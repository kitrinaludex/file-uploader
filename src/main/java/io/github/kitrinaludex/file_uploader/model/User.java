package io.github.kitrinaludex.file_uploader.model;

public class User {
    private long id;
    private String username;
    private String password;

    public User(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public User(long id, String password, String username) {
        this.id = id;
        this.password = password;
        this.username = username;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
