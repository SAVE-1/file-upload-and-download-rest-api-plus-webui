package com.filesharing.filebin.file.entities;

public record File(
        String name,
        String fileType,
        String uploadDate,
        Integer size,
        Source source,
        String uploaderId
) {
}
