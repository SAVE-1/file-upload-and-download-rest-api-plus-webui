package com.filesharing.filebin.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.filesharing.filebin.constants.MyConstants;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Table(name = "filedata", uniqueConstraints={@UniqueConstraint(columnNames = {"uploaderEmail", "fileName"})})
@Entity
public class FileMetadata {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(nullable = false)
        @JsonIgnore
        private Integer id;

        @Column(nullable = false)
        private String uploaderEmail;

        @Column(nullable = false, name="file_name", length = MyConstants.FILENAME_MAX_LENGTH)
        private String fileName;

        @CreationTimestamp
        @Column(nullable = false, name="upload_date")
        private Date uploadDate;
        @Column(nullable = true, name="file_size")
        private Integer fileSize;

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public String getFileName() {
                return fileName;
        }

        public void setFileName(String fileName) {
                this.fileName = fileName;
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
                return String.format("File={id={0}, fileName='{1}', uploadDate={2}, fileSize={3}}",
                        this.id, this.fileName, this.uploadDate, this.fileSize);
        }


        public String getUploaderEmail() {
                return uploaderEmail;
        }

        public void setUploaderEmail(String uploaderEmail) {
                this.uploaderEmail = uploaderEmail;
        }
}
