package com.filesharing.filebin.services.interfaces;

import com.filesharing.filebin.responses.FileMetadataResponse;
import com.filesharing.filebin.services.filestorage.FileonDisk;
import org.springframework.core.io.Resource;

import java.util.Optional;

public interface AzureBlobStorageService {
    Optional<Resource> getBlob(String name);

    void uploadBlob(String name, Resource file);

    Boolean doesBlobExist(String fileName);

    Optional<FileMetadataResponse> deleteBlob(String filename);
}
