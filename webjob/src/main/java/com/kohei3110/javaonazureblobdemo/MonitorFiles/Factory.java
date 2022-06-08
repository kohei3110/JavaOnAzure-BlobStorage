package com.kohei3110.javaonazureblobdemo.MonitorFiles;

import java.util.logging.Logger;

import com.kohei3110.javaonazureblobdemo.MonitorFiles.model.FileLocation;
import com.kohei3110.javaonazureblobdemo.MonitorFiles.repository.DeleteUploadedFileRepository;
import com.kohei3110.javaonazureblobdemo.MonitorFiles.repository.ListFilesRepository;
import com.kohei3110.javaonazureblobdemo.MonitorFiles.repository.UploadBlobRepository;
import com.kohei3110.javaonazureblobdemo.MonitorFiles.service.DeleteUploadedFileService;
import com.kohei3110.javaonazureblobdemo.MonitorFiles.service.ListFilesService;
import com.kohei3110.javaonazureblobdemo.MonitorFiles.service.UploadBlobService;


public class Factory {

    Logger logger = Logger.getLogger(Factory.class.getName());

    private FileLocation fileLocation;
    private UploadBlobService uploadBlobService;
    private UploadBlobRepository uploadBlobRepository;
    private ListFilesService listFilesService;
    private ListFilesRepository listFilesRepository;
    private DeleteUploadedFileService deleteFileService;
    private DeleteUploadedFileRepository deleteFileRepository;

    public Factory() {
        this.fileLocation = new FileLocation(getFileLocation());
        this.uploadBlobRepository = new UploadBlobRepository(this.fileLocation, getConnectionString());
        this.uploadBlobService = new UploadBlobService(this.uploadBlobRepository);
        this.deleteFileRepository = new DeleteUploadedFileRepository(this.fileLocation);
        this.deleteFileService = new DeleteUploadedFileService(this.deleteFileRepository);
        this.listFilesRepository = new ListFilesRepository();
        this.listFilesService = new ListFilesService(this.fileLocation, this.listFilesRepository);
    }

    public UploadBlobService getUploadBlobServiceInstance() {
        return this.uploadBlobService;
    }

    public ListFilesService getListFilesServiceInstance() {
        return this.listFilesService;
    }

    public DeleteUploadedFileService getDeleteFileServiceInstance() {
        return this.deleteFileService;
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
