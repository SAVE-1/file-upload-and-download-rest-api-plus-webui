package com.filesharing.filebin.services.interfaces;

import com.filesharing.filebin.responses.FileMetadataResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FileMetadataRepository {
    Optional<FileMetadataResponse> upsert(String filename, String username, int filesize, LocalDateTime uploadDate);
    List<FileMetadataResponse> listUsersFiles(String user);
    Optional<FileMetadataResponse> delete(String filename, String username);
    Optional<FileMetadataResponse> getFileInformation(String username, String filename);
    Optional<FileMetadataResponse> insertNewFile(String filename, String username, int filesize, String uploadDate);
}
