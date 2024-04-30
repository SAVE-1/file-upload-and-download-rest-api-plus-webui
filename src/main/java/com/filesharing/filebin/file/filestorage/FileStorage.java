package com.filesharing.filebin.file.filestorage;

public interface FileStorage {

    FileUploadResponse upload(FileUploadRequest fileUploadRequest);
}
