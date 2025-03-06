package com.example.springfilestorage.infra.storage.service.impl;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocalStorageHelperTest {

    private LocalStorageHelper localStorageHelper;

    @TempDir
    Path tempDir; // JUnit이 테스트 실행 시 임시 디렉토리를 생성해줌

    @BeforeEach
    void setUp() {
        localStorageHelper = new LocalStorageHelper();
    }

    @Test
    @DisplayName("파일을 정상적으로 업로드할 수 있어야 한다")
    void uploadFile_ShouldSaveFile() throws IOException {
        // given
        String fileName = "testFile.txt";
        String content = "Hello, this is a test file!";
        MultipartFile mockFile = new MockMultipartFile(fileName, fileName, "text/plain", content.getBytes());

        // when
        String uploadedFilePath = localStorageHelper.uploadFile(mockFile, tempDir.toString(), fileName);

        // then
        Path uploadedFile = Paths.get(uploadedFilePath);
        assertTrue(Files.exists(uploadedFile), "파일이 업로드되지 않았습니다.");
        assertEquals(content, Files.readString(uploadedFile), "파일 내용이 다릅니다.");
    }

    @Test
    @DisplayName("저장된 파일을 바이트 배열로 읽어올 수 있어야 한다")
    void getFileBytes_ShouldReturnFileContents() throws IOException {
        // given
        String fileName = "testFile.txt";
        String content = "File content for testing";
        Path filePath = tempDir.resolve(fileName);
        Files.write(filePath, content.getBytes());

        // when
        byte[] fileBytes = localStorageHelper.getFileBytes(tempDir.toString(), fileName);

        // then
        assertNotNull(fileBytes, "파일이 null로 반환되었습니다.");
        assertEquals(content, new String(fileBytes), "파일 내용이 다릅니다.");
    }

    @Test
    @DisplayName("파일이 존재하면 정상적으로 삭제되어야 한다")
    void deleteFile_ShouldDeleteExistingFile() throws IOException {
        // given
        String fileName = "testFile.txt";
        Path filePath = tempDir.resolve(fileName);
        Files.createFile(filePath);
        assertTrue(Files.exists(filePath), "파일이 생성되지 않았습니다.");

        // when
        localStorageHelper.deleteFile(tempDir.toString(), fileName);

        // then
        assertFalse(Files.exists(filePath), "파일이 삭제되지 않았습니다.");
    }

    @Test
    @DisplayName("파일이 존재하지 않아도 삭제 시 예외가 발생하지 않아야 한다")
    void deleteFile_ShouldNotThrowException_WhenFileDoesNotExist() {
        // given
        String fileName = "nonExistentFile.txt";

        // when & then (파일이 없을 경우 예외 없이 실행되어야 함)
        assertDoesNotThrow(() -> localStorageHelper.deleteFile(tempDir.toString(), fileName));
    }

    @Test
    @DisplayName("파일 업로드 중 오류 발생 시 예외가 발생해야 한다")
    void uploadFile_ShouldThrowException_WhenIOExceptionOccurs() throws IOException {
        // given
        String fileName = "errorFile.txt";
        MultipartFile mockFile = Mockito.mock(MultipartFile.class);

        when(mockFile.getInputStream()).thenThrow(new IOException("Mocked IOException"));

        // when & then
        UncheckedIOException exception = assertThrows(UncheckedIOException.class, () ->
                localStorageHelper.uploadFile(mockFile, tempDir.toString(), fileName)
        );

        assertTrue(exception.getMessage().contains("파일 업로드 실패"), "예외 메시지가 예상과 다릅니다.");
    }
}
