package com.filesharing.filebin.entities;

import com.filesharing.filebin.config.constants.MyConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class FileMetadataCompositeKey  implements Serializable {

    @Column(nullable = false)
    private String uploaderEmail;

    @Column(nullable = false, name="file_name", length = MyConstants.FILENAME_MAX_LENGTH)
    private String fileName;

    public String getUploaderEmail() {
        return uploaderEmail;
    }

    public void setUploaderEmail(String uploaderEmail) {
        this.uploaderEmail = uploaderEmail;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
