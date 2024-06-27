package com.filesharing.filebin.controller;

import com.azure.core.util.BinaryData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.azure.storage.blob.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

// azurite -s -l j:\azurite -d j:\azurite\debug.log
// https://github.com/Azure/azure-sdk-for-java/blob/main/sdk/storage/azure-storage-blob/README.md

@RestController
@RequestMapping("blob")
public class BlobController {

    @Value("${spring.cloud.azure.storage.blob.connection-string}")
    private String connectionString;

    @Value("${spring.cloud.azure.storage.blob.base-container}")
    private String baseContainer;

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