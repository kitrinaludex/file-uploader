package io.github.kitrinaludex.file_uploader.model;

import java.time.LocalDateTime;

public class ShareLink {
    private String token;
    private String folderUuid;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private String permissionLevel;
    private boolean isActive = true;

    public ShareLink(LocalDateTime expiresAt, String folderUuid, boolean isActive, String permissionLevel, String token) {
        this.expiresAt = expiresAt;
        this.folderUuid = folderUuid;
        this.isActive = isActive;
        this.permissionLevel = permissionLevel;
        this.token = token;
    }


    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setFolderUuid(String folderUuid) {
        this.folderUuid = folderUuid;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setPermissionLevel(String permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public String getFolderUuid() {
        return folderUuid;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getPermissionLevel() {
        return permissionLevel;
    }

    public String getToken() {
        return token;
    }


    public ShareLink() {
    }

    private boolean isValid() {
        return isActive;
    }
}
