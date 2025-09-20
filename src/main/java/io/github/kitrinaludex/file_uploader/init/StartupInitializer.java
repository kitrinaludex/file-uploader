package io.github.kitrinaludex.file_uploader.init;

import java.io.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;



@Component
public class StartupInitializer {
  @Value("${uploadDirectory}")
  private String uploadDirectory;


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
}
