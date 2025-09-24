package io.github.kitrinaludex.file_uploader.init;

import io.github.kitrinaludex.file_uploader.controller.AuthController;
import io.github.kitrinaludex.file_uploader.dto.SignupRequest;
import java.io.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;



@Component
public class StartupInitializer {
  @Value("${uploadDirectory}")
  private String uploadDirectory;

  private AuthController authController;

  public StartupInitializer(AuthController authController) {
    this.authController = authController;
  }

  @Bean
  public CommandLineRunner initDirectory() {
    return args -> {
      File dir = new File(uploadDirectory);
      if (!dir.exists()) {
        boolean created = dir.mkdirs();
        if (created) {
          System.out.println("Upload directory created");
        }
      }

    };
  }

  @Bean
  public CommandLineRunner initDemoUser() throws Exception {
    return args -> {
      SignupRequest signupRequest = new SignupRequest("demo","demo");
      if (authController.register(signupRequest).getStatusCode().is2xxSuccessful()){
        System.out.println("demo account created");
      }


    };

  }
}
