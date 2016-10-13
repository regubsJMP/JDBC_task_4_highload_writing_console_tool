package com.test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Renat_Gubaidullin on 10/13/2016.
 */
public class ThreadEngine implements Runnable{

    private final String URL = "jdbc:mysql://localhost:3306/";
    private final String DB_NAME = "jmp_db?autoReconnect=true&useSSL=false";
    private final String USER_NAME = "renat";
    private final String PASSWORD = "renat";

    private int columnsNum;
    private int rowsNum;
    private String tableName;
    private Double startTime;
    private String threadName;

    public ThreadEngine(int columnsNum, int rowsNum, String tableName, Double startTime, String threadName) {
        this.columnsNum = columnsNum;
        this.rowsNum = rowsNum;
        this.tableName = tableName;
        this.startTime = startTime;
        this.threadName = threadName;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(threadName);
        ConnectionCreator dbConnection = new ConnectionCreator(URL, DB_NAME, USER_NAME,PASSWORD);
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        TablesCreator tc = new TablesCreator(tableName, columnsNum, rowsNum, connection);
        try {
            tc.createTable();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        double estimatedTime = (System.currentTimeMillis() - startTime);
        System.out.println(Thread.currentThread().getName() +  " lasted "+  estimatedTime + " ms");

        //Thread.currentThread().stop();
        /*
        Thread.currentThread().interrupt();
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
        /*try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}
