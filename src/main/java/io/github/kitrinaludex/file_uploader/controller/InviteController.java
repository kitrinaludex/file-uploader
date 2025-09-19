package io.github.kitrinaludex.file_uploader.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InviteController {

  ShareService shareService;

  public InviteController(ShareService shareService) {
    this.shareService = shareService;
  }

  @GetMapping("/invite/{token}")
  private ResponseEntity<?> receiveInvite(@PathVariable String token) {

    shareService.receiveInvite(token);

    return ResponseEntity.ok().build();
  }

  @PostMapping("/invite/{folderUuid}")
  public ResponseEntity<?> createInvite(@PathVariable String folderUuid) {

    return ResponseEntity.ok().body(shareService.createShareLink(folderUuid).getToken());

  }
}
