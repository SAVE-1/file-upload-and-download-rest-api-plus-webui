package com.filesharing.filebin.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobClientBuilder;
import com.filesharing.filebin.services.filestorage.FileonDisk;
import com.filesharing.filebin.services.interfaces.AzureBlobStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

@Service
public class AzureBlobStorageServiceImpl implements AzureBlobStorageService {
    @Value("${spring.cloud.azure.storage.blob.connection-string}")
    private String connectionString;

    @Value("${spring.cloud.azure.storage.blob.base-container}")
    private String baseContainer;

    @Override
    public Optional<Resource> getBlob(String name) {
        BlobClient blobClient = new BlobClientBuilder()
                .endpoint("http://127.0.0.1:10000")
                .connectionString(connectionString)
                .containerName(baseContainer)
                .blobName(name)
                .buildClient();

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            blobClient.downloadStream(outputStream);

            ByteArrayResource r = new ByteArrayResource(outputStream.toByteArray(), name);

            return Optional.of(r);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
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
