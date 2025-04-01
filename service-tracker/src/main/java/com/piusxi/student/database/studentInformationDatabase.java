package com.piusxi.student.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class is responsible for connecting to the student_info database and inserting values
 * 
 */
public class studentInformationDatabase {

    public final Connection connection = null;
    public final Statement statement = null;

    public static final String DB_URL = "jdbc:mariadb://localhost:3306/student_info";
    public static final String USER = "alanmitchell";
    public static final String PASSWORD = "922925";

    /**
     * This method connects to the student_info database
     * @param connection -> Connection initializer
     */
    public static void connect(Connection connection) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");

            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Connection successful");
        }
        catch (SQLException se) {
            System.out.println("Error: " + se.getMessage());
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * This methods inserts the first name into the database
     * @param firstName -> first name of student
     * @param statement -> Statement initializer
     * @param connection -> Connection initializer
     */
    public static void insertFirstName(String firstName, Statement statement, Connection connection) {
        try {
            statement = connection.createStatement();

            String insertFirstNameSQL = "INSERT INTO Students (first_name) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertFirstNameSQL);

            preparedStatement.setString(1, firstName);
        }
        catch (SQLException se) {
            System.out.println("Error: " + se.getMessage());
        }
        
    }

    /**
     * This method inserts the last name into the database
     * @param lastName -> last name of student
     * @param statement -> Statement initializer
     * @param connection -> Connection initializer
     */
    public static void insertLastName(String lastName, Statement statement, Connection connection) {

    }

    /**
     * This method inserts the student ID into the database
     * @param studentId -> unique student ID of student
     * @param statement -> Statement initializer
     * @param connection -> Connection initializer
     */
    public static void insertStudentID(String studentId, Statement statement, Connection connection) {

    }

    /**
     * This method inserts the email into the database
     * @param email -> student email
     * @param statement -> Statement initializer
     * @param connection -> Connection initializer
     */
    public static void insertEmail(String email, Statement statement, Connection connection) {

    }

    /**
     * This method inserts the password into the database
     * @param password -> password student chooses when creating account
     * @param statement -> Statement initializer
     * @param connection -> Connection initializer
     */
    public static void insertPassword(String password, Statement statement, Connection connection) {

    }


    public static void main(String[] args) {
        
    }
}
