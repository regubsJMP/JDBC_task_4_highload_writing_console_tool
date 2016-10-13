package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Renat_Gubaidullin on 10/11/2016.
 */
public class ConnectionCreator {

    private String URL;
    private String DB_NAME;
    private String USER_NAME;
    private String PASSWORD;

    public ConnectionCreator(String URL, String DB_NAME, String USER_NAME, String PASSWORD) {
        this.URL = URL;
        this.DB_NAME = DB_NAME;
        this.USER_NAME = USER_NAME;
        this.PASSWORD = PASSWORD;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL + DB_NAME, USER_NAME, PASSWORD);
    }
}
