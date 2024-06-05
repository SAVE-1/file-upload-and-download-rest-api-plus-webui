package com.filesharing.filebin.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
// import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class FileMetadataRepositoryImplTest {

    @InjectMocks
    private FileMetadataRepositoryImpl fileMetadataRepositoryImpl;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void FileMetadataRepositoryImpl_upsert_ReturnsInt() {
        String filename = "bouldering-guide.pdf";
        String username = "sam@smith.com";
        int filesize = 3;
        LocalDateTime time = LocalDateTime.now();

        Mockito.when(fileMetadataRepositoryImpl.upsert(filename, username, filesize, time)).thenReturn(1);




    }

}


