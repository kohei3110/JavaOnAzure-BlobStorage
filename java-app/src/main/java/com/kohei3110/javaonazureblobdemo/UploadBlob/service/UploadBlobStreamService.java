package com.kohei3110.javaonazureblobdemo.UploadBlob.service;

import java.util.logging.Logger;

import com.kohei3110.javaonazureblobdemo.UploadBlob.repository.UploadBlobStreamRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadBlobStreamService {
    
    private UploadBlobStreamRepository uploadBlobStreamRepository;

    Logger logger = Logger.getLogger(UploadBlobStreamService.class.getName());

    public UploadBlobStreamService (UploadBlobStreamRepository uploadBlobStreamRepository) {
        this.uploadBlobStreamRepository = uploadBlobStreamRepository;
    }

    public void streamUpload(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new Exception("Upload file is empty.");
        }
        uploadBlobStreamRepository.upload(file);
    }

}
