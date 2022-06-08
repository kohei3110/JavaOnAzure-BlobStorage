package com.kohei3110.javaonazureblobdemo.UploadBlob.service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.logging.Logger;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.sas.BlobContainerSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;

public class GetSasService {

    Logger logger = Logger.getLogger(GetSasService.class.getName());

    public String getSasUrl(String accountName, String containerName, String sas) {
        return String.format("https://%s.blob.core.windows.net/%s?%s",accountName, containerName, sas);
    }

    public String getSas(BlobContainerClient client, BlobServiceSasSignatureValues sasValues) {
        return client.generateSas(sasValues);
    }

    public BlobServiceSasSignatureValues getSasValues() {
        BlobContainerSasPermission permission = new BlobContainerSasPermission()
            .setReadPermission(false)
            .setWritePermission(true)
            .setAddPermission(false)
            .setCreatePermission(true)
            .setDeletePermission(false);
 
        OffsetDateTime expiryTime = OffsetDateTime.now().plus(Duration.ofMinutes(5));
 
        BlobServiceSasSignatureValues sasValues = new BlobServiceSasSignatureValues(expiryTime, permission);
        return sasValues;
    }
}
