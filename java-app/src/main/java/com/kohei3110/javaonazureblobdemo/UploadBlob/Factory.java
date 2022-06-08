package com.kohei3110.javaonazureblobdemo.UploadBlob;

import java.util.logging.Logger;

import com.kohei3110.javaonazureblobdemo.UploadBlob.model.FileLocation;
import com.kohei3110.javaonazureblobdemo.UploadBlob.repository.LocalStoreRepositoty;
import com.kohei3110.javaonazureblobdemo.UploadBlob.repository.UploadBlobRepository;
import com.kohei3110.javaonazureblobdemo.UploadBlob.repository.UploadBlobStreamRepository;
import com.kohei3110.javaonazureblobdemo.UploadBlob.service.GetSasService;
import com.kohei3110.javaonazureblobdemo.UploadBlob.service.LocalStoreService;
import com.kohei3110.javaonazureblobdemo.UploadBlob.service.UploadBlobService;
import com.kohei3110.javaonazureblobdemo.UploadBlob.service.UploadBlobStreamService;

public class Factory {

    Logger logger = Logger.getLogger(Factory.class.getName());

    private FileLocation fileLocation;
    private UploadBlobService uploadBlobService;
    private UploadBlobStreamService uploadBlobStreamService;
    private LocalStoreService localStoreService;
    private GetSasService getSasService;
    private UploadBlobRepository uploadBlobRepository;
    private UploadBlobStreamRepository uploadBlobStreamRepository;
    private LocalStoreRepositoty localStoreRepository;

    public Factory() {
        this.fileLocation = new FileLocation(getFileLocation());
        this.uploadBlobRepository = new UploadBlobRepository(this.fileLocation, getConnectionString());
        this.uploadBlobService = new UploadBlobService(this.uploadBlobRepository);
        this.uploadBlobStreamRepository = new UploadBlobStreamRepository(getConnectionString());
        this.uploadBlobStreamService = new UploadBlobStreamService(this.uploadBlobStreamRepository);
        this.localStoreRepository = new LocalStoreRepositoty(this.fileLocation);
        this.localStoreService = new LocalStoreService(this.localStoreRepository);
        this.getSasService = new GetSasService();
    }

    public UploadBlobService getUploadBlobServiceInstance() {
        return this.uploadBlobService;
    }

    public UploadBlobStreamService getUploadBlobStreamServiceInstance() {
        return this.uploadBlobStreamService;
    }

    public LocalStoreService getLocalStoreServiceInstance() {
        return this.localStoreService;
    }

    public GetSasService getSasServiceInstance() {
        return this.getSasService;
    }

    public String getConnectionString() {
        try {
            String connectionString = System.getenv("STORAGE_CONNECTION_STRING");
            return connectionString;
        } catch (Exception e) {
            logger.warning(e.toString());
            throw new Error("Get connectionstring operation has failed.");
        }
    }

    public String getFileLocation() {
        try {
            String fileLocation = System.getenv("FILE_LOCATION");
            return fileLocation;
        } catch (Exception e) {
            logger.warning(e.toString());
            throw new Error("Get filelocation operation has failed.");
        }
    }
}