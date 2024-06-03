package com.filesharing.filebin.repositories;

import com.filesharing.filebin.repositories.filestorage.FileonDisk;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class FileStorageRepositoryImplTest {
    private final FileStorageRepositoryImpl repository = new FileStorageRepositoryImpl(filePath);

    private static final String filePath = "upload-dir-TESTS";

    @BeforeEach
    void setUp() throws MalformedURLException {
        File theDir = new File(filePath);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }

        String originalFileName1 = "file1.txt";
        String originalFileName2 = "file2.txt";

        String newFileName1 = "filenew1.txt";
        String newFileName2 = "filenew2.txt";

        writeToFile(originalFileName1, "I AM FILE 1.");
        writeToFile(originalFileName2, "I AM FILE 2.");

        MultipartFile mult1 = getMultiPartFile(newFileName1, originalFileName1);
        MultipartFile mult2 = getMultiPartFile(newFileName2, originalFileName2);

        FileonDisk f1 = new FileonDisk(mult1, newFileName1);
        FileonDisk f2 = new FileonDisk(mult2, newFileName2);

        repository.uploadFileToDisk(f1);
        repository.uploadFileToDisk(f2);
    }

    @AfterEach
    void tearDown() {
        try {
            FileUtils.cleanDirectory(new File(this.filePath));
        } catch (IOException io) {
            System.out.println("No such folder!!");
        }
    }

    private void writeToFile(String fileName, String text) {
        try {
            PrintWriter writer = new PrintWriter(this.filePath + "/" + fileName, "UTF-8");
            writer.println(text);
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println(e.toString());
        }
    }

    private MultipartFile getMultiPartFile(String newFileName, String originalFileName) {
        try {
            String contentType = "text/plain";
            byte[] content = Files.readAllBytes(Path.of(this.filePath + "/" + originalFileName));
            return new MockMultipartFile(newFileName,
                    originalFileName, contentType, content);
        } catch (final IOException e) {
            System.out.println(e.toString());
            return null;
        }
    }

    @Test
    void twoFilesShouldExist() {
        Boolean file1Exists = repository.doesFileExist("filenew1.txt");
        assertTrue(file1Exists == true, "File 1. not found");

        Boolean file2Exists = repository.doesFileExist("filenew2.txt");
        assertTrue(file2Exists == true, "File 2. not found");
    }

    @Test
    void upsertFile() {
        String originalFileName = "file2.txt";
        String newFileName = "upsertFileTest.txt";

        MultipartFile mult = getMultiPartFile(newFileName, originalFileName);

        FileonDisk f = new FileonDisk(mult, newFileName);

        repository.uploadFileToDisk(f);

        Boolean file2Exists = repository.doesFileExist(newFileName);
        assertTrue(file2Exists == true, "File not found");
    }

    @Test
    void deleteFile() {
        String originalFileName = "file2.txt";
        String newFileName = "deleteFile.txt";

        MultipartFile mult = getMultiPartFile(newFileName, originalFileName);

        FileonDisk f = new FileonDisk(mult, newFileName);

        repository.uploadFileToDisk(f);

        Boolean fileExists = repository.doesFileExist(newFileName);
        assertTrue(fileExists == true, "File not found");

        repository.deleteFile(newFileName);

        Boolean fileShouldNotExist = repository.doesFileExist(newFileName);
        assertTrue(fileShouldNotExist == false, "File found");
    }

    @Test
    void getFile() throws IOException {
        String originalFileName = "file2.txt";
        String newFileName = "deleteFile.txt";

        MultipartFile mult = getMultiPartFile(newFileName, originalFileName);

        FileonDisk f = new FileonDisk(mult, newFileName);

        repository.uploadFileToDisk(f);

        Boolean fileExists = repository.doesFileExist(newFileName);
        assertTrue(fileExists == true, "File not found");

        Resource r = repository.getUploadedFile(newFileName);

        Boolean fileShouldNotExist = repository.doesFileExist(newFileName);

        if(fileShouldNotExist == false) {
            System.out.println("File does not exist!");
            return;
        }

        try {
            String hashString1 = getMd5Hash(f.data().getBytes());
            String hashString2 = getMd5Hash(Files.readAllBytes(r.getFile().toPath()));

            assertTrue(hashString1.equals(hashString2), "File not found");
        } catch (IOException i) {
            System.out.println(i.toString());
        }
    }

    private String getMd5Hash(byte[] b)  {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(b);
            return new BigInteger(1, digest).toString(16);
        } catch (NoSuchAlgorithmException n) {
            System.out.println(n);
            return "";
        }
    }


}
