package com.piusxi.student.backend;

import java.sql.Connection;
import java.sql.SQLException;

import com.piusxi.student.database.studentInformationDatabase;

/**
 * This class will handle password reset functionality
 */
public class forgotPassword {
    
    /**
     * Updates a student's password in the database
     * @param studentId -> the Student ID to update
     * @param newPassword -> the new password for the account
     * @return true -> if the password was updated successfully, false otherwise 
     */
    public static boolean updateStudentPassword(String studentId, String newPassword) {
        if (studentId == null || studentId.isEmpty() || newPassword == null || newPassword.isEmpty()) {
            return false;
        }

        if (!strongPasswordCheck.isStrong(newPassword)) {
            return false;
        }

        Connection connection = null;
        boolean success = false;

        try {
            connection = studentInformationDatabase.connect();
            if (connection == null) {
                throw new SQLException("Failed to establish database connection");
            }
            
            studentInformationDatabase.updatePassword(studentId, newPassword, connection);
            success = true;
        }
        catch (SQLException se) {
            System.out.println("Database error: " + se.getMessage());
            se.printStackTrace();
            success = false;
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                } 
                catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        }
        
        return success;
    }

    public static boolean validatePasswordChange(String password, String confirmPassword) {
        if (password == null || password.isEmpty() || confirmPassword == null || confirmPassword.isEmpty()) {
            return false;
        }

        if (!password.equals(confirmPassword)) {
            return false;
        }
        
        return strongPasswordCheck.isStrong(password);
    }

    /**
     * Feedback method, just shows reasons why password wasnt strong enough
     * @param password
     * @return feedback -> why password didnt pass 
     */
    public static String getPasswordStrengthFeedback(String password) {
        return strongPasswordCheck.getPasswordFeedback(password);
    }

    
    public static boolean isPasswordSame(String studentId, String newPassword) {
        Connection connection = null;
        boolean isSame = false;

        try {
            connection = studentInformationDatabase.connect();
            if (connection == null) {
                throw new SQLException("Failed to establish database connection");
            }
            
            isSame = studentInformationDatabase.isPasswordDifferent(studentId, newPassword, connection);
        }
        catch (SQLException se) {
            System.out.println("Database error: " + se.getMessage());
            se.printStackTrace();
            isSame = true;
        } 
        finally {
            if (connection != null) {
                try {
                    connection.close();
                } 
                catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        }
        
        return isSame;
    }
}