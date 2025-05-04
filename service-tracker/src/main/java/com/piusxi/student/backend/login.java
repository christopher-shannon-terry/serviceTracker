package com.piusxi.student.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.piusxi.student.database.studentInformationDatabase;

public class login {

    /**
     * Authenticates a student using either their student ID or email, and password
     * 
     * @param username The student ID or email
     * @param password The password for the account
     * @return A LoginResult object containing authentication status and student info
     */
    public static loginResult authenticate(String username, String password) {
        loginResult result = new loginResult();
        result.setAuthenticated(false);
    
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            result.setErrorMessage("Username and password are required");
            return result;
        }
    
        Connection connection = null;
        try {
            connection = studentInformationDatabase.connect();
    
            if (connection == null) { 
                throw new SQLException("Failed to connect to database");
            }
    
            boolean isEmail = username.contains("@");
            boolean isStudentID = username.matches("\\d+");
    
            String loginQuery = "";
            if (isEmail) {
                loginQuery = "SELECT * FROM Students WHERE email = ? AND password = BINARY ?";
            } 
            else if (isStudentID) {
                loginQuery = "SELECT * FROM Students WHERE student_id = ? AND password = BINARY ?";
            }
            else {
                result.setErrorMessage("Invalid username format");
                return result;
            }
    
            try (PreparedStatement preparedStatement = connection.prepareStatement(loginQuery)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
    
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        result.setAuthenticated(true);
    
                        result.setStudentId(resultSet.getString("student_id"));
                        result.setFirstName(resultSet.getString("first_name"));
                        result.setLastName(resultSet.getString("last_name"));
                        result.setEmail(resultSet.getString("email"));
                        result.setGradeYear(resultSet.getString("grade_year"));
                        result.setGradYear(resultSet.getString("grad_year"));
                    }
                    else {
                        result.setErrorMessage("Invalid username or password");
                    }
                }
            }
        }
        catch (SQLException se) {
            result.setErrorMessage("Database Error: " + se.getMessage());
            se.printStackTrace();
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
    
        return result;
    }

    /**
     * Inner class to hold the authentication result and student information
     */
    public static class loginResult {
        private boolean authenticated;
        private String errorMessage;
        private String studentId;
        private String firstName;
        private String lastName;
        private String email;
        private String gradeYear;
        private String gradYear;
        private boolean isAdminAttempt = false;

        public boolean isAdminAttempt() {
            return isAdminAttempt;
        }

        public void setIsAdminAttempt(boolean isAdminAttempt) {
            this.isAdminAttempt = isAdminAttempt;
        }

        public boolean isAuthenticated() {
            return authenticated;
        }

        public void setAuthenticated(boolean authenticated) {
            this.authenticated = authenticated;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGradeYear() {
            return gradeYear;
        }

        public void setGradeYear(String gradeYear) {
            this.gradeYear = gradeYear;
        }

        public String getGradYear() {
            return gradYear;
        }

        public void setGradYear(String gradYear) {
            this.gradYear = gradYear;
        }
    }
}