package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static String URL = "jdbc:mysql://localhost:3306/pharmacy_db";
    private static String USER = "root";
    private static String PASSWORD = "";

    static {
        try (java.io.InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input != null) {
                java.util.Properties prop = new java.util.Properties();
                prop.load(input);
                URL = prop.getProperty("db.url", URL);
                USER = prop.getProperty("db.user", USER);
                PASSWORD = prop.getProperty("db.password", PASSWORD);
            } else {
                System.out.println("Sorry, unable to find db.properties. Using default settings.");
            }
        } catch (java.io.IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
