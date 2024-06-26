package com.filesharing.filebin.controller;

import com.azure.core.util.BinaryData;
import org.springframework.web.bind.annotation.*;

import com.azure.storage.blob.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

// azurite -s -l j:\azurite -d j:\azurite\debug.log
@RestController
@RequestMapping("blob")
public class BlobController {

//    @Value("azurite-blob://testcontainer/test.txt")

//    @GetMapping("/readBlobFile")
//    public String readBlobFile() throws IOException {
//        BlobClient blobClient = new BlobClientBuilder()
//                .endpoint("http://127.0.0.1:10000")
//                .connectionString("SharedAccessSignature=sv=2023-01-03&ss=btqf&srt=sco&st=2024-06-26T06%3A29%3A10Z&se=2031-06-27T06%3A29%3A00Z&sp=rwdxlup&sig=lmLvS69ar0Y5gkEg%2BxBRGYQuR5jnQu378nBG4WPulZc%3D;BlobEndpoint=http://127.0.0.1:10000/devstoreaccount1;FileEndpoint=http://devstoreaccount1.file.core.windows.net;QueueEndpoint=http://127.0.0.1:10001/devstoreaccount1;TableEndpoint=http://127.0.0.1:10002/devstoreaccount1;")
//                .containerName("test1")
//                .blobName("test1.txt")
//                .buildClient();
//
//        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
//            blobClient.downloadStream(outputStream);
//            return outputStream.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "";
//        }
//    }

    @PostMapping("/writeBlobFile")
    public String writeBlobFile(@RequestBody String data) throws IOException {

        BlobClient blobClient = new BlobClientBuilder()
                .endpoint("http://127.0.0.1:10000")
                .connectionString("SharedAccessSignature=sv=2023-01-03&ss=btqf&srt=sco&st=2024-06-26T06%3A29%3A10Z&se=2031-06-27T06%3A29%3A00Z&sp=rwdxlup&sig=lmLvS69ar0Y5gkEg%2BxBRGYQuR5jnQu378nBG4WPulZc%3D;BlobEndpoint=http://127.0.0.1:10000/devstoreaccount1;FileEndpoint=http://devstoreaccount1.file.core.windows.net;QueueEndpoint=http://127.0.0.1:10001/devstoreaccount1;TableEndpoint=http://127.0.0.1:10002/devstoreaccount1;")
                .containerName("test1")
                .blobName("test1.txt")
                .buildClient();

        blobClient.upload(BinaryData.fromString(data), true);

        return "file was updated";
    }
}