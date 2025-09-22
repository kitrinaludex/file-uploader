package io.github.kitrinaludex.file_uploader;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.kitrinaludex.file_uploader.dto.SignupRequest;
import io.github.kitrinaludex.file_uploader.model.Folder;
import io.github.kitrinaludex.file_uploader.repository.FileRepository;
import io.github.kitrinaludex.file_uploader.repository.UserRepository;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.platform.suite.api.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileUploaderApplicationTests {

  public static String testFolderUuid;
  @Autowired
  UserRepository userRepository;
  @Autowired
  FileRepository fileRepository;
  @Autowired
  private TestRestTemplate restTemplate;
  @Value("${uploadDirectory}")
  private String uploadDirectory;

  @Test
  void contextLoads() {

  }


  @Nested
  @Suite
  class AuthTestSuite {
    @Test

    public void canLoginSuccessfully() {

      assertThat(restTemplate.withBasicAuth("demo", "demo")
          .getForEntity("/login", String.class).getStatusCode()).isEqualTo(
          HttpStatusCode.valueOf(200));

    }

    @Test
    public void canRegisterSuccessfully() {

      SignupRequest signupRequest = new SignupRequest("zxcqwe123", "asdfzx");
      assertThat(restTemplate
          .postForEntity("/register", signupRequest, String.class).getStatusCode()).isEqualTo(
          HttpStatusCode.valueOf(200));

      assertThat(userRepository.getUserByUsername("asdfzx")).isNotNull();
    }

  }

  @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
  @Nested
  @Suite
  class FolderTestSuite {

    @Test
    @Order(1)
    public void canCreateFolders() {
      ResponseEntity<?> response = restTemplate.withBasicAuth("demo", "demo")
          .postForEntity("/folders?name=coolfolder", "", String.class);
      testFolderUuid = (String) response.getBody();
      assertThat(response.getStatusCode())
          .isEqualTo(HttpStatusCode.valueOf(200)); //should be 201
      assertThat(fileRepository.findFolderByUuid(testFolderUuid)).isPresent();
      File folder = new File(uploadDirectory + fileRepository.getRootByUsername("demo") +
          "/" + testFolderUuid);
      assertThat(folder.isDirectory()).isTrue();
    }

    @Test
    @Order(2)
    public void canCreateNestedFolders() {
      ResponseEntity<?> response = restTemplate.withBasicAuth("demo", "demo")
          .postForEntity("/folders?name=coolfolder2&parentUuid=" + testFolderUuid, "",
              String.class);
      assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200)); //should be 201
      Optional<Folder> createdFolder = fileRepository.findFolderByUuid((String) response.getBody());

      assertThat(createdFolder).isPresent();
      assertThat(createdFolder.get().getParentUuid()).isEqualTo(testFolderUuid);
      File folder = new File(uploadDirectory + fileRepository.getRootByUsername("demo") +
          "/" + testFolderUuid + "/" + createdFolder.get().getUuid());
      assertThat(folder.isDirectory()).isTrue();
    }

    @Test
    @Order(3)
    void canRenameFolders() {

      try {
        restTemplate.withBasicAuth("demo", "demo")
            .put("/folders/" + testFolderUuid + "?name=asdf", "");
      } catch (Exception e) {
        throw new RuntimeException(e);
      }

      assertThat(fileRepository.findFolderByUuid(testFolderUuid).get().getName()).isEqualTo("asdf");
    }

    @Test
    @Order(4)
    void canDeleteFolders() {
      String folderUuid;

      ResponseEntity<?> response = restTemplate.withBasicAuth("demo", "demo")
          .postForEntity("/folders?name=testfolder", "", String.class);
      folderUuid = (String) response.getBody();

      try {
        restTemplate.withBasicAuth("demo", "demo").delete("/folders/" + folderUuid);
      } catch (Exception e) {
        throw new RuntimeException(e);
      } finally {
        assertThat(fileRepository.findFolderByUuid(folderUuid)).isNotPresent();
      }
    }

    @Test
    @Order(5)
    void canDeleteFoldersWithFiles() {
      try {
        restTemplate.withBasicAuth("demo", "demo").delete("/folders/" + testFolderUuid);
      } catch (Exception e) {
        throw new RuntimeException(e);
      } finally {
        assertThat(fileRepository.findFolderByUuid(testFolderUuid)).isNotPresent();
      }
    }
  }

  @Nested
  @Suite
  class FileTestSuite {

    @Test
    void canUploadFiles() throws IOException {
      File tempFile = Files.createTempFile("testfile", ".bin").toFile();
      try (FileOutputStream fos = new FileOutputStream(tempFile)) {
        byte[] buffer = new byte[1024]; //1kb
        for (int i = 0; i < 999; i++) {
          fos.write(buffer);
        }

      }
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.MULTIPART_FORM_DATA);
      headers.setBasicAuth("demo", "demo");

      MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
      FileSystemResource fileResource = new FileSystemResource(tempFile);
      body.add("file", fileResource);
      HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

      ResponseEntity<String> response =
          restTemplate.postForEntity("/files", requestEntity, String.class);
      assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
      tempFile.deleteOnExit();
    }

    @Test
    void cantUploadFilesOverLimit() throws IOException {
      File tempFile = Files.createTempFile("testfile2", ".bin").toFile();
      try (FileOutputStream fos = new FileOutputStream(tempFile)) {
        byte[] buffer = new byte[1024]; //1kb
        for (int i = 0; i < 11000; i++) { // 10000 * 1kb = 10mb
          fos.write(buffer);
        }

      }
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.MULTIPART_FORM_DATA);
      headers.setBasicAuth("demo", "demo");

      MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
      FileSystemResource fileResource = new FileSystemResource(tempFile);
      body.add("file", fileResource);
      HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

      ResponseEntity<String> response =
          restTemplate.postForEntity("/files", requestEntity, String.class);
      assertThat(response.getStatusCode().is4xxClientError()).isTrue();
      tempFile.deleteOnExit();
    }

  }
}

