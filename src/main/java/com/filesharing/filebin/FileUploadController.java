package com.filesharing.filebin;

import com.filesharing.filebin.file.database.FileJdbcDatabaseRepository;
import com.filesharing.filebin.file.filestorage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private final StorageService storageService;
    private final FileJdbcDatabaseRepository fileJdbcDatabaseRepository;

    @Autowired
    public FileUploadController(StorageService storageService, FileJdbcDatabaseRepository fileJdbcDatabaseRepository) {
        this.storageService = storageService;
        this.fileJdbcDatabaseRepository = fileJdbcDatabaseRepository;
    }

    @GetMapping("")
    String helloWorld() {
        return fileJdbcDatabaseRepository.helloWorld();
    }

    @GetMapping("/write-hello-world")
    void writeHelloWorld() {
        fileJdbcDatabaseRepository.writeHelloWorldToDatabase();
    }




}
