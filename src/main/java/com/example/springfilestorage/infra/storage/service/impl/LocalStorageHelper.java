package com.example.springfilestorage.infra.storage.service.impl;

import com.example.springfilestorage.infra.storage.service.FileStorageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
public class LocalStorageHelper implements FileStorageHelper {

    @Override
    public String uploadFile(MultipartFile file, String filePath, String fileName) {
        Path uploadDir = Paths.get(filePath).toAbsolutePath().normalize();
        Path targetLocation = uploadDir.resolve(fileName);

        try (InputStream inputStream = file.getInputStream()) {
            // 디렉토리 생성 (이미 존재해도 예외 발생 안함)
            Files.createDirectories(uploadDir);

            // 파일 저장 (기존 파일이 있을 경우 덮어쓰기)
            Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return targetLocation.toString();
        } catch (IOException e) {
            log.error(">>> 파일 업로드 에러: {}", e.getMessage());
            throw new UncheckedIOException("파일 업로드 실패: " + targetLocation, e);
        }
    }

    @Override
    public byte[] getFileBytes(String filePath, String fileName) {
        Path fileLocation = Paths.get(filePath, fileName).toAbsolutePath();

        try {
            return Files.readAllBytes(fileLocation);
        } catch (IOException e) {
            log.error(">>> 파일 읽기 에러: {}", e.getMessage());
            throw new UncheckedIOException("파일 읽기 실패: " + fileLocation, e);
        }
    }

    @Override
    public void deleteFile(String filePath, String fileName) {
        Path fileLocation = Paths.get(filePath, fileName).toAbsolutePath();

        try {
            Files.deleteIfExists(fileLocation);
        } catch (IOException e) {
            log.error(">>> 파일 삭제 에러: {}", e.getMessage());
            throw new UncheckedIOException("파일 삭제 실패: " + fileLocation, e);
        }
    }
}
