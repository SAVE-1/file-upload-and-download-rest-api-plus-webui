package com.filesharing.filebin.file.filestorage;

import com.filesharing.filebin.FileUploadController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    public Path storagePath;

    public FileStorageServiceImpl(@Value("${filebin.storage.local.path}") String path) {
        this.storagePath = Paths.get(path);
    }

    @Override
    public void setPath(String path) {
        this.storagePath = Paths.get(path);
    }

    @Override
    public String getPath() {
        return this.storagePath.toString();
    }

    @Override
    public Optional<String> uploadFileToDisk(FileonDisk file) {
        try {
            if (file.data().isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }

            Path destinationFile = this.storagePath.resolve(
                            Paths.get(file.data().getOriginalFilename()))
                    .normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(this.storagePath.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }

            try (InputStream inputStream = file.data().getInputStream()) {
                logger.info("Writing to {}", this.storagePath.toString());
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }

        return Optional.empty();
    }
}
