package com.piusxi.student.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * This class is responsible for connecting to the student_info database and inserting values
 * @implNote -> Will delete print statement verification later
 */
public class studentInformationDatabase {

    public final Connection connection = null;

    public static final String DB_URL = "jdbc:mariadb://localhost:3306/student_info";
    public static final String USER = "general";
    public static final String PASSWORD = "123456";

    /* public static final String DB_URL = "jdbc:mariadb://localhost:3306/student_info";
    public static final String USER = "alanmitchell";
    public static final String PASSWORD = "922925"; */

    /* public static final String DB_URL = "jdbc:mariadb://localhost:3306/student_info";
    public static final String USER = "joshuachristian";
    public static final String PASSWORD = "123456"; */

    /* public static final String DB_URL = "jdbc:mariadb://localhost:3306/student_info";
    public static final String USER = "ethancobb";
    public static final String PASSWORD = "123456"; */

    /* public static final String DB_URL = "jdbc:mariadb://localhost:3306/student_info";
    public static final String USER = "shannonterry";
    public static final String PASSWORD = "123456"; */

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
     * @param gradYear -> graduation year of student
     * @param gradeYear -> grade year of student (9 - Freshman, 10 - Sophomore, 11 - Junior, 12 - Senior)
     * @param email
     * @param password
     * @param connection -> Connection initializer
     */
    public static void insertStudentData(String firstName, String lastName, String studentId, String gradYear, String gradeYear, String email, String password, Connection connection) {
        String insertStudentDataSQL = "INSERT INTO Students (first_name, last_name, student_id, email, password, grad_year, grade_year) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertStudentDataSQL)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, studentId);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, password);
            preparedStatement.setString(6, gradYear);
            preparedStatement.setString(7, gradeYear);
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
    public static boolean updatePassword(String studentId, String password, Connection connection) {
        String updatePasswordSQL = "UPDATE Students SET password = ? WHERE student_id = ?";
        boolean success = false;

        try (PreparedStatement preparedStatement = connection.prepareStatement(updatePasswordSQL)) {
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, studentId);
            
            int rowsUpdated = preparedStatement.executeUpdate();
            System.out.println(rowsUpdated + " rows updated");

            success = (rowsUpdated > 0);
        }
        catch (SQLException se) {
            System.out.println("Error updating password: " + se.getMessage());
            se.printStackTrace();
        }

        return success;
    }

    
    public static boolean isPasswordDifferent(String studentId, String newPassword, Connection connection) {
        String currentPasswordQuery = "SELECT password FROM Students WHERE student_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(currentPasswordQuery)) {
        preparedStatement.setString(1, studentId);
        
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String currentPassword = resultSet.getString("password");
                    
                    // Return true if the new password is different from the current one
                    return (!newPassword.equals(currentPassword));
                }
                // If no record is found with the given student ID
                return true;
            }
        }
        catch (SQLException se) {
            System.out.println("Error checking current password: " + se.getMessage());
            se.printStackTrace();
            // In case of database error, default to allowing the password change
            return true;
        }
    }

    /**
     * This method will update the students grade year (on August 1 of every year or something)
     * will probably use local date or whatever
     * @param gradeYear
     * @param connection
     */
    public static void updateGradeYear(String gradeYear, Connection connection) throws SQLException {
        LocalDate date = LocalDate.now(); // Get current date

        // If August 1st, increment all student grade level
        if (date.getMonthValue() == 8 && date.getDayOfMonth() == 1) {
            String updateGradeYearSQL = "UPDATE Students SET grade_year = " + 
                                        "CASE " + 
                                            "WHEN grade_year = '9' THEN '10'" +
                                            "WHEN grade_year = '10' THEN '11'" +
                                            "WHEN grade_year = '11' THEN '12'" +
                                            "END";
            
            try (PreparedStatement ps = connection.prepareStatement(updateGradeYearSQL)) {
                int updatedGrade = ps.executeUpdate();
    
                // System.out.printf("Updated grade year for %d students\n", updatedGrade);
            }
        }
        else {
            System.out.println("Today is not August 1st");
        }
    }

    /**
     * This will method will auto wipe seniors from the account database after every year
     * Will have to be wiped before the updateGradeYear happens so that the juniors form the previous year dont have to recreate an account
     * So probably will delete around June 1st
     */
    public static void wipeSeniors(Connection connection) throws SQLException {
        LocalDate date = LocalDate.now();

        if (date.getMonthValue() == 7 && date.getDayOfMonth() == 1) {
            String deleteSeniorsSQL = "DELETE FROM Students WHERE grade_year = '12'";

            try (PreparedStatement ps = connection.prepareStatement(deleteSeniorsSQL)) {
                int deleted = ps.executeUpdate();

                // System.out.printf("Deleted %d seniors from database\n", deleted);
            }
        }
        else {
            System.out.println("Today is not July 1st");
        }
    }
}