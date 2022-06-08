package com.kohei3110.javaonazureblobdemo.MonitorFiles.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import com.azure.storage.blob.BlobAsyncClient;
import com.azure.storage.blob.BlobClientBuilder;
import com.kohei3110.javaonazureblobdemo.MonitorFiles.model.FileLocation;

public class UploadBlobRepository {
    private final Path rootLocation;
    private String connectionString;

    Logger logger = Logger.getLogger(UploadBlobRepository.class.getName());

    private static final String CONTAINER_NAME = "content";

    public UploadBlobRepository(FileLocation storageProperties, String connectionString) {
        this.rootLocation = Paths.get(storageProperties.getLocation());
        this.connectionString = connectionString;
    }

    public void store(List<String> files) throws Exception {
        for (String f: files) {
            Path path = Paths.get(f);
            logger.info(path.toString());
            BufferedReader br = new BufferedReader(new FileReader(path.toString()));
            try {
                if (br.readLine() == null) {
                throw new Exception("Upload file is empty.");
                }
                br.close();
                Path destinationFile = this.rootLocation.resolve(
                        Paths.get(path.getFileName().toString())
                    )
                    .normalize()
                    .toAbsolutePath();
                    
                // Blob Upload
                BlobAsyncClient blobAsyncClient = buildBlobClient(Paths.get(path.getFileName().toString()).toString());
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
            } catch (Exception e) {
                logger.warning(e.toString());
                throw new Exception("Blob upload has failed.");
            }
        }
    }
    private BlobAsyncClient buildBlobClient (String blobName) throws Exception {
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
