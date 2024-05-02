package com.filesharing.filebin;

import com.filesharing.filebin.file.database.FileJdbcDatabaseRepository;
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

    private final FileJdbcDatabaseRepository fileJdbcDatabaseRepository;

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    public FileUploadController(FileJdbcDatabaseRepository fileJdbcDatabaseRepository) {
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

    // for uploading the SINGLE file to the database
    @PostMapping(
            path = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestParam("image") MultipartFile file) throws Exception {
        return "PRÖÖT";
    }

    @PostMapping(
            path = "/test"
    )
    public String test() throws Exception {
        return "PRÖÖT";
    }




}
