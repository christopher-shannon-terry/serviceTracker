package com.piusxi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class databaseConnectionTest {
    
    // Database connection parameters
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/admin_database";
    private static final String USER = "alanmitchell";
    private static final String PASSWORD = "922925";
    
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        
        try {
            // Register JDBC driver
            Class.forName("org.mariadb.jdbc.Driver");
            
            // Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Connection successful!");
        } 
        catch(SQLException se) {
            // Handle JDBC errors
            System.out.println("SQL Error: " + se.getMessage());
            se.printStackTrace();
        } 
        catch(Exception e) {
            // Handle Class.forName errors
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } 
        finally {
            // Finally block to close resources
            try {
                if(stmt != null) stmt.close();
            } 
            catch(SQLException se) {
                // Do nothing
            }
            try {
                if(conn != null) conn.close();
            } 
            catch(SQLException se) {
                se.printStackTrace();
            }
            System.out.println("\nDatabase connection closed.");
        }
    }
}
