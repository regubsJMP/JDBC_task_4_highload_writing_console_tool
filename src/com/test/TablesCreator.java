package com.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Renat_Gubaidullin on 10/11/2016.
 */
public class TablesCreator {
    private String tableName;
    private int columnsNumber;
    private int rowsNumber;
    private Connection connection;
    private Statement statement;
    private String tableQuery = "";
    private String columnsQuery = ",";
    private String rowsQuery;
    private String fieldsSet = "(";
    private String valuesSet = "(";

    ArrayList<String> typeList = new ArrayList<String>() {{add("INT"); add("YEAR"); add("VARCHAR(50)");}};
    Random randomGenerator = new Random();

    public TablesCreator(String tableName, int columnsNumber, int rowsNumber, Connection connection) {
        this.columnsNumber = columnsNumber;
        this.rowsNumber = rowsNumber;
        this.connection = connection;
        this.tableName = tableName;

    }

    public void  createTable() throws InterruptedException {

        ArrayList<String> nameSet = new ArrayList<String>();
        ArrayList<String> typeSet = new ArrayList<String>();


        for (int i = 0; i < columnsNumber; i++ ){
            int index = randomGenerator.nextInt(typeList.size());
            String type = typeList.get(index);
            columnsQuery = columnsQuery.concat("column_" + i + " " + type + " NOT NULL,");
            nameSet.add("column_" + i);
            typeSet.add(type + i);
        }

        tableQuery = "CREATE TABLE " +  tableName + "(id INT NOT NULL AUTO_INCREMENT" + columnsQuery +
                    "PRIMARY KEY ( id ))";
        //tableQuery = "CREATE TABLE ";

        //CREATE TABLE null(id INT NOT NULL AUTO_INCREMENT,PRIMARY KEY ( id ));

        System.out.println(tableQuery);

        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement.execute(tableQuery);
            System.out.println("Table is created");
        } catch (SQLException e) {
            System.out.println("bad table creation Query");
            e.printStackTrace();
            System.exit(0);
        }

        for (int i = 0; i < columnsNumber; i++){
            fieldsSet = fieldsSet.concat(nameSet.get(i)+", ");
        }

        fieldsSet = fieldsSet.substring(0, fieldsSet.length() - 1) + ")";

        for (int k = 0; k < rowsNumber; k++){
            for (int i = 0; i < columnsNumber; i++){
                /*if(i == 0){
                    valuesSet = "(" + i + ", ";
                */
                String typeSemaphore = typeSet.get(i);
                typeSemaphore = typeSemaphore.substring(0, typeSemaphore.length() - 1);

                if(typeSemaphore.equals("INT")){
                    int randomInt = randomGenerator.nextInt(100);
                    valuesSet = valuesSet.concat(randomInt + ", ");
                }else
                {
                    if(typeSemaphore.equals("YEAR")){
                        int randomYear = randomGenerator.nextInt(100) + 1950;
                        valuesSet = valuesSet.concat(randomYear  + ", ");
                    }else
                    {
                        if(typeSemaphore.equals("VARCHAR(50)")){
                            String randomString = "'" + "StringValue_" + randomGenerator.nextInt(100) + "'";
                            valuesSet = valuesSet.concat(randomString + ", ");
                        }
                    }
                }
                Thread.sleep(50);
            }

            valuesSet = valuesSet.substring(0, valuesSet.length() - 1) + ")";

            rowsQuery = "INSERT INTO " + tableName + fieldsSet.substring(0, fieldsSet.length() - 2) + ")VALUES" +
                    valuesSet.substring(0, valuesSet.length() - 2) +");";

            System.out.println(rowsQuery);

            Thread.sleep(50);

            try {
                statement.execute(rowsQuery);
                System.out.println("Table is created");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            valuesSet = "(";
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Thread.sleep(50);
  /*
        use near 'null(id INT NOT NULL AUTO_INCREMENT,PRIMARY KEY ( id ))' a
        INSERT INTO tutorials_tbl
                ->(tutorial_title, tutorial_author, submission_date)
                ->VALUES
                ->("Learn PHP", "John Poul", NOW());

         CREATE TABLE null(id INT NOT NULL AUTO_INCREMENT,PRIMARY KEY ( id ));

        mysql> CREATE TABLE tutorials_tbl(
        -> tutorial_id INT NOT NULL AUTO_INCREMENT,
        -> tutorial_title VARCHAR(100) NOT NULL,
        -> tutorial_author VARCHAR(40) NOT NULL,
        -> submission_date DATE,
        -> PRIMARY KEY ( tutorial_id )
                -> );

            */
    }







}
