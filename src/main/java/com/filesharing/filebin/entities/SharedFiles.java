package com.filesharing.filebin.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Table(name = "sharedfiles")
@Entity
public class SharedFiles {
    @EmbeddedId
    private FileMetadataCompositeKey uploaderEmailAndFileName;

    @CreationTimestamp
    @Column(nullable = false, name = "link_creation")
    private Date linkCreation;

    @CreationTimestamp
    @Column(nullable = false, name = "starts")
    private Date starts;

    @CreationTimestamp
    @Column(nullable = false, name = "expires")
    private Date expires;

    @Column(nullable = false, name = "is_active")
    private boolean isActive;

    public SharedFiles(String email, String filename, Date linkCreation) {
        this.uploaderEmailAndFileName = new FileMetadataCompositeKey(email, filename);
        this.linkCreation = linkCreation;
    }

    public FileMetadataCompositeKey getUploaderEmailAndFileName() {
        return uploaderEmailAndFileName;
    }

    public void setUploaderEmailAndFileName(FileMetadataCompositeKey uploaderEmailAndFileName) {
        this.uploaderEmailAndFileName = uploaderEmailAndFileName;
    }

    public Date getLinkCreation() {
        return linkCreation;
    }

    public void setLinkCreation(Date linkCreation) {
        this.linkCreation = linkCreation;
    }

    public Date getStarts() {
        return starts;
    }

    public void setStarts(Date starts) {
        this.starts = starts;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
