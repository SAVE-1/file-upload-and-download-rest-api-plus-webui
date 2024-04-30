package com.filesharing.filebin.file.filestorage;

import org.springframework.stereotype.Component;

@Component
public class FileMapper {
    public FileEntity toEntity(String id, FileUploadRequest fileUploadRequest) {
        return FileEntity.builder()
                .id(id)
                .name(fileUploadRequest.name())
                .size(fileUploadRequest.file().getSize())
                .build();
    }

    public FileUploadResponse toFileUploadResponse(FileEntity fileEntity) {
        return new FileUploadResponse(
                fileEntity.id(),
                fileEntity.name(),
                "",
                fileEntity.size()
        )
    }
}
