package com.filesharing.filebin.entities;

import com.filesharing.filebin.config.constants.MyConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;


// https://docs.jboss.org/hibernate/orm/5.3/userguide/html_single/Hibernate_User_Guide.html#identifiers-composite-aggregated
@Embeddable
public class FileMetadataCompositeKey implements Serializable {

    @Column(nullable = false, name = "uploader_email")
    private String uploaderEmail;

    @Column(nullable = false, name = "file_name", length = MyConstants.FILENAME_MAX_LENGTH)
    private String fileName;

    public FileMetadataCompositeKey(String uploaderEmail, String fileName) {
        this.uploaderEmail = uploaderEmail;
        this.fileName = fileName;
    }

    private FileMetadataCompositeKey() {
    }

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

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        FileMetadataCompositeKey pk = (FileMetadataCompositeKey) o;
        return Objects.equals( uploaderEmail, pk.uploaderEmail ) &&
                Objects.equals( fileName, pk.fileName );
    }

    @Override
    public int hashCode() {
        return Objects.hash( uploaderEmail, fileName );
    }

}
