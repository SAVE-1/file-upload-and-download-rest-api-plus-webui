package com.filesharing.filebin.repositories;

import com.filesharing.filebin.entities.FileMetadataResponseRowMapper;
import com.filesharing.filebin.repositories.interfaces.FileMetadataRepository;
import com.filesharing.filebin.responses.FileMetadataResponse;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class FileMetadataRepositoryImpl implements FileMetadataRepository {

    private final JdbcClient jdbcClient;

    public FileMetadataRepositoryImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Transactional
    public int upsert(String filename, String username, int filesize, LocalDateTime uploadDate) {
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
                .param("upload_date", uploadDate.toString())
                .update();

        return updated;
    }

    public List<FileMetadataResponse> listUsersFiles(String user) {
        List<FileMetadataResponse> results = jdbcClient.sql("SELECT file_name, upload_date, file_size, uploader_email FROM filedata WHERE uploader_email = ?")
                .params(user).query(FileMetadataResponseRowMapper.getInstance()).list();

        return results;
    }

    public int insertNewFile(String filename, String username, int filesize, String uploadDate) {
        int updated = jdbcClient.sql("INSERT INTO filedata(file_name, file_size, upload_date, uploader_email) values(?, ?, ?, ?)")
                .params(filename, filesize, uploadDate, username)
                .update();

        return updated;
    }

    public int delete(String username, String filename) {
        int updated = jdbcClient.sql("DELETE FROM filedata WHERE uploader_email = :email  AND file_name = :file;")
                .param("email", username)
                .param("file", filename)
                .update();

        return updated;
    }

    public Optional<FileMetadataResponse> getFileInformation(String username, String filename) {
        FileMetadataResponse results = jdbcClient.sql("SELECT file_name, upload_date, file_size, uploader_email FROM filedata WHERE uploader_email = :username AND file_name = :filename")
                .param("username", username)
                .param("filename", filename)
                .query(FileMetadataResponseRowMapper.getInstance()).single();
        Optional<FileMetadataResponse> opt = Optional.of(results);

        return opt;
    }


}
