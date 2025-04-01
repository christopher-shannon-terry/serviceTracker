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

    /**
     * This method inserts all student data into the database
     * @param firstName
     * @param lastName
     * @param studentId
     * @param gradYear
     * @param email
     * @param password
     * @param connection -> Connection initializer
     */
    public static void insertStudentData(String firstName, String lastName, String studentId, String gradYear, String email, String password, Connection connection) {
        String insertStudentDataSQL = "INSERT INTO Students (first_name, last_name, student_id, email, password, grad_year) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertStudentDataSQL)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, studentId);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, password);
            preparedStatement.setString(6, gradYear);
            preparedStatement.executeUpdate();

            System.out.println("Student record inserted successfully");
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
        String updatePasswordSQL = "UPDATE Students SET password = ? WHERE student_id = ?";
    }

    /**
     * This method will update the students grade year (on August 1, 2025 maybe)
     * will probably use local date or whatever
     * @param gradeYear
     * @param connection
     */
    public static void updateGradeYear(String gradeYear, Connection connection) {
        String updateGradeYearSQL = "UPDATE Students SET grade_year = ? WHERE student_id = ?";
    }
}