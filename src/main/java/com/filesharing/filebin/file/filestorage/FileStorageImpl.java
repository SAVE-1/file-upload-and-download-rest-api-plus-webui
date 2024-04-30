package com.filesharing.filebin.file.filestorage;

import org.springframework.stereotype.Service;

@Service
public class FileStorageImpl implements FileStorage {

    private final FileRepository repository;
    private final FileMapper mapper;

    public FileStorageImpl(FileRepository repository, FileMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    public FileUploadResponse upload(FileUploadRequest fileUploadRequest) {
        var fileEntity = mapper.toEntity(newId(), fileUploadRequest);
        repository.save(fileEntity);

        return mapper.toFileUploadResponse(fileEntity);
    }

    private String newId() { return java.util.UUID.randomUUID().toString(); }

}
