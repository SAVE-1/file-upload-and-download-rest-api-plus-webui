package com.filesharing.filebin.services;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobClientBuilder;
import com.filesharing.filebin.responses.FileMetadataResponse;
import com.filesharing.filebin.services.interfaces.AzureBlobStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
public class AzureBlobStorageServiceImpl implements AzureBlobStorageService {
    @Value("${spring.cloud.azure.storage.blob.connection-string}")
    private String connectionString;

    @Value("${spring.cloud.azure.storage.blob.base-container}")
    private String baseContainer;

    @Override
    public void uploadBlob(String name, Resource file) {
        BlobClient blobClient = new BlobClientBuilder()
                .endpoint("http://127.0.0.1:10000")
                .connectionString(connectionString)
                .containerName(baseContainer)
                .blobName(name)
                .buildClient();
        try {
            BinaryData data = BinaryData.fromBytes(file.getContentAsByteArray());
            blobClient.upload(data.toStream(), data.getLength(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void uploadBlob(String name, MultipartFile file) {
        BlobClient blobClient = new BlobClientBuilder()
                .endpoint("http://127.0.0.1:10000")
                .connectionString(connectionString)
                .containerName(baseContainer)
                .blobName(name)
                .buildClient();
        try {
            BinaryData data = BinaryData.fromBytes(file.getBytes());
            blobClient.upload(data.toStream(), data.getLength(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
    public Boolean doesBlobExist(String name) {
        BlobClient blobClient = new BlobClientBuilder()
                .endpoint("http://127.0.0.1:10000")
                .connectionString(connectionString)
                .containerName(baseContainer)
                .blobName(name)
                .buildClient();

        return blobClient.exists();
    }

    @Override
    public void deleteBlob(String name) {
        BlobClient blobClient = new BlobClientBuilder()
                .endpoint("http://127.0.0.1:10000")
                .connectionString(connectionString)
                .containerName(baseContainer)
                .blobName(name)
                .buildClient();

        blobClient.delete();
    }
}
