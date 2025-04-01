package com.piusxi.student.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class is responsible for connecting to the student_info database and inserting values
 * @implNote -> Will delete print statement verification later
 */
public class studentInformationDatabase {

    public final Connection connection = null;

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
     * @param connection -> Connection initializer
     */
    public static void insertFirstName(String firstName, Connection connection) {
        String insertFirstNameSQL = "INSERT INTO Students (first_name) VALUES (?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertFirstNameSQL)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.executeUpdate();

            System.out.println("First name inserted successfully");
        }
        catch (SQLException se) {
            System.out.println("Error: " + se.getMessage());
        }
        
    }

    /**
     * This method inserts the last name into the database
     * @param lastName -> last name of student
     * @param connection -> Connection initializer
     */
    public static void insertLastName(String lastName, Connection connection) {
        String insertLastNameSQL = "INSERT INTO Students (last_name) VALUES (?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertLastNameSQL)) {
            preparedStatement.setString(1, lastName);
            preparedStatement.executeUpdate();

            System.out.println("Last name inserted successfully");
        } 
        catch (SQLException se) {
            System.out.println("Error: " + se.getMessage());
        }
    }

    /**
     * This method inserts the student ID into the database
     * @param studentId -> unique student ID of student
     * @param connection -> Connection initializer
     */
    public static void insertStudentID(String studentId, Connection connection) {
        String insertStudentIdSQL = "INSERT INTO Students (student_id) VALUES (?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertStudentIdSQL)) {
            preparedStatement.setString(1, studentId);
            preparedStatement.executeUpdate();

            System.out.println("Student ID inserted successfully");
        }
        catch (SQLException se) {
            System.out.println("Error: " + se.getMessage());
        }
    }

    /**
     * This method inserts the email into the database
     * @param email -> student email
     * @param connection -> Connection initializer
     */
    public static void insertEmail(String email, Connection connection) {
        String insertEmailSQL = "INSERT INTO Students (email) VALUES (?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertEmailSQL)) {
            preparedStatement.setString(1, email);
            preparedStatement.executeUpdate();

            System.out.println("Email inserted successfully");
        }
        catch (SQLException se) {
            System.out.println("Error: " + se.getMessage());
        }
    }

    /**
     * This method inserts the password into the database
     * @param password -> password student chooses when creating account
     * @param connection -> Connection initializer
     */
    public static void insertPassword(String password, Connection connection) {
        String insertPasswordSQL = "INSERT INTO Students (password) VALUES (?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertPasswordSQL)) {
            preparedStatement.setString(1, password);
            preparedStatement.executeUpdate();

            System.out.println("Password inserted successfully");
        }
        catch (SQLException se) {
            System.out.println("Error: " + se.getMessage());
        }
    }

    /**
     * This method updates the password when the student decides to change their password
     * @param password -> password student chooses when creating account
     * @param connection -> Connection initializer
     */
    public static void updatePassword(String password, Connection connection) {
        String updatePasswordSQL = "UPDATE Student SET password = ? WHERE student_id = ?";
    }
}
