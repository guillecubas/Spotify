package es.ulpgc.spotify.downloader;

import java.sql.*;

public class SQLiteDBHelper {
    private Statement statement;

    public SQLiteDBHelper() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:spotify.db");
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable(String tableName, String columns) {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + columns + ");";
        try {
            statement.executeUpdate(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertData(String tableName, String columns, String values) {
        String insertDataSQL = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ");";
        try {
            statement.executeUpdate(insertDataSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public ResultSet selectData(String table, String columns, String condition) throws SQLException {
        String selectSQL = "SELECT " + columns + " FROM " + table;
        if (condition != null) {
            selectSQL += " WHERE " + condition;
        }
        return statement.executeQuery(selectSQL);
    }

}




