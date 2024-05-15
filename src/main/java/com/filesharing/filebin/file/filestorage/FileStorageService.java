package com.filesharing.filebin.file.filestorage;

import org.springframework.core.io.Resource;

import java.util.Optional;

public interface FileStorageService {

    void setPath(String path);

    String getPath();

    Resource getUploadedFile(String name);

    Optional<String> uploadFileToDisk(FileonDisk file);

    Boolean doesFileExist(String fileName);
}
