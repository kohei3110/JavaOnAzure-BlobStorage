package com.kohei3110.javaonazureblobdemo;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import com.kohei3110.javaonazureblobdemo.UploadBlob.Factory;
import com.kohei3110.javaonazureblobdemo.UploadBlob.service.GetSasService;
import com.kohei3110.javaonazureblobdemo.UploadBlob.service.LocalStoreService;
import com.kohei3110.javaonazureblobdemo.UploadBlob.service.UploadBlobService;
import com.kohei3110.javaonazureblobdemo.UploadBlob.service.UploadBlobStreamService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * コントローラクラス。
 */
@SpringBootApplication
@RestController
@ComponentScan("{com.kohei3110.javaonazureblobdemo.UploadBlob}")
public class Controller {

    private static final String CONTAINER_NAME = "content";

	Logger logger = Logger.getLogger(Controller.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(Controller.class, args);
	}

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestBody MultipartFile multipartFile) throws Exception {
        try {
            LocalStoreService localStoreService = injectLocalStoreService();
            UploadBlobService uploadBlobService = injectUploadBlobService();
            localStoreService.init();
            localStoreService.store(multipartFile);
            uploadBlobService.store(multipartFile);
            return ResponseEntity.status(HttpStatus.OK).body("upload succeeded");
        } catch (Exception e) {
            logger.warning(e.toString());
            throw new Exception("Upload operation has failed.");
        }
    }

    @PostMapping("/uploadstream")
    public ResponseEntity<String> uploadstream(@RequestBody MultipartFile multipartFile) throws Exception {
        try {
            UploadBlobStreamService uploadBlobStreamService = injectUploadBlobStreamService();
            uploadBlobStreamService.streamUpload(multipartFile);
            return ResponseEntity.status(HttpStatus.OK).body("upload succeeded");
        } catch (Exception e) {
            logger.warning(e.toString());
            throw new Exception("Upload operation has failed.");
        }
    }

    @PostMapping("/uploadschedule")
    public ResponseEntity<String> uploadschedule(@RequestBody MultipartFile multipartFile) throws Exception {
        try {
            LocalStoreService localStoreService = injectLocalStoreService();
            localStoreService.init();
            localStoreService.store(multipartFile);
            return ResponseEntity.status(HttpStatus.OK).body("upload succeeded");
        } catch (Exception e) {
            logger.warning(e.toString());
            throw new Exception("Upload operation has failed.");
        }
    }

    @GetMapping("/sas")
    public String getSas() throws Exception {
        try {
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(factory.getConnectionString())
                .buildClient();
            BlobContainerClient blobContainerClient = blobServiceClient
                .getBlobContainerClient(CONTAINER_NAME);
            GetSasService getSasService = injectGetSasService();
            BlobServiceSasSignatureValues sasValues = getSasService.getSasValues();
            String accountName = blobContainerClient.getAccountName();
            String sas = blobContainerClient.generateSas(sasValues);
            return getSasService.getSasUrl(accountName, CONTAINER_NAME, sas);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new Exception("Get sas operation has failed.");
        }
    }

    Factory factory = new Factory();

    LocalStoreService injectLocalStoreService() {
        return factory.getLocalStoreServiceInstance();
    }

    UploadBlobService injectUploadBlobService() {
        return factory.getUploadBlobServiceInstance();
    }

    UploadBlobStreamService injectUploadBlobStreamService() {
        return factory.getUploadBlobStreamServiceInstance();
    }

    GetSasService injectGetSasService() {
        return factory.getSasServiceInstance();
    }
}