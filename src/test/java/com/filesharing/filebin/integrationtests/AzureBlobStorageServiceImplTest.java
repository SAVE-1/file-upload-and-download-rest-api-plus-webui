package com.filesharing.filebin.integrationtests;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.*;
import com.filesharing.filebin.services.AzureBlobStorageServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "spring.cloud.azure.storage.blob.base-container=integrationtests")
class AzureBlobStorageServiceImplTest {
    private final AzureBlobStorageServiceImpl repository;

    @Value("${spring.cloud.azure.storage.blob.connection-string}")
    private String connectionString;

    @Value("${spring.cloud.azure.storage.blob.test.base-container}")
    private String baseContainer;

    @Value("${spring.cloud.azure.storage.blob.endpoint-url}")
    private String endpointUrl;

    @Value("${spring.cloud.azure.storage.blob.sas-token}")
    private String sasToken;

    private static final String filePath = "upload-dir-TESTS";

    @Autowired
    public AzureBlobStorageServiceImplTest(AzureBlobStorageServiceImpl repository) {
        this.repository = repository;
    }

    @BeforeEach
    void setUp() throws MalformedURLException {
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .endpoint(endpointUrl)
                .connectionString(connectionString)
                .buildClient();

        blobServiceClient.createBlobContainerIfNotExists(baseContainer);

        BlobContainerClient blobContainerClient = new BlobContainerClientBuilder()
                .endpoint(endpointUrl)
                .connectionString(connectionString)
                .containerName(baseContainer)
                .buildClient();

        BlobClient blob = blobContainerClient.getBlobClient("filenew1.txt");
        String dataSample = "samples 1.";
        blob.upload(BinaryData.fromString(dataSample), true);

        BlobClient blob2 = blobContainerClient.getBlobClient("filenew2.txt");
        String dataSample2 = "samples 2.";
        blob2.upload(BinaryData.fromString(dataSample2), true);
    }

    @AfterEach
    void tearDown() {

    }

    private MultipartFile getMultiPartFile(String newFileName, String fileContent) {
        String contentType = "text/plain";
        byte[] content = fileContent.getBytes();
        return new MockMultipartFile(newFileName,
                fileContent, contentType, content);
    }

    @Test
    void twoFilesShouldExist() {
        String file1 = "twoFilesShouldExist1-integration-test.txt";
        String file2 = "twoFilesShouldExist2-integration-test.txt";
        createFile(file1, "File 1.");
        createFile(file2, "File 1.");

        Boolean file1Exists = repository.doesBlobExist(file1);
        assertTrue(file1Exists == true, "File 1. not found");

        Boolean file2Exists = repository.doesBlobExist(file2);
        assertTrue(file2Exists == true, "File 2. not found");
    }

    @Test
    void uploadBlob() {
        String fileName = "file2.txt";
        String content = "lorem ipsum";

        MultipartFile mult = getMultiPartFile(fileName, content);

        repository.uploadBlob(fileName, mult);

        Boolean file2Exists = repository.doesBlobExist(fileName);
        assertTrue(file2Exists == true, "File not found");
    }

    @Test
    void deleteFile() {
        String fileName = "deleteFile-integration-test.txt";
        String content = "lorem ipsum";

        MultipartFile mult = getMultiPartFile(fileName, content);

        repository.uploadBlob(fileName, mult);

        Boolean file2Exists = repository.doesBlobExist(fileName);
        // file SHOULD exist
        assertTrue(file2Exists == true, "File not found");

        repository.deleteBlob(fileName);

        Boolean fileShouldNotExist = repository.doesBlobExist(fileName);
        // file SHOULD NOT exist
        assertTrue(fileShouldNotExist == false, "File found");
    }

    @Test
    void getFile() {
        String file1 = "twoFilesShouldExist1-integration-test.txt";
        String content = "lorem ipsum content";
        createFile(file1, content);

        Optional<Resource> readBlob = repository.getBlob(file1);

        Boolean fileShouldNotExist = repository.doesBlobExist(file1);

        if (fileShouldNotExist == false) {
            System.out.println("File does not exist!");
            return;
        }

        try {
            String s = new String(readBlob.get().getContentAsByteArray(), StandardCharsets.UTF_8);
            assertTrue(s.equals(content), "Files are not equal");
        } catch (java.io.IOException i) {
            System.out.println(i);
        }
    }

    private void createFile(String name, String content) {
        BlobContainerClient blobContainerClient = new BlobContainerClientBuilder()
                .endpoint(endpointUrl)
                .connectionString(connectionString)
                .containerName(baseContainer)
                .buildClient();

        BlobClient blob = blobContainerClient.getBlobClient(name);
        blob.upload(BinaryData.fromString(content), true);
    }
}
