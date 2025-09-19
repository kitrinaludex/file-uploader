package io.github.kitrinaludex.file_uploader.controller;

import io.github.kitrinaludex.file_uploader.service.UploadService;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class UploadController {

  UploadService uploadService;

  public UploadController(UploadService uploadService) {
    this.uploadService = uploadService;
    System.out.println("sexo");
  }

  @PostMapping("/files")
  public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile
                                          file, @RequestParam(required = false, value = "parent")
                                      String parentUuid) throws IOException {

    return uploadService.saveFile(file, parentUuid);
  }


  @PostMapping("/folders")
  public ResponseEntity<?> createFolder(@RequestParam String name,
                                        @RequestParam(required = false) String parentUuid)
      throws Exception {
    String uuid = uploadService.createFolder(name, parentUuid);

    return ResponseEntity.ok(uuid);
  }

  @PutMapping("/folders/{uuid}")
  public ResponseEntity<?> renameFolder(@PathVariable String uuid, @RequestParam String name) {
    uploadService.renameFolder(name, uuid);

    return ResponseEntity.ok().build();
  }

  @PutMapping("/files/{uuid}")
  public ResponseEntity<?> renameFile(@PathVariable String uuid, @RequestParam String name) {
    uploadService.renameFile(name, uuid);

    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/files/{uuid}")
  public ResponseEntity<?> deleteFile(@PathVariable String uuid) {

    uploadService.deleteFile(uuid);

    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/folders/{uuid}")
  public ResponseEntity<?> deleteFolder(@PathVariable String uuid) {

    uploadService.deleteFolder(uuid);

    return ResponseEntity.ok().build();
  }


}
