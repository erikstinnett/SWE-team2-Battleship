package Server;

import java.sql.*;
import java.io.*;
import java.util.*;

public class Database {
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private ResultSetMetaData rmd;

    public void setConnection(String fn) {
        // Create a properties object
        Properties prop = new Properties();

        // Use a FileInputStream as input to the Properties object for reading the file
        // the db.properties file
        FileInputStream fis;

        try {
            fis = new FileInputStream(fn);
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            prop.load(fis);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Get the username,password, and url
        String url = prop.getProperty("url");
        String user = prop.getProperty("user");
        String pass = prop.getProperty("password");

        // Set the conn object
        try {
            conn = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public ArrayList<String> query(String query) {
        // instantiate arraylist
        ArrayList<String> records = new ArrayList<>();
        try {
            // Using the Conn object create a Statement object
            stmt = conn.createStatement();
            // Usinng the Statement object executeQuery using the input query (Returns the
            // ResultSet)
            rs = stmt.executeQuery(query);

            // //Get metadata about the query
            rmd = rs.getMetaData();

            // //Get the # of columns
            int no_columns = rmd.getColumnCount();

            // Use a while loop to process the rows - Create a comma delimitted record from
            // each field
            // and add comma delimited record to ArrayList
            while (rs.next()) // iterator
            {
                String row = "";
                for (int i = 0; i < no_columns; i++) {
                    row += rs.getString(i + 1);
                    // avoid placing comma at the end of last item
                    if (i != no_columns - 1)
                        row += " , ";
                }
                records.add(row);
            }

        } catch (SQLException e) {
            // return null since the query did not go through
            return null;
        }

        // return to show if it exists or not
        if (records.isEmpty()) {
            return null;
        } else {
            return records;
        }
    }

    public String queryCheckPassword(String query) {
        String retrievedPW = "";
        try {
            // Using the Conn object create a Statement object
            stmt = conn.createStatement();
            // Using the Statement object executeQuery using the input query (Returns the
            // ResultSet)
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                retrievedPW = rs.getString(1);
            }

        } catch (SQLException e) {

            return "";
        }
        return retrievedPW;
    }

    public void executeDML(String dml) throws SQLException {
        Statement stmt;
        // 1. Use the Conn object to create a Statement object
        stmt = conn.createStatement();
        // 2. Run DML on the execute method of Statement
        stmt.execute(dml);
    }
}
