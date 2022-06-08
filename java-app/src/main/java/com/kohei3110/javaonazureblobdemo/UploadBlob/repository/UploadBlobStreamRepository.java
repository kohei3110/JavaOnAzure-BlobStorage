package com.kohei3110.javaonazureblobdemo.UploadBlob.repository;

import java.io.ByteArrayInputStream;
import java.util.logging.Logger;

import com.azure.storage.blob.BlobAsyncClient;
import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.ProgressReceiver;
import com.azure.storage.blob.models.AccessTier;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.azure.storage.blob.models.BlobRequestConditions;
import com.azure.storage.blob.models.ParallelTransferOptions;
import com.azure.storage.blob.specialized.BlobOutputStream;

import org.springframework.web.multipart.MultipartFile;

public class UploadBlobStreamRepository {

    private static final String CONTAINER_NAME = "content";
    private String connectionString;
    
    Logger logger = Logger.getLogger(UploadBlobStreamRepository.class.getName());

    public UploadBlobStreamRepository(String connectionString) {
        this.connectionString = connectionString;
    }

    public void upload(MultipartFile file) throws Exception {
        // stream upload
        BlobAsyncClient blobAsyncClient = buildBlobClient(file.getOriginalFilename(), this.connectionString);
        ParallelTransferOptions options = new ParallelTransferOptions()
            .setBlockSizeLong((long) (2 * 1024 * 1024))  // blockSize
            .setMaxConcurrency(2)
            .setProgressReceiver(new ProgressReceiver() {

                @Override
                public void reportProgress(long bytesTransferred) {
                    logger.info("uploaded: " + bytesTransferred);
                }
                
            });
            BlobHttpHeaders headers = new BlobHttpHeaders().setContentLanguage("ja-JP").setContentType("binary");
        BlobOutputStream blobOutputStream = BlobOutputStream.blockBlobOutputStream(
            blobAsyncClient, 
            options, 
            headers, 
            null, 
            AccessTier.HOT, 
            new BlobRequestConditions(), 
            null
        );
        ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getBytes());
        int next = inputStream.read();
        while (next != -1) {
            blobOutputStream.write(next);
            next = inputStream.read();
        }

        blobOutputStream.close();
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
