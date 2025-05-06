package com.piusxi.admin.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class adminDatabase {
    
    public final Connection connection = null;

    public static final String DB_URL = "jdbc:mariadb://localhost:3306/admin_database";
    public static final String USER = "general";
    public static final String PASSWORD = "123456";

    /* public static final String DB_URL = "jdbc:mariadb://localhost:3306/admin_database";
    public static final String USER = "alanmitchell";
    public static final String PASSWORD = "922925"; */

    /* public static final String DB_URL = "jdbc:mariadb://localhost:3306/admin_database";
    public static final String USER = "joshuachristian";
    public static final String PASSWORD = "123456"; */

    /* public static final String DB_URL = "jdbc:mariadb://localhost:3306/admin_database";
    public static final String USER = "ethancobb";
    public static final String PASSWORD = "123456"; */

    /* public static final String DB_URL = "jdbc:mariadb://localhost:3306/admin_database";
    public static final String USER = "shannonterry";
    public static final String PASSWORD = "123456"; */

    public static Connection connect() {
        Connection connection = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Connection successful");

            return connection;
        }
        catch (SQLException se) {
            System.out.println("Error: " + se.getMessage());
        } 
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}
