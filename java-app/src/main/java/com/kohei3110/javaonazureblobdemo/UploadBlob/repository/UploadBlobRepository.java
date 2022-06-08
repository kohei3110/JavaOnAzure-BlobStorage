package com.kohei3110.javaonazureblobdemo.UploadBlob.repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import com.azure.storage.blob.BlobAsyncClient;
import com.azure.storage.blob.BlobClientBuilder;
import com.kohei3110.javaonazureblobdemo.UploadBlob.model.FileLocation;
import org.springframework.web.multipart.MultipartFile;

public class UploadBlobRepository {
    
    private BlobAsyncClient blobAsyncClient;
    private final Path rootLocation;
    private String connectionString;

    public UploadBlobRepository (FileLocation fileLocation, String connectionString) {
        this.rootLocation = Paths.get(fileLocation.getLocation());
        this.connectionString = connectionString;
    }

    private static final String CONTAINER_NAME = "content";
    Logger logger = Logger.getLogger(UploadBlobRepository.class.getName());

    public void init() throws Exception {
        try {
            Files.createDirectories(rootLocation);
        } catch (Exception e) {
            logger.warning(e.toString());
            throw new Exception("Create directory has failed.");
        }
    }

    public void uploadBlob(MultipartFile file) throws Exception {
        try {
            this.blobAsyncClient = buildBlobClient(Paths.get(file.getOriginalFilename().toString()).toString(), this.connectionString);
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new Exception("Blob Upload operation has failed.");
        }
        Path destinationFile = this.rootLocation.resolve(
            Paths.get(file.getOriginalFilename())
        )
        .normalize()
        .toAbsolutePath();
        blobAsyncClient.uploadFromFile(destinationFile.toString(), true)
            .doOnError(throwable -> {
                logger.warning(throwable.getMessage());
            })
            .doFirst(() -> {
                logger.info("Called 3rd");
            })
            .doOnSuccess(onSuccess -> {
                logger.info("Upload succeeded!");
            })
            .doFirst(() -> {
                logger.info("Called 2nd");
            })
            .doFirst(() -> {
                logger.info("Called 1st");
            })
            .doFinally(onFinally -> {
                logger.info("onFinally");
                logger.info(onFinally.name());
            })
            .subscribe(completion -> {
                logger.info("subscribe!");
            });    
    }

    private BlobAsyncClient buildBlobClient (String blobName, String connectionString) throws Exception {
        try {
            BlobAsyncClient blobAsyncClient = new BlobClientBuilder()
                .connectionString(connectionString)
                .containerName(CONTAINER_NAME)
                .blobName(blobName)
                .buildAsyncClient();
            
            return blobAsyncClient;
        } catch (Exception e) {
            logger.warning("Build blobAsyncClient has failed.");
            throw new Exception(e.toString());
        }
    }
}
