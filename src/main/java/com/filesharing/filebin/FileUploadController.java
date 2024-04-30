package com.filesharing.filebin;

import com.filesharing.filebin.file.database.FileJdbcDatabaseRepository;
import com.filesharing.filebin.file.filestorage.FileStorageImpl;
import com.filesharing.filebin.file.filestorage.FileSystemStorageService;
import com.filesharing.filebin.file.filestorage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.print.attribute.standard.Media;

@RestController
@RequestMapping("/api/file")
@Tag(name = "File upload and download", description = "File upload and download API")
public class FileUploadController {

    private final FileStorageImpl storageServiceImpl;
    private final FileJdbcDatabaseRepository fileJdbcDatabaseRepository;

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    public FileUploadController(FileStorageImpl storageService, FileJdbcDatabaseRepository fileJdbcDatabaseRepository) {
        this.storageServiceImpl = storageService;
        this.fileJdbcDatabaseRepository = fileJdbcDatabaseRepository;
    }

    @GetMapping("helloworld")
    String helloWorld() {
        return fileJdbcDatabaseRepository.helloWorld();
    }

    @GetMapping("/write-hello-world")
    void writeHelloWorld() {
        fileJdbcDatabaseRepository.writeHelloWorldToDatabase();
    }

    @GetMapping("/echo")
    String writeHelloWorld(@RequestParam("echo") String echo) {
        return fileJdbcDatabaseRepository.echo(echo);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<FileUploadResponse> upload(
            @Validated @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name
    ) {
        FileUploadRequest fileUploadRequest = new FileUploadRequest(file, name);
        return ResponseEntity.ok(storageServiceImpl.upload(fileUploadRequest));
    }


}
