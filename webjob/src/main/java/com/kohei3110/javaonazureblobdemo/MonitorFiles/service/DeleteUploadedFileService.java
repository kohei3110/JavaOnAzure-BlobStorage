package com.kohei3110.javaonazureblobdemo.MonitorFiles.service;

import java.io.IOException;
import java.util.logging.Logger;

import com.kohei3110.javaonazureblobdemo.MonitorFiles.repository.DeleteUploadedFileRepository;

public class DeleteUploadedFileService {
    private DeleteUploadedFileRepository deleteRepository;
    Logger logger = Logger.getLogger(DeleteUploadedFileService.class.getName());

    public DeleteUploadedFileService(DeleteUploadedFileRepository deleteRepository) {
        this.deleteRepository = deleteRepository;
    }

    public void deleteFiles() throws IOException {
        deleteRepository.deleteFiles();
    }
}