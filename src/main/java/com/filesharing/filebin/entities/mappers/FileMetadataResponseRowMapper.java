package com.filesharing.filebin.entities.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.filesharing.filebin.responses.FileMetadataResponse;
import org.springframework.jdbc.core.RowMapper;

public class FileMetadataResponseRowMapper implements RowMapper<FileMetadataResponse> {

    private FileMetadataResponseRowMapper() {}

    private static final FileMetadataResponseRowMapper INSTANCE = new FileMetadataResponseRowMapper();

    public static FileMetadataResponseRowMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public FileMetadataResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new FileMetadataResponse(
                rs.getString("uploader_email"),
                rs.getString("file_name"),
                rs.getTimestamp("upload_date").toLocalDateTime().withNano(0).toString(),
                Integer.valueOf(rs.getInt("file_size"))
        );
    }
}
