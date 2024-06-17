package com.filesharing.filebin.responses;

public class FileMetadataResponse {
    private String email;
    private String filename;
    private String uploaddate;
    private Integer size;

    public FileMetadataResponse(String email, String filename, String uploaddate, Integer size) {
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

    public String getUploaddate() {
        return uploaddate;
    }

    public void setUploaddate(String uploaddate) {
        this.uploaddate = uploaddate;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileMetadataResponse that = (FileMetadataResponse) o;
        return email.equals(that.email)
                && filename.equals(that.filename)
                && uploaddate.equals(that.uploaddate)
                && size.equals(that.size);
    }

}
