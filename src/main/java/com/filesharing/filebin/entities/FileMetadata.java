package com.filesharing.filebin.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Table(name = "filedata", uniqueConstraints = {@UniqueConstraint(columnNames = {"uploaderEmail", "fileName"})})
@Entity
public class FileMetadata {
    @EmbeddedId
    private FileMetadataCompositeKey uploaderEmailPlusFileName;

    @CreationTimestamp
    @Column(nullable = false, name = "upload_date")
    private Date uploadDate;
    @Column(nullable = false, name = "file_size")
    private Integer fileSize;

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return String.format("File={uploaderEmail={0}, fileName='{1}', uploadDate={2}, fileSize={3}}",
                this.uploaderEmailPlusFileName.getUploaderEmail(), this.uploaderEmailPlusFileName.getFileName(), this.uploadDate, this.fileSize);
    }

    public void setUploaderEmailPlusFileName(FileMetadataCompositeKey uploaderEmailPlusFileName) {
        this.uploaderEmailPlusFileName = uploaderEmailPlusFileName;
    }

    public FileMetadataCompositeKey getUploaderEmailPlusFileName() {
        return uploaderEmailPlusFileName;
    }


}
