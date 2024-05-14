package com.filesharing.filebin.repositories;

import com.filesharing.filebin.entities.FileMetadata;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class FileMetadataRepositoryImpl implements FileMetadataRepository {

    private final JdbcClient jdbcClient;

    public FileMetadataRepositoryImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public String helloWorld() {
        return "Hello world";
    }

    public void writeHelloWorldToDatabase() {
        var updated = jdbcClient.sql("INSERT INTO test(name, timestamp) values(?, ?)")
                .params("HELLO WORLD from Method:writeHelloWorldToDatabase()", LocalDateTime.now())
                .update();

        Assert.state(updated == 1, "Failed to create run jee");
    }

    public String echo(String str) {
        var updated = jdbcClient.sql("INSERT INTO test(name, timestamp) values(?, ?)")
                .params(str, LocalDateTime.now())
                .update();

        if (updated > 0) {
            return str;
        }

        return "";
    }

    public Optional<FileMetadata> insertNew(FileMetadata newFile) {
        var updated = jdbcClient.sql("INSERT INTO filedata(file_name, file_size, upload_date) values(?, ?, ?)")
                .params(newFile.getFileName(), newFile.getFileSize(), newFile.getUploadDate())
                .update();

        return Optional.ofNullable(newFile);
    }
}
