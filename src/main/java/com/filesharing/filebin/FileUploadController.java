package com.filesharing.filebin;

import com.filesharing.filebin.file.database.FileMetadataRepositoryImpl;
import com.filesharing.filebin.file.filestorage.FileStorageServiceImpl;
import com.filesharing.filebin.file.filestorage.FileonDisk;
import com.filesharing.filebin.file.filestorage.StorageFileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/file")
@Tag(name = "File upload and download", description = "File upload and download API")
public class FileUploadController {

    private final FileMetadataRepositoryImpl fileMetadataRepositoryImpl;
    private final FileStorageServiceImpl fileStorageServiceImpl;

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    public FileUploadController(FileMetadataRepositoryImpl fileMetadataRepositoryImpl, FileStorageServiceImpl fileStorageServiceImpl) {
        this.fileMetadataRepositoryImpl = fileMetadataRepositoryImpl;
        this.fileStorageServiceImpl = fileStorageServiceImpl;
    }

    @GetMapping("helloworld")
    String helloWorld() {
        return fileMetadataRepositoryImpl.helloWorld();
    }

    @GetMapping("/write-hello-world")
    void writeHelloWorld() {
        fileMetadataRepositoryImpl.writeHelloWorldToDatabase();
    }

    @GetMapping("/echo")
    String writeHelloWorld(@RequestParam("echo") String echo) {
        return fileMetadataRepositoryImpl.echo(echo);
    }

    // for uploading the SINGLE file to the database
    @PostMapping(
            path = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        FileonDisk fileonDisk = new FileonDisk(file, "adwadw");

        fileStorageServiceImpl.uploadFileToDisk(fileonDisk);
        return "upload";
    }

    @GetMapping(path = "/download/{filename:.+}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws Exception {
        Resource file = fileStorageServiceImpl.getUploadedFile(filename);

        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping(path = "/test")
    public String test() throws Exception {
        return "TEST";
    }


}
