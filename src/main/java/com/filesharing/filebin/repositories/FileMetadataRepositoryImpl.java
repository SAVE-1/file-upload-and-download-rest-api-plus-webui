package com.filesharing.filebin.repositories;

import com.filesharing.filebin.entities.FileMetadata;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

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

    @Transactional
    public int upsert(String filename, String username, int filesize, String uploadDate) {
        String que = """
                UPDATE filedata WITH (UPDLOCK, SERIALIZABLE) SET file_size = :file_size, upload_date = :upload_date WHERE uploader_email = :uploader_email and file_name = :file_name;
                IF @@ROWCOUNT = 0
                BEGIN
                INSERT INTO filedata(file_name, file_size, upload_date, uploader_email) values(:file_name, :file_size, :upload_date, :uploader_email);
                END;
                """;

        var updated = jdbcClient.sql(que)
                .param("file_name", filename)
                .param("uploader_email", username)
                .param("file_size", filesize)
                .param("upload_date", uploadDate)
                .update();

        return updated;
    }

    public List<FileMetadata> findByUploaderEmail(String user) {
        List<FileMetadata> results = jdbcClient.sql("SELECT file_name, file_size, uploader_email FROM filedata WHERE uploader_email = ?")
                .params(user).query(FileMetadata.class).list();

        return results;
    }

    public int insertNewFile(String filename, String username, int filesize, String uploadDate) {
        int updated = jdbcClient.sql("INSERT INTO filedata(file_name, file_size, upload_date, uploader_email) values(?, ?, ?, ?)")
                .params(filename, filesize, uploadDate, username)
                .update();

        return updated;
    }


}
