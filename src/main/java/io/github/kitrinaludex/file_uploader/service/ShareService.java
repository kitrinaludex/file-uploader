package io.github.kitrinaludex.file_uploader.service;

import io.github.kitrinaludex.file_uploader.exception.InvalidTokenException;
import io.github.kitrinaludex.file_uploader.exception.NoFolderAccessException;
import io.github.kitrinaludex.file_uploader.model.ShareLink;
import io.github.kitrinaludex.file_uploader.repository.PermissionRepository;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ShareService {

  private final PermissionRepository permissionRepository;

  public ShareService(PermissionRepository permissionRepository) {
    this.permissionRepository = permissionRepository;
  }

  public ShareLink createShareLink(String folderUuid) {

    if (!permissionRepository.hasAccessToFolder(folderUuid)) {
      throw new NoFolderAccessException("You don't have permissions to share this folder");
    }
    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    ShareLink shareLink = new ShareLink();
    shareLink.setToken(generateToken());
    shareLink.setFolderUuid(folderUuid);
    shareLink.setCreatedBy(username);
    shareLink.setCreatedAt(LocalDateTime.now());

    permissionRepository.saveShareLink(shareLink);

    return shareLink;

  }

  private String generateToken() {
    final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    final int TOKEN_LENGTH = 8;

    SecureRandom random = new SecureRandom();
    StringBuilder token = new StringBuilder(TOKEN_LENGTH);

    for (int i = 0; i < TOKEN_LENGTH; i++) {
      int index = random.nextInt(CHARACTERS.length());
      token.append(CHARACTERS.charAt(index));
    }

    if (permissionRepository.tokenExists(String.valueOf(token))) {
      return generateToken();
    }
    return String.valueOf(token);
  }


  public void receiveInvite(String token) {

    if (!permissionRepository.tokenExists(token)) {
      throw new InvalidTokenException("Token does not exist");
    }

    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    ShareLink shareLink = permissionRepository.getShareLink(token);

    permissionRepository.giveAccessToFolder(username, shareLink.getFolderUuid());
  }

}
