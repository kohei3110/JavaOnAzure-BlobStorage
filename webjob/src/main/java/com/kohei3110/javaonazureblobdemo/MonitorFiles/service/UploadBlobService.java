package com.kohei3110.javaonazureblobdemo.MonitorFiles.service;

import java.util.List;
import java.util.logging.Logger;

import com.kohei3110.javaonazureblobdemo.MonitorFiles.repository.UploadBlobRepository;

public class UploadBlobService {
    private UploadBlobRepository uploadBlobRepository;

    Logger logger = Logger.getLogger(UploadBlobService.class.getName());


    public UploadBlobService(UploadBlobRepository uploadBlobRepository) {
        this.uploadBlobRepository = uploadBlobRepository;
    }

    public void store(List<String> files) throws Exception {
        uploadBlobRepository.store(files);
    }    
}
