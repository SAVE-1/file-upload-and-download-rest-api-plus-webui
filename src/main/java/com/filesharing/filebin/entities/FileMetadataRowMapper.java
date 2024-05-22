package com.filesharing.filebin.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import com.filesharing.filebin.responses.FileMetadataResponse;
import org.springframework.jdbc.core.RowMapper;

public class FileMetadataRowMapper implements RowMapper<FileMetadataResponse> {

    private FileMetadataRowMapper() {}

    private static final FileMetadataRowMapper INSTANCE = new FileMetadataRowMapper();

    public static FileMetadataRowMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public FileMetadataResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new FileMetadataResponse(
                rs.getString("uploader_email"),
                rs.getString("file_name"),
                rs.getDate("upload_date"),
                Integer.valueOf(rs.getInt("file_size"))
        );
    }

    private Instant getInstantFromTimestamp(Timestamp timestamp) {
        return (timestamp != null) ? timestamp.toInstant() : null;
    }
}
