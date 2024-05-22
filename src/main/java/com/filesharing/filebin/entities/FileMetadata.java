package com.filesharing.filebin.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Table(name = "filedata")
@Entity
public class FileMetadata {
    @EmbeddedId
    private FileMetadataCompositeKey uploaderEmailAndFileName;

    @CreationTimestamp
    @Column(nullable = false, name = "upload_date")
    private Date uploadDate;
    @Column(nullable = false, name = "file_size")
    private Integer fileSize;

    public FileMetadata(String email, String filename, Date uploadDate, Integer filesize) {
        this.uploaderEmailAndFileName = new FileMetadataCompositeKey(email, filename);
        this.uploadDate = uploadDate;
        this.fileSize = filesize;
    }

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
                this.uploaderEmailAndFileName.getUploaderEmail(), this.uploaderEmailAndFileName.getFileName(), this.uploadDate, this.fileSize);
    }

    public void setUploaderEmailAndFileName(FileMetadataCompositeKey uploaderEmailAndFileName) {
        this.uploaderEmailAndFileName = uploaderEmailAndFileName;
    }

    public FileMetadataCompositeKey getUploaderEmailAndFileName() {
        return uploaderEmailAndFileName;
    }
}
