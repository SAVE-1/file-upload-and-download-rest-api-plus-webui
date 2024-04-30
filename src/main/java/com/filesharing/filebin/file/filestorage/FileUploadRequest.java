package com.filesharing.filebin.file.filestorage;

import org.springframework.web.multipart.MultipartFile;

public record FileUploadRequest(
        MultipartFile file,
        String name

) {

}
