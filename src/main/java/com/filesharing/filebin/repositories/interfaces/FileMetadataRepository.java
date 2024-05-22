package com.filesharing.filebin.repositories.interfaces;

import com.filesharing.filebin.entities.FileMetadata;
import com.filesharing.filebin.responses.FileMetadataResponse;

import java.util.List;

public interface FileMetadataRepository {
    int upsert(String filename, String username, int filesize, String uploadDate);
    List<FileMetadataResponse> findByUploaderEmail(String user);
    int delete(String filename, String username);
}
