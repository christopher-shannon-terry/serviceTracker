package com.piusxi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class databaseConnectionTest {
    
    // Database connection parameters
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/testdb";
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
            
            // Create a statement
            stmt = conn.createStatement();
            
            // Drop table if exists to avoid conflicts
            String dropTable = "DROP TABLE IF EXISTS employees";
            stmt.executeUpdate(dropTable);
            
            // Create table
            String createTable = "CREATE TABLE employees (" +
                                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                                "name VARCHAR(100), " +
                                "position VARCHAR(100), " +
                                "salary DOUBLE)";
            stmt.executeUpdate(createTable);
            System.out.println("Table 'employees' created successfully.");
            
            // Insert data
            System.out.println("Inserting records into the table...");
            
            // Using PreparedStatement for better security and performance
            String insertSQL = "INSERT INTO employees (name, position, salary) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);
            
            // Insert first employee
            pstmt.setString(1, "John Doe");
            pstmt.setString(2, "Software Engineer");
            pstmt.setDouble(3, 75000.00);
            pstmt.executeUpdate();
            
            // Insert second employee
            pstmt.setString(1, "Jane Smith");
            pstmt.setString(2, "Project Manager");
            pstmt.setDouble(3, 85000.00);
            pstmt.executeUpdate();
            
            // Insert third employee
            pstmt.setString(1, "Robert Johnson");
            pstmt.setString(2, "Database Administrator");
            pstmt.setDouble(3, 78000.00);
            pstmt.executeUpdate();
            
            System.out.println("Records inserted successfully.");
            
            // Retrieve and display records
            System.out.println("\nRetrieving records from the database...");
            String selectSQL = "SELECT * FROM employees";
            ResultSet rs = stmt.executeQuery(selectSQL);
            
            // Process the result set
            System.out.println("\nEmployee Records:");
            System.out.println("--------------------------------------------------");
            System.out.println("ID | Name           | Position                | Salary");
            System.out.println("--------------------------------------------------");
            
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String position = rs.getString("position");
                double salary = rs.getDouble("salary");
                
                System.out.printf("%-3d| %-15s| %-24s| $%.2f%n", 
                                 id, name, position, salary);
            }
            System.out.println("--------------------------------------------------");
            
            // Close resources
            rs.close();
            pstmt.close();
            
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
