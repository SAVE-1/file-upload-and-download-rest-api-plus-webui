package com.filesharing.filebin.repositories;

import com.filesharing.filebin.entities.FileMetadata;
import com.filesharing.filebin.repositories.interfaces.FileMetadataRepository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class FileMetadataRepositoryImpl implements FileMetadataRepository {

    private final JdbcClient jdbcClient;

    public FileMetadataRepositoryImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
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

    public int delete(String filename, String username) {
        int updated = jdbcClient.sql("DELETE FROM filedata WHERE uploader_email = :email  AND file_name = :file;")
                .param("email", username)
                .param("file", filename)
                .update();

        return updated;
    }


}
