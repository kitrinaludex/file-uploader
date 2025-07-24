package io.github.kitrinaludex.file_uploader.controller;

import io.github.kitrinaludex.file_uploader.service.DownloadService;
import io.github.kitrinaludex.file_uploader.service.UserService;
import java.net.MalformedURLException;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DownloadController {

    DownloadService downloadService;
    UserService userService;

    public DownloadController(DownloadService downloadService,UserService userService) {
        this.downloadService = downloadService;
        this.userService = userService;
    }

    @GetMapping("/download/{fileUuid}")
    public Resource downloadFile(@PathVariable String fileUuid) throws MalformedURLException {
        return downloadService.download(fileUuid);
    }
}
