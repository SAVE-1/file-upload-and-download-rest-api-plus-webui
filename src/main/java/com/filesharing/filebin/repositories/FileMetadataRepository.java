package com.filesharing.filebin.repositories;

import com.filesharing.filebin.entities.FileMetadata;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FileMetadataRepository {
    void writeHelloWorldToDatabase();
    String helloWorld();
    String echo(String str);
    Optional<FileMetadata> upsert(FileMetadata newFile);
    List<FileMetadata> findByUploaderEmail(String user);
}
