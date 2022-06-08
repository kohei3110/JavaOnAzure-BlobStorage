package com.kohei3110.javaonazureblobdemo.MonitorFiles.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import com.kohei3110.javaonazureblobdemo.MonitorFiles.model.FileLocation;

public class DeleteUploadedFileRepository {
    private final Path rootLocation;

    Logger logger = Logger.getLogger(DeleteUploadedFileRepository.class.getName());

    public DeleteUploadedFileRepository(FileLocation storageProperties) {
        this.rootLocation = Paths.get(storageProperties.getLocation());
    }

    public void deleteFiles() throws IOException {
        Files.walk(rootLocation.toFile().toPath(), 1)
            .forEach(file -> {
                try {
                    Files.delete(file);
                } catch (IOException e) {
                    logger.warning(e.getMessage());
                }
            });
    }
}
