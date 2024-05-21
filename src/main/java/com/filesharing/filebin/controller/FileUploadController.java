package com.filesharing.filebin.controller;

import com.filesharing.filebin.config.constants.MyConstants;
import com.filesharing.filebin.entities.FileMetadata;
import com.filesharing.filebin.entities.User;
import com.filesharing.filebin.repositories.FileMetadataRepositoryImpl;
import com.filesharing.filebin.repositories.FileStorageRepositoryImpl;
import com.filesharing.filebin.repositories.filestorage.FileonDisk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/file")
@Tag(name = "File upload and download", description = "File upload and download API")
public class FileUploadController {

    private final FileMetadataRepositoryImpl fileMetadataRepositoryImpl;
    private final FileStorageRepositoryImpl fileStorageServiceImpl;

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    public FileUploadController(FileMetadataRepositoryImpl fileMetadataRepositoryImpl, FileStorageRepositoryImpl fileStorageServiceImpl) {
        this.fileMetadataRepositoryImpl = fileMetadataRepositoryImpl;
        this.fileStorageServiceImpl = fileStorageServiceImpl;
    }

    // for uploading the SINGLE file to the database
    @PostMapping(
            path = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file, Boolean forceOverwrite) throws Exception {

        Boolean doesExist = fileStorageServiceImpl.doesFileExist(file.getOriginalFilename());

        if(doesExist && forceOverwrite == false) {
            return new ResponseEntity<>(MyConstants.FILE_ALREADY_EXISTS, HttpStatus.CONFLICT);
        }

        FileonDisk fileonDisk = new FileonDisk(file, file.getName());

        fileStorageServiceImpl.uploadFileToDisk(fileonDisk);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();

        fileMetadataRepositoryImpl.upsert(file.getOriginalFilename(), user.getEmail(), (int)file.getSize(), LocalDateTime.now().toString());

        return ResponseEntity.ok().build();
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

    @GetMapping(path = "/myfiles")
    public List<FileMetadata> getUserFileList() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();

        List<FileMetadata> f = fileMetadataRepositoryImpl.findByUploaderEmail(user.getEmail());

        return f;
    }

}
