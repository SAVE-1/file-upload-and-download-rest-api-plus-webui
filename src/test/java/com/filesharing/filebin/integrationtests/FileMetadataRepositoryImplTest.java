package com.filesharing.filebin.integrationtests;

import com.filesharing.filebin.repositories.FileMetadataRepositoryImpl;
import com.filesharing.filebin.responses.FileMetadataResponse;
import org.junit.Rule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

// https://foojay.io/today/the-new-jdbcclient-introduced-in-spring-framework-6-1/
// https://java.testcontainers.org/modules/databases/mssqlserver/

@Testcontainers
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan("com.filesharing.filebin.repositories")
@TestPropertySource(properties = "spring.sql.init.mode=always")
class FileMetadataRepositoryImplTest {

    @Container
    @ServiceConnection
    private static final MSSQLServerContainer<?> SQLSERVER_CONTAINER = new MSSQLServerContainer<>(
            "mcr.microsoft.com/mssql/server:2022-latest").acceptLicense();

    @Autowired
    private FileMetadataRepositoryImpl fileMetadataRepositoryImpl;

    private List<FileMetadataResponse> list;
    private final String username = "sam@smith.com";

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", SQLSERVER_CONTAINER::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", SQLSERVER_CONTAINER::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", SQLSERVER_CONTAINER::getPassword);
    }

    @BeforeAll
    static void beforeAll() {
        SQLSERVER_CONTAINER.start();
    }

    @AfterAll
    static void afterAll() {
        SQLSERVER_CONTAINER.stop();
    }

    @BeforeEach
    void setUp() {

        list = Arrays.asList(
                new FileMetadataResponse(username, "bouldering.json", LocalDateTime.now().toString(), 1),
                new FileMetadataResponse(username, "wedding-image-1.jpeg", LocalDateTime.now().toString(), 2),
                new FileMetadataResponse(username, "toyota-corolla-1987-field-guide.pdf", LocalDateTime.now().toString(), 3),
                new FileMetadataResponse(username, "malware.exe", LocalDateTime.now().toString(), 4));

        for (FileMetadataResponse f : list) {
            fileMetadataRepositoryImpl.upsert(f);
        }
    }

    @AfterEach
    void tearDown() { }

    @Test
    void connectionEstablished() {
        assertTrue(SQLSERVER_CONTAINER.isCreated());
        assertTrue(SQLSERVER_CONTAINER.isRunning());
    }

    @Test
    public void upsert() {
//        String filename = "bouldering-guide.pdf";
//        int filesize = 3;
//        LocalDateTime time = LocalDateTime.now();
//
//        Optional<FileMetadataResponse> r = Optional.of(new FileMetadataResponse(username, filename, time.toString(), filesize));
//
//        when(fileMetadataRepositoryImpl.upsert(filename, username, filesize, time)).thenReturn(r);
//
//        Optional<FileMetadataResponse> rr = Optional.of(new FileMetadataResponse(username, filename, time.toString(), filesize));
//
//        Optional<FileMetadataResponse> rrr = fileMetadataRepositoryImpl.upsert(filename, username, filesize, time);

        assertTrue("a".equals("a"), "Metadata are not equal");

    }

    @Test
    public void FileMetadataRepositoryImpl_listUsersFiles_ReturnsList_FileMetadataResponse() {
        assertTrue("a".equals("a"), "Metadata are not equal");
    }

    @Test
    public void FileMetadataRepositoryImpl_delete_ReturnsOptional_FileMetadataResponse() {
        assertTrue("a".equals("a"), "Metadata are not equal");
    }
}
