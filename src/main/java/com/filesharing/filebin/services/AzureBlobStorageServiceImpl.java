package com.filesharing.filebin.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobClientBuilder;
import com.filesharing.filebin.services.filestorage.FileonDisk;
import com.filesharing.filebin.services.interfaces.AzureBlobStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

public class AzureBlobStorageServiceImpl implements AzureBlobStorageService {
    @Value("${spring.cloud.azure.storage.blob.connection-string}")
    private String connectionString;

    @Value("${spring.cloud.azure.storage.blob.base-container}")
    private String baseContainer;

    @Override
    public Optional<Resource> getBlob(String name) {
        return Optional.empty();
//        BlobClient blobClient = new BlobClientBuilder()
//                .endpoint("http://127.0.0.1:10000")
//                .connectionString(connectionString)
//                .containerName(baseContainer)
//                .blobName(name)
//                .buildClient();
//
//        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
//            blobClient.downloadStream(outputStream);
//            return Optional.of(outputStream.);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return Optional.of(Resource);
//        }
    }

    @Override
    public Optional<String> uploadBlob(FileonDisk file) {
        return Optional.empty();
    }

    @Override
    public Boolean doesBlobExist(String fileName) {
        return null;
    }

    @Override
    public Boolean deleteBlob(String filename) {
        return null;
    }
}