package com.filesharing.filebin.file.filestorage;

import java.nio.file.Path;
import java.util.Optional;

public interface FileStorageService {

    public void setPath(String path);

    public String getPath();

    public Optional<String> uploadFileToDisk(FileonDisk file);




}
