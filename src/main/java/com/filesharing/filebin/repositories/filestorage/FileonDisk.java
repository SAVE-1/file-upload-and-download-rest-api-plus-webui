package com.filesharing.filebin.repositories.filestorage;

import org.springframework.web.multipart.MultipartFile;

public record FileonDisk(
        MultipartFile data,
        String fingerprint
) {
}
