package com.filesharing.filebin.integrationtests;

import com.filesharing.filebin.entities.mappers.FileMetadataResponseRowMapper;
import com.filesharing.filebin.services.FileMetadataServiceImpl;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    private FileMetadataServiceImpl fileMetadataRepositoryImpl;

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
    public void listUsersFiles() {
        String email = "s@s.com";
        List<FileMetadataResponse> f = fileMetadataRepositoryImpl.listUsersFiles(email);

        FileMetadataResponse res1 = new FileMetadataResponse("s@s.com", "1709899617664.gif",
                "2024-06-05T13:32:06", 7142809);
        FileMetadataResponse res2 = new FileMetadataResponse("s@s.com", "OIG3.1P9ahX7fRIAD0aes.jfif",
                "2024-06-05T10:59:27", 192933);

        List<FileMetadataResponse> ref = Arrays.asList(res1, res2);

        assertTrue(ref.equals(f), "User file list is incorrect");
    }

    @Test
    public void upsert() {
        String file = "example.gif";
        String email = "s@s.com";
        int file_size = 192933;
        String date  = "2024-06-05T10:59:27";

        FileMetadataResponse f = new FileMetadataResponse(email,file, date, file_size);

        fileMetadataRepositoryImpl.upsert(f);

        FileMetadataResponse t = jdbcClient.sql("SELECT file_name, uploader_email, file_size, upload_date FROM filedata where file_name = :file AND uploader_email = :email")
                .param("file", file)
                .param("email", email)
                .query(FileMetadataResponseRowMapper.getInstance()).single();

        assertTrue(t.equals(f), "Metadata are not equal");
    }

    @Test
    public void delete() {
        String file = "example3.gif";
        String email = "s@s.com";
        int file_size = 192933;
        String date  = "2024-06-05T10:59:27";

        jdbcClient.sql("INSERT INTO filedata (file_name, uploader_email, file_size, upload_date) VALUES (:file, :email, :size, :date)")
                .param("file", file)
                .param("email", email)
                .param("size", file_size)
                .param("date", date)
                .update();

        FileMetadataResponse firstSelect = jdbcClient.sql("SELECT file_name, uploader_email, file_size, upload_date FROM filedata where file_name = :file AND uploader_email = :email")
                .param("file", file)
                .param("email", email)
                .query(FileMetadataResponseRowMapper.getInstance()).single();

        FileMetadataResponse reference = new FileMetadataResponse(email, file, date, file_size);

        assertTrue(reference.equals(firstSelect), "Entry does not exist in database");

        fileMetadataRepositoryImpl.delete(email, file);

        Optional<FileMetadataResponse> shouldNotFindFile = jdbcClient.sql("SELECT file_name, uploader_email, file_size, upload_date FROM filedata where file_name = :file AND uploader_email = :email")
                .param("file", file)
                .param("email", email)
                .query(FileMetadataResponseRowMapper.getInstance()).optional();

        assertTrue(shouldNotFindFile.isEmpty() == true, "Entry found in database, should not be found (should be deleted)");
    }

    @Test
    public void getFileInformation() {
        String file = "exampleGetFileInformation.gif";
        String email = "s@s.com";
        int file_size = 192933;
        String date  = "2024-06-05T10:59:27";

        FileMetadataResponse reference = new FileMetadataResponse(email, file, date, file_size);

        jdbcClient.sql("INSERT INTO filedata (file_name, uploader_email, file_size, upload_date) VALUES (:file, :email, :size, :date)")
                .param("file", file)
                .param("email", email)
                .param("size", file_size)
                .param("date", date)
                .update();

        Optional<FileMetadataResponse> info = fileMetadataRepositoryImpl.getFileInformation(email, file);

        assertTrue(info.get().equals(reference), "Entry found in database, should not be found (should be deleted)");
    }
}
