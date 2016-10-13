package com.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by Renat_Gubaidullin on 10/11/2016.
 */
public class SlowloadWritingConsoleTool {
    private static int tablesNum;
    private static int columnsNum;
    private static int rowsNum;
    private static int threadsNum;

    private static int cycles;

    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "jmp_db?autoReconnect=true&useSSL=false";
    private static final String USER_NAME = "renat";
    private static final String PASSWORD = "renat";


    public static void main(String[] args) throws SQLException, InterruptedException {

        double startTime = System.currentTimeMillis();

        consoleInput();

        //System.out.println(tablesNum + " and " + columnsNum + " and " + rowsNum);
        cycles = tablesNum/threadsNum;
        System.out.println(cycles);

        if (cycles < 1){
            cycles = 1;
        }else{
            if (cycles*threadsNum < tablesNum){
                cycles = cycles + 1 ;
            }
        }

        for (int i = 0; i < cycles; i++){
            for (int k = 0; k < threadsNum; k++){
                int tableId = (i+1)*threadsNum - (threadsNum -1) + k;
                String tableName = "Table_" + String.valueOf(tableId);
                ConnectionCreator dbConnection = new ConnectionCreator(URL, DB_NAME, USER_NAME,PASSWORD);
                Connection connection = dbConnection.getConnection();
                TablesCreator tc = new TablesCreator(tableName, columnsNum, rowsNum, connection);
                tc.createTable();
            }
        }

        double estimatedTime = (System.currentTimeMillis() - startTime);

        System.out.println("Lasted time " + estimatedTime);
    }

    public static void consoleInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Set tables number");
        tablesNum = Integer.parseInt(scanner.nextLine());

        System.out.println("Set rows number");
        rowsNum = Integer.parseInt(scanner.nextLine());

        System.out.println("Set columns number");
        columnsNum = Integer.parseInt(scanner.nextLine());

        System.out.println("Set threads number");
        threadsNum = Integer.parseInt(scanner.nextLine());
    }
}
