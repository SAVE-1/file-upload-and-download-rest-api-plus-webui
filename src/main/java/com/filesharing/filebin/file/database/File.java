package com.filesharing.filebin.file.database;

public record File(
        Integer id,
        String name,
        String fileId,
        String fileType,
        String uploadDate,
        Integer size,
        Source source,
        String uploaderId
) {
}
