package com.kohei3110.javaonazureblobdemo.MonitorFiles.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.kohei3110.javaonazureblobdemo.MonitorFiles.model.FileLocation;
import com.kohei3110.javaonazureblobdemo.MonitorFiles.repository.ListFilesRepository;

public class ListFilesService {
    
    private final Path rootLocation;
    private ListFilesRepository listFilesRepository;

    Logger logger = Logger.getLogger(ListFilesService.class.getName());

    public ListFilesService(FileLocation storageProperties, ListFilesRepository listFilesRepository) {
        this.rootLocation = Paths.get(storageProperties.getLocation());
        this.listFilesRepository = listFilesRepository;
    }

    public List<String> listFiles() throws IOException {
        Set<Path> files = listFilesRepository.listFiles(rootLocation.toString());

        // 最終更新日時が1分以内のファイルを取得
        LocalDateTime targetDate = LocalDateTime.now().minusMinutes(1);
        List<String> fileList = files.stream()
            .filter(file -> {
                    LocalDateTime lastModified;
                    try {
                        lastModified = LocalDateTime.ofInstant(
                            Files.getLastModifiedTime(file).toInstant(), 
                            ZoneId.systemDefault()
                        );
                        return lastModified.isAfter(targetDate);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return false;
            })
            .map(Path::toAbsolutePath)
            .map(Path::toString)
            .collect(Collectors.toList());
        return fileList;
    }
}
