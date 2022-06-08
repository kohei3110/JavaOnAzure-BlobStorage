package com.kohei3110.javaonazureblobdemo.UploadBlob.service;

import java.util.logging.Logger;

import com.kohei3110.javaonazureblobdemo.UploadBlob.repository.UploadBlobRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadBlobService {

    private UploadBlobRepository uploadBlobRepository;

    public UploadBlobService(UploadBlobRepository uploadBlobRepository) {
        this.uploadBlobRepository = uploadBlobRepository;
    }

    Logger logger = Logger.getLogger(UploadBlobService.class.getName());

    public void init() throws Exception {
        uploadBlobRepository.init();
    }

    public void store(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new Exception("Upload file is empty.");
        }
        uploadBlobRepository.uploadBlob(file);
    }    
}
