package com.filesharing.filebin.repositories;

import com.filesharing.filebin.entities.FileMetadata;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
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

    public Optional<FileMetadata> upsert(FileMetadata newFile) {
        throw new NotImplementedException();
        /*
            MERGE INTO students
            USING (VALUES (@id, @name,@age)) AS source (ID, Name,Age)
            ON students.ID = source.ID
            WHEN MATCHED THEN
            UPDATE SET Name = source.Name,
                    age=source.age
            WHEN NOT MATCHED THEN
            INSERT (ID, Name,age)
            VALUES (source.ID, source.Name,source.age);
        */


        /// ---- DO NOT DELETE ---- WIP
//        String q = "MERGE INTO filedata" +
//        "USING (VALUES (@file_name, @file_size, @upload_date)) AS source (ID, Name,Age)" +
//        "ON students.ID = source.ID" +
//        "WHEN MATCHED THEN" +
//        "UPDATE SET " +
//                "Name = source.Name," +
//                "age=source.age" +
//        "WHEN NOT MATCHED THEN" +
//        "INSERT (ID, Name,age)" +
//        "VALUES (source.ID, source.Name,source.age);";
//
//
//        var updated = jdbcClient.sql("INSERT INTO filedata(file_name, file_size, upload_date) values(?, ?, ?)")
//                .params(newFile.getFileName(), newFile.getFileSize(), newFile.getUploadDate())
//                .update();
//
//        return Optional.ofNullable(newFile);
    }

    public List<FileMetadata> findByUploaderEmail(String user) {
        List<FileMetadata> results = jdbcClient.sql("SELECT file_name, file_size, uploader_email FROM filedata WHERE uploader_email = ?")
                .params(user).query(FileMetadata.class).list();

        return results;
    }

}
