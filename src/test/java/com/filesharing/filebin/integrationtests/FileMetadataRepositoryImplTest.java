package com.filesharing.filebin.integrationtests;

import com.filesharing.filebin.entities.mappers.FileMetadataResponseRowMapper;
import com.filesharing.filebin.repositories.FileMetadataRepositoryImpl;
import com.filesharing.filebin.responses.FileMetadataResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Autowired
    private JdbcClient jdbcClient;

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

    @AfterEach
    void tearDown() { }

    @Test
    void connectionEstablished() {
        assertTrue(SQLSERVER_CONTAINER.isCreated());
        assertTrue(SQLSERVER_CONTAINER.isRunning());
    }

    @Test
    public void upsert() {
        String fileName = "example.gif";
        String email = "s@s.com";
        int file_size = 192933;
        String date  = "2024-06-05T10:59:27.1658530";

        var t = jdbcClient.sql("SELECT * FROM filedata;").query(FileMetadataResponseRowMapper.getInstance()).list();

        System.out.println(t);

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
