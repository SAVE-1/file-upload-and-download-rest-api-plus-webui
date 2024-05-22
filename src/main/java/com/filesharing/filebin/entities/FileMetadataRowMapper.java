package com.filesharing.filebin.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import org.springframework.jdbc.core.RowMapper;

public class FileMetadataRowMapper implements RowMapper<FileMetadata> {

    private FileMetadataRowMapper() {}

    private static final FileMetadataRowMapper INSTANCE = new FileMetadataRowMapper();

    public static FileMetadataRowMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public FileMetadata mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new FileMetadata(
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
