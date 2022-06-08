package com.kohei3110.javaonazureblobdemo.MonitorFiles.model;

public class FileLocation {    
    // 【参考】https://docs.microsoft.com/ja-jp/azure/app-service/operating-system-functionality#file-access-across-multiple-instances
    private String location = "";

    public FileLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
}