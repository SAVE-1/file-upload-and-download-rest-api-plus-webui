package com.filesharing.filebin.filebin;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.filesharing.filebin.controller.FileUploadController;
import com.filesharing.filebin.repositories.FileMetadataRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileUploadController.class)
public class FileUploadControllerJunitTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    FileMetadataRepositoryImpl fileMetadataRepositoryImpl;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldUpsert() throws Exception {
        String filename = "climbing.pdf";
        String user = "s@s.com";
        int filesize = 3;
        String uploaddate = "2022-12-12 00:00:00.0000";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(uploaddate, formatter);

        Mockito.when(fileMetadataRepositoryImpl.upsert(filename, user, filesize, dateTime)).thenReturn(1);

//        mockMvc.perform(get("/api/todos"))
//                .andExpectAll(
//                        status().isOk(),
//                        content().json(objectMapper.writeValueAsString(todos))
//                );


    }


}
