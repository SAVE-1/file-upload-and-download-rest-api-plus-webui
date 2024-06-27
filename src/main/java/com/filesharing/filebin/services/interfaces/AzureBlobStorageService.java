package com.filesharing.filebin.services.interfaces;

import com.filesharing.filebin.services.filestorage.FileonDisk;
import org.springframework.core.io.Resource;

import java.util.Optional;

public interface AzureBlobStorageService {
    Optional<Resource> getBlob(String name);

    Optional<String> uploadBlob(FileonDisk file);

    Boolean doesBlobExist(String fileName);

    Boolean deleteBlob(String filename);
}
