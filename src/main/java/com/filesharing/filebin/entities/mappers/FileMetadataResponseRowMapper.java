package com.filesharing.filebin.entities.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.filesharing.filebin.responses.FileMetadataResponse;
import org.springframework.jdbc.core.RowMapper;

/**
 * Result mapper for filedata-table
 */
public class FileMetadataResponseRowMapper implements RowMapper<FileMetadataResponse> {

    private FileMetadataResponseRowMapper() {}

    private static final FileMetadataResponseRowMapper INSTANCE = new FileMetadataResponseRowMapper();

    /**
     * Do note, nanoseconds are cut out from {@link package com.filesharing.filebin.responses.FileMetadataResponse}
     * instances in queries that use FileMetadataResponseRowMapper.getInstance()
     */
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
