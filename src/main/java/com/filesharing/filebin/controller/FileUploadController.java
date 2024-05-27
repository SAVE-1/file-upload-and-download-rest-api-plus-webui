package com.filesharing.filebin.controller;

import com.filesharing.filebin.entities.User;
import com.filesharing.filebin.repositories.FileMetadataRepositoryImpl;
import com.filesharing.filebin.repositories.FileStorageRepositoryImpl;
import com.filesharing.filebin.repositories.filestorage.FileonDisk;
import com.filesharing.filebin.responses.FileMetadataResponse;
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
    public ResponseEntity<FileMetadataResponse> uploadFile(@RequestParam("file") MultipartFile file, Boolean forceOverwrite) throws Exception {

        Boolean doesExist = fileStorageServiceImpl.doesFileExist(file.getOriginalFilename());

        if (doesExist && forceOverwrite == false) {
            return new ResponseEntity<>(HttpStatus.FOUND);
        }

        FileonDisk fileonDisk = new FileonDisk(file, file.getName());

        fileStorageServiceImpl.uploadFileToDisk(fileonDisk);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        LocalDateTime dateNow = LocalDateTime.now();

        fileMetadataRepositoryImpl.upsert(file.getOriginalFilename(), user.getEmail(), (int) file.getSize(), dateNow);
        FileMetadataResponse t = new FileMetadataResponse(user.getEmail(), file.getOriginalFilename(), dateNow.withNano(0).toString(), (int)file.getSize());
        return ResponseEntity.ok().body(t);
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
    public List<FileMetadataResponse> getUserFileList() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        List<FileMetadataResponse> f = fileMetadataRepositoryImpl.listUsersFiles(user.getEmail());

        return f;
    }

    @DeleteMapping(path = "/download/{filename:.+}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> deleteFile(@PathVariable String filename) throws Exception {
        Boolean doesExist = fileStorageServiceImpl.doesFileExist(filename);

        if (doesExist)
        {
            Boolean successfullDel = fileStorageServiceImpl.deleteFile(filename);

            if(successfullDel) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                User user = (User) authentication.getPrincipal();
                int n = fileMetadataRepositoryImpl.delete(user.getEmail(), filename);

                if(n > 0){
                    return ResponseEntity.ok().build();
                }
            }
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/getinfo/{filename:.+}",
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
