package com.piusxi.student.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.piusxi.student.database.studentInformationDatabase;

public class studentSession {
    
    private static studentSession instance = null;

    private String studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String gradeYear;
    private String gradYear;

    private studentSession() {
    }

    public static synchronized studentSession getInstance() {
        if (instance == null) {
            instance = new studentSession();
        }
        return instance;
    }

    public boolean startSession(String studentId) {
        this.studentId = studentId;
        return loadStudentInfo();
    }

    private boolean loadStudentInfo() {
        if (studentId == null || studentId.isEmpty()) {
            return false;
        }

        Connection connection = null;
        boolean success = false;

        try {
            connection = studentInformationDatabase.connect();
            if (connection == null) {
                throw new SQLException("Failed to connect to database");
            }
            
            String query = "SELECT * FROM Students WHERE student_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, studentId);
                
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        firstName = resultSet.getString("first_name");
                        lastName = resultSet.getString("last_name");
                        email = resultSet.getString("email");
                        gradeYear = resultSet.getString("grade_year");
                        gradYear = resultSet.getString("grad_year");
                        success = true;
                    }
                }
            }
        }
        catch (SQLException se) {
            System.err.println("Error loading student information: " + se.getMessage());
            se.printStackTrace();
        }
        finally {
            if (connection == null) {
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

    public void endSession() {
        studentId = null;
        firstName = null;
        lastName = null;
        email = null;
        gradeYear = null;
        gradYear = null;
    }

    public boolean isSessionActive() {
        return studentId != null && !studentId.isEmpty();
    }

    public String getStudentId() {
        return studentId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getGradeYear() {
        return gradeYear;
    }
    
    public String getGradYear() {
        return gradYear;
    }

    public String getFullName() {
        if (firstName != null && lastName != null) {
            return firstName + " " + lastName;
        }

        return null;
    }
}