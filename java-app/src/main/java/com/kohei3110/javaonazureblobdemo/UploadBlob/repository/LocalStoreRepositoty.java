package com.kohei3110.javaonazureblobdemo.UploadBlob.repository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;

import com.kohei3110.javaonazureblobdemo.UploadBlob.model.FileLocation;

import org.springframework.web.multipart.MultipartFile;

public class LocalStoreRepositoty {
    
    private final Path rootLocation;

    Logger logger = Logger.getLogger(LocalStoreRepositoty.class.getName());

    public LocalStoreRepositoty(FileLocation storageProperties) {
        this.rootLocation = Paths.get(storageProperties.getLocation());
    }

    public void init() throws Exception {
        try {
            Files.createDirectories(rootLocation);
        } catch (Exception e) {
            logger.warning(e.toString());
            throw new Exception("Create directory has failed.");
        }
    }

    // ローカル保存
    public void store(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new Exception("Upload file is empty.");
        }
        Path destinationFile = this.rootLocation.resolve(
                Paths.get(file.getOriginalFilename())
            )
            .normalize()
            .toAbsolutePath();
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile,
                StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new Exception("Failed to store file.", e);
        }
    }
}
