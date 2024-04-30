package com.filesharing.filebin.file.filestorage;

public record FileUploadResponse(
        String id,
        String name,
        String url,
        Long size
) {
}
