package com.filesharing.filebin.file.database;


import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class FileJdbcDatabaseRepository implements FileDatabaseRepository {

    private final JdbcClient jdbcClient;

    public FileJdbcDatabaseRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public String helloWorld() {
        return "Hello world";
    }

    public void writeHelloWorldToDatabase() {
        var updated = jdbcClient.sql("INSERT INTO test(name) values(?)")
                .params("HELLO WORLD from Method:writeHelloWorldToDatabase()")
                .update();

        Assert.state(updated == 1, "Failed to create run jee");
    }

}
