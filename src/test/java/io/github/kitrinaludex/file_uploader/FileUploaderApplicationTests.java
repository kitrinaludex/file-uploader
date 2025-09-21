package io.github.kitrinaludex.file_uploader;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.kitrinaludex.file_uploader.dto.SignupRequest;
import io.github.kitrinaludex.file_uploader.model.Folder;
import io.github.kitrinaludex.file_uploader.repository.FileRepository;
import io.github.kitrinaludex.file_uploader.repository.UserRepository;
import java.io.File;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileUploaderApplicationTests {

  @Autowired
  UserRepository userRepository;
  @Autowired
  FileRepository fileRepository;
  @Autowired
  private TestRestTemplate restTemplate;
  public static String testFolderUuid;

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

//          HttpHeaders headers = new HttpHeaders();
//          headers.setBasicAuth("demo","demo");
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
    //folders?name=coolfolder3&parentUuid=c5a5f8ee-146c-4858-a1dc-828a1a34517e
    public void canCreateFolders() {
      ResponseEntity<?> response = restTemplate.withBasicAuth("demo", "demo")
          .postForEntity("/folders?name=coolfolder", "", String.class);
      testFolderUuid = (String) response.getBody();
      System.out.println(testFolderUuid);
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
      System.out.println("nested uuid:" + testFolderUuid);
      ResponseEntity<?> response = restTemplate.withBasicAuth("demo", "demo")
          .postForEntity("/folders?name=coolfolder2&parentUuid=" + testFolderUuid, "", String.class);
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
            .put("/folders/"+ testFolderUuid + "?name=asdf", "");
      } catch (Exception e) {
        throw new RuntimeException(e);
      }

      assertThat(fileRepository.findFolderByUuid(testFolderUuid).get().getName()).isEqualTo("asdf");
    }

    @Test
    @Order(4)
    void canDeleteFolders() {
      try {
        restTemplate.withBasicAuth("demo", "demo").delete("/folders/" + testFolderUuid);
      }catch (Exception e) {
        throw new RuntimeException(e);
      }finally {
        assertThat(fileRepository.findFolderByUuid(testFolderUuid)).isNotPresent();
      }


    }

  }

  @Nested
  @Suite
  class FileTestSuite {

      void canUploadFiles() {


      }
  }

}

