package com.filesharing.filebin.services.interfaces;

import com.filesharing.filebin.services.filestorage.FileonDisk;
import org.springframework.core.io.Resource;

import java.util.Optional;

public interface FileStorageRepository {

    void setPath(String path);

    String getPath();

    Resource getUploadedFile(String name);

    Optional<String> uploadFileToDisk(FileonDisk file);

    Boolean doesFileExist(String fileName);

    Boolean deleteFile(String filename);

}
