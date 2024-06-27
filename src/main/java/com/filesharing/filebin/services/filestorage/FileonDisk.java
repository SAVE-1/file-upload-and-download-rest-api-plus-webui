package com.filesharing.filebin.services.filestorage;

import org.springframework.web.multipart.MultipartFile;

public record FileonDisk(
        MultipartFile data,
        String fingerprint
) {
}
