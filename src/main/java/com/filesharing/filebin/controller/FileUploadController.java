package com.filesharing.filebin.controller;

import com.filesharing.filebin.entities.User;
import com.filesharing.filebin.services.AzureBlobStorageServiceImpl;
import com.filesharing.filebin.services.FileMetadataServiceImpl;
import com.filesharing.filebin.services.FileStorageServiceImpl;
import com.filesharing.filebin.services.filestorage.FileonDisk;
import com.filesharing.filebin.responses.FileMetadataResponse;
import io.swagger.v3.oas.annotations.Operation;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/file")
@Tag(name = "File upload and download", description = "File upload and download API")
public class FileUploadController {

    private final FileMetadataServiceImpl fileMetadataRepositoryImpl;
    private final AzureBlobStorageServiceImpl azureBlobStorageServiceImpl;

//    private final FileStorageServiceImpl fileStorageServiceImpl;

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    public FileUploadController(FileMetadataServiceImpl fileMetadataRepositoryImpl, AzureBlobStorageServiceImpl azureBlobStorageServiceImpl) {
        this.fileMetadataRepositoryImpl = fileMetadataRepositoryImpl;
        this.azureBlobStorageServiceImpl = azureBlobStorageServiceImpl;
    }

    @Operation(summary = "Upserts a new file to disk and database")
    @PostMapping(
            path = "/",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FileMetadataResponse> uploadFile(@RequestParam("file") MultipartFile file, Boolean forceOverwrite) throws Exception {

        Boolean doesExist = azureBlobStorageServiceImpl.doesBlobExist(file.getOriginalFilename());

        if (doesExist && forceOverwrite == false) {
            return new ResponseEntity<>(HttpStatus.FOUND);
        }

        FileonDisk fileonDisk = new FileonDisk(file, file.getOriginalFilename());

        azureBlobStorageServiceImpl.uploadBlob(file.getOriginalFilename(), file);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        LocalDateTime dateNow = LocalDateTime.now();

        fileMetadataRepositoryImpl.upsert(file.getOriginalFilename(), user.getEmail(), (int) file.getSize(), dateNow);
        FileMetadataResponse t = new FileMetadataResponse(user.getEmail(), file.getOriginalFilename(), dateNow.withNano(0).toString(), (int) file.getSize());
        return ResponseEntity.ok().body(t);
    }

    @Operation(summary = "Gets file from disk")
    @GetMapping(path = "/{filename:.+}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws Exception {
        Optional<Resource> file = azureBlobStorageServiceImpl.getBlob(filename);

        if (file.isPresent())
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + filename + "\"").body(file.get());

        return ResponseEntity.notFound().build();


    }

    @Operation(summary = "Lists all user files and their info")
    @GetMapping(path = "/myfiles")
    public List<FileMetadataResponse> getUserFileList() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        List<FileMetadataResponse> f = fileMetadataRepositoryImpl.listUsersFiles(user.getEmail());

        return f;
    }

    @Operation(summary = "Deletes file from disk and database")
    @DeleteMapping(path = "/{filename:.+}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> deleteFile(@PathVariable String filename) throws Exception {
        Boolean doesExist = azureBlobStorageServiceImpl.doesBlobExist(filename);

        if (doesExist) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();

            Optional<FileMetadataResponse> f = fileMetadataRepositoryImpl.delete(user.getEmail(), filename);

            if (f.isPresent()) {
                azureBlobStorageServiceImpl.deleteBlob(filename);
                return ResponseEntity.ok().build();
            }
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Gets info on a single file")
    @GetMapping(path = "/info/{filename:.+}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<FileMetadataResponse> getFileInfo(@PathVariable String filename) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Optional<FileMetadataResponse> file = fileMetadataRepositoryImpl.getFileInformation(user.getEmail(), filename);

        if (file.isPresent())
            return ResponseEntity.ok().body(file.get());

        return ResponseEntity.notFound().build();
    }


}
