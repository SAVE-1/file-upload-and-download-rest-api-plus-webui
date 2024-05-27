package com.filesharing.filebin.repositories.interfaces;

import com.filesharing.filebin.responses.FileMetadataResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FileMetadataRepository {
    int upsert(String filename, String username, int filesize, LocalDateTime uploadDate);
    List<FileMetadataResponse> listUsersFiles(String user);
    int delete(String filename, String username);
    Optional<FileMetadataResponse> getFileInformation(String username, String filename);
}
