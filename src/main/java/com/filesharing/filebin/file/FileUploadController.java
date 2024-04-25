package com.filesharing.filebin.file;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private final FileRepositoryJdbc fileRepositoryJdbc;

    public FileUploadController(FileRepositoryJdbc fileRepositoryJdbc) {
        this.fileRepositoryJdbc = fileRepositoryJdbc;
    }

    @GetMapping("")
    String helloWorld() {
        return fileRepositoryJdbc.helloWorld();
    }

    @GetMapping("/write-hello-world")
    void writeHelloWorld() {
        fileRepositoryJdbc.writeHelloWorldToDatabase();
    }


}
