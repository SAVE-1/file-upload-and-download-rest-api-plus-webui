package com.filesharing.filebin.file.database;

public interface FileDatabaseRepository {
    void writeHelloWorldToDatabase();
    String helloWorld();

    String echo(String str);


}
