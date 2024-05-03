package com.filesharing.filebin.file.filestorage;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public record FileonDisk(
        MultipartFile data,
        String fingerprint
) {
}
