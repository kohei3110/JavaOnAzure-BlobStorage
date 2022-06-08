package com.kohei3110.javaonazureblobdemo.MonitorFiles.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListFilesRepository {
    public Set<Path> listFiles (String dir) throws IOException {
        try (Stream<Path> stream = Files.walk(Paths.get(dir), 1)) {
            return stream
                .filter(file -> !Files.isDirectory(file))
                .map(Path::toAbsolutePath)
                .collect(Collectors.toSet());
        }
    }
}