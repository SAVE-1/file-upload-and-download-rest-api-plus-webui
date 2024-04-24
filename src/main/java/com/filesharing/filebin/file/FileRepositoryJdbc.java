package com.filesharing.filebin.file;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class FileRepositoryJdbc implements FileRepository {

    private final JdbcClient jdbcClient;

    public FileRepositoryJdbc(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public String helloWorld() {
        return "Hello world";
    }

}
