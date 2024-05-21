package com.filesharing.filebin.repositories.interfaces;

import com.filesharing.filebin.repositories.filestorage.FileonDisk;
import org.springframework.core.io.Resource;

import java.util.Optional;

public interface FileStorageRepository {

    void setPath(String path);

    String getPath();

    Resource getUploadedFile(String name);

    Optional<String> uploadFileToDisk(FileonDisk file);

    Boolean doesFileExist(String fileName);
}
