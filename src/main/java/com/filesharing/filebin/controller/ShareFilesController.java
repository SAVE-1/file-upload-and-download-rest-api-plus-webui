package com.filesharing.filebin.controller;

import com.filesharing.filebin.repositories.FileStorageRepositoryImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/share/file")
@Tag(name = "Share a file", description = "File upload and download API")
public class ShareFilesController {
    private final FileStorageRepositoryImpl fileStorageServiceImpl;

    public ShareFilesController(FileStorageRepositoryImpl fileStorageServiceImpl) {
        this.fileStorageServiceImpl = fileStorageServiceImpl;
    }

    @Operation(summary = "Gets file from disk")
    @GetMapping(path = "/{filename:.+}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<String> downloadFile(@PathVariable String filename) throws Exception {
        return ResponseEntity.ok().body("not implemented");
    }


}
