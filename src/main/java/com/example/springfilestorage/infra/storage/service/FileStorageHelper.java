package com.example.springfilestorage.infra.storage.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageHelper {
    String uploadFile(MultipartFile file, String filePath, String fileName);

    byte[] getFileBytes(String filePath, String fileName);

    void deleteFile(String filePath, String fileName);
}
