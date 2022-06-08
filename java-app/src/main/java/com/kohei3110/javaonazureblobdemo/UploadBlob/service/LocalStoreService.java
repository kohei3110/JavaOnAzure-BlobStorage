package com.kohei3110.javaonazureblobdemo.UploadBlob.service;

import java.util.logging.Logger;

import com.kohei3110.javaonazureblobdemo.UploadBlob.repository.LocalStoreRepositoty;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LocalStoreService {

    private LocalStoreRepositoty localStoreRepository;

    Logger logger = Logger.getLogger(LocalStoreService.class.getName());

    public LocalStoreService(LocalStoreRepositoty localStoreRepository) {
        this.localStoreRepository = localStoreRepository;
    }

    public void init() throws Exception {
        localStoreRepository.init();
    }

    public void store(MultipartFile file) throws Exception {
        localStoreRepository.store(file);
    }
}
