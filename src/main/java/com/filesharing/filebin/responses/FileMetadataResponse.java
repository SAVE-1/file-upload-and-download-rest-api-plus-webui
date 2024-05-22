package com.filesharing.filebin.responses;

import java.util.Date;

public class FileMetadataResponse {
    private String email;
    private String filename;
    private Date uploaddate;
    private int size;

    public FileMetadataResponse(String email, String filename, Date uploaddate, int size) {
        this.email = email;
        this.filename = filename;
        this.uploaddate = uploaddate;
        this.size = size;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Date getUploaddate() {
        return uploaddate;
    }

    public void setUploaddate(Date uploaddate) {
        this.uploaddate = uploaddate;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
