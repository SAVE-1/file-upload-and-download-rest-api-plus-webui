package com.filesharing.filebin.controller;

import com.azure.core.util.BinaryData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import com.azure.core.credential.*;
import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;
import com.azure.storage.blob.specialized.*;
import com.azure.storage.common.*;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

// azurite -s -l j:\azurite -d j:\azurite\debug.log
@RestController
@RequestMapping("blob")
public class BlobController {

//    @Value("azurite-blob://testcontainer/test.txt")
//    private Resource blobFile;

//    @GetMapping("/readBlobFile")
//    public String readBlobFile() throws IOException {
//        return StreamUtils.copyToString(
//                this.blobFile.getInputStream(),
//                Charset.defaultCharset());
//    }

    @PostMapping("/writeBlobFile")
    public String writeBlobFile(@RequestBody String data) throws IOException {

        BlobClient blobClient = new BlobClientBuilder()
                .endpoint("http://127.0.0.1:10000")
                .connectionString("SharedAccessSignature=sv=2023-01-03&ss=btqf&srt=sco&st=2024-06-26T06%3A29%3A10Z&se=2031-06-27T06%3A29%3A00Z&sp=rwdxlup&sig=lmLvS69ar0Y5gkEg%2BxBRGYQuR5jnQu378nBG4WPulZc%3D;BlobEndpoint=http://127.0.0.1:10000/devstoreaccount1;FileEndpoint=http://devstoreaccount1.file.core.windows.net;QueueEndpoint=http://127.0.0.1:10001/devstoreaccount1;TableEndpoint=http://127.0.0.1:10002/devstoreaccount1;")
                .containerName("test1")
                .blobName("test1.txt")
                .buildClient();

        blobClient.upload(BinaryData.fromString(data));

        return "file was updated";
    }
}