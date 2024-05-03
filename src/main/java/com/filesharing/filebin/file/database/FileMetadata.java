package com.filesharing.filebin.file.database;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public record FileMetadata(
        String fingerprint,
        String userAssignedName,
        String uploader,
        String checksum,
        LocalDateTime uploadDate,
        String type,
        Integer size,
        Boolean isScannedForMalicious,
        Boolean isScannedForIllegal

) {
}
