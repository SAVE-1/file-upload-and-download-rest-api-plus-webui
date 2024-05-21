package com.filesharing.filebin.repositories;

import com.filesharing.filebin.entities.FileMetadata;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FileMetadataRepository {
    int upsert(String filename, String username, int filesize, String uploadDate);
    List<FileMetadata> findByUploaderEmail(String user);
}
