package com.filesharing.filebin.file;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

@Repository
public class FileRepositoryJdbc implements FileRepository {

    private final JdbcClient jdbcClient;

    public FileRepositoryJdbc(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public String helloWorld() {
        return "Hello world";
    }

    public void writeHelloWorldToDatabase() {
        var updated = jdbcClient.sql("INSERT INTO test(name) values(?)")
                .params("HELLO WORLD from Method:writeHelloWorldToDatabase()")
                .update();

        // String name = this.getClass().getEnclosingMethod().getName();

        Assert.state(updated == 1, "Failed to create run jee");
    }

}
