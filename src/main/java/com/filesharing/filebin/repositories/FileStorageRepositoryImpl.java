package com.filesharing.filebin.repositories;

import com.filesharing.filebin.controller.FileUploadController;
import com.filesharing.filebin.repositories.filestorage.FileonDisk;
import com.filesharing.filebin.repositories.filestorage.StorageException;
import com.filesharing.filebin.repositories.filestorage.StorageFileNotFoundException;
import com.filesharing.filebin.repositories.interfaces.FileStorageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;


/*

    TODO: rewrite file write and read
          because currently, if two users upload file with same name, they will be overwritten
          current file hierarchy is flat, should be: [user name folder]/file

*/
@Service
public class FileStorageRepositoryImpl implements FileStorageRepository {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    public Path storagePath;

    public FileStorageRepositoryImpl(@Value("${filebin.storage.local.path}") String path) {
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
                            Paths.get(file.data().getName()))
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

    public Resource getUploadedFile(String name) {
        try {
            Path file = load(name);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + name);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + name, e);
        }
    }

    public Path load(String filename) {
        return storagePath.resolve(filename);
    }

    public Boolean doesFileExist(String fileName) {
        try {
            Path file = load(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return true;
            } else {
                return false;
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + fileName, e);
        }
    }

    public Boolean deleteFile(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            return resource.getFile().delete();
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
