package com.filesharing.filebin.integrationtests;

import com.filesharing.filebin.services.AzureBlobStorageServiceImpl;
import com.filesharing.filebin.services.filestorage.FileonDisk;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(properties = "spring.cloud.azure.storage.blob.base-container=test")
class AzureBlobStorageServiceImplTest {
    private final AzureBlobStorageServiceImpl repository = new AzureBlobStorageServiceImpl();

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

        repository.uploadBlob(newFileName1, mult1);
        repository.uploadBlob(newFileName2, mult2);
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
        Boolean file1Exists = repository.doesBlobExist("filenew1.txt");
        assertTrue(file1Exists == true, "File 1. not found");

        Boolean file2Exists = repository.doesBlobExist("filenew2.txt");
        assertTrue(file2Exists == true, "File 2. not found");
    }

    @Test
    void upsertFile() {
        String originalFileName = "file2.txt";
        String newFileName = "upsertFileTest.txt";

        MultipartFile mult = getMultiPartFile(newFileName, originalFileName);

        FileonDisk f = new FileonDisk(mult, newFileName);

        repository.uploadBlob(newFileName, mult);

        Boolean file2Exists = repository.doesBlobExist(newFileName);
        assertTrue(file2Exists == true, "File not found");
    }

    @Test
    void deleteFile() {
        String originalFileName = "file2.txt";
        String newFileName = "deleteFile.txt";

        MultipartFile mult = getMultiPartFile(newFileName, originalFileName);

        FileonDisk f = new FileonDisk(mult, newFileName);

        repository.uploadBlob(newFileName, mult);

        Boolean fileExists = repository.doesBlobExist(newFileName);
        assertTrue(fileExists == true, "File not found");

        repository.deleteBlob(newFileName);

        Boolean fileShouldNotExist = repository.doesBlobExist(newFileName);
        assertTrue(fileShouldNotExist == false, "File found");
    }

    @Test
    void getFile() throws IOException {
        String originalFileName = "file2.txt";
        String newFileName = "deleteFile.txt";

        MultipartFile mult = getMultiPartFile(newFileName, originalFileName);

        FileonDisk f = new FileonDisk(mult, newFileName);

        repository.uploadBlob(newFileName, mult);

        Boolean fileExists = repository.doesBlobExist(newFileName);
        assertTrue(fileExists == true, "File not found");

        Optional<Resource> r = repository.getBlob(newFileName);

        Boolean fileShouldNotExist = repository.doesBlobExist(newFileName);

        if(fileShouldNotExist == false) {
            System.out.println("File does not exist!");
            return;
        }

        try {
            String hashString1 = getMd5Hash(f.data().getBytes());
            String hashString2 = getMd5Hash(r.get().getContentAsByteArray());

            assertTrue(hashString1.equals(hashString2), "Files are not equal");
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
