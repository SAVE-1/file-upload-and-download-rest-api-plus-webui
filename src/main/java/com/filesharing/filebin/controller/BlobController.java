package com.filesharing.filebin.controller;

import com.azure.core.util.BinaryData;
import com.filesharing.filebin.services.AzureBlobStorageServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.azure.storage.blob.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

// azurite -s -l j:\azurite -d j:\azurite\debug.log
// https://github.com/Azure/azure-sdk-for-java/blob/main/sdk/storage/azure-storage-blob/README.md

@RestController
@RequestMapping("blob")
public class BlobController {

    @Value("${spring.cloud.azure.storage.blob.connection-string}")
    private String connectionString;

    @Value("${spring.cloud.azure.storage.blob.base-container}")
    private String baseContainer;

    private final AzureBlobStorageServiceImpl azureBlobStorageServiceImpl;

    public BlobController(AzureBlobStorageServiceImpl azureBlobStorageServiceImpl) {
        this.azureBlobStorageServiceImpl = azureBlobStorageServiceImpl;
    }

    @GetMapping(path = "/readBlobFile/{filename:.+}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public String readBlobFile(@PathVariable String filename) throws IOException {
        BlobClient blobClient = new BlobClientBuilder()
                .endpoint("http://127.0.0.1:10000")
                .connectionString(connectionString)
                .containerName(baseContainer)
                .blobName(filename)
                .buildClient();

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            blobClient.downloadStream(outputStream);
            return outputStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    @GetMapping(path = "/readBlobFile2/{filename:.+}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> readBlobFile2(@PathVariable String filename) throws IOException {
        Optional<Resource> r =  this.azureBlobStorageServiceImpl.getBlob(filename);

        if(r.isPresent()) {
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + filename + "\"").body(r.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/writeBlobFile")
    public String writeBlobFile(@RequestBody String data) throws IOException {

        BlobClient blobClient = new BlobClientBuilder()
                .endpoint("http://127.0.0.1:10000")
                .connectionString(connectionString)
                .containerName(baseContainer)
                .blobName("test1.txt")
                .buildClient();

        blobClient.upload(BinaryData.fromString(data), true);

        return "file was updated";
    }
}