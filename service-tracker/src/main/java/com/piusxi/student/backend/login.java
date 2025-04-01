package com.piusxi.student.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.piusxi.student.database.studentInformationDatabase;

/*
 * IDK why the JOptionPane errors are happening
 */

public class login {

    public static void authenticateLogin(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and password are required", 
                "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection connection = null;
        try {
            connection = studentInformationDatabase.connect();
            if (connection == null) {
                throw new SQLException("Failed to connect to database");
            }

            boolean isEmail = username.contains("@");
            
            String loginQuery;
            if (isEmail) {
                loginQuery = "SELECT * FROM Students WHERE email = ? AND password = ?";
            }
            else {
                loginQuery = "SELECT * FROM Students WHERE student_id = ? AND password = ?";
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(loginQuery)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String studentId = resultSet.getString("student_id");
                        String firstName = resultSet.getString("first_name");

                        JOptionPane.showMessageDialog(this,
                            "Login Successful! Welcome back, " + firstName + ".",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                        
                        dispose();

                        SwingUtilities.invokeLater(() -> {
                            studentHomepage homepage = new studentHomepage();
                            // pass studentId to service submitted database in order to display theyre specific service submissions and what not
                            // homepage.setStudentId(studentId); -> need to add setStudentId to studentHomepage
                            homepage.setVisible(true);

                            /* testerHomepage tester = new testerHomepage();
                            tester.setVisible(true); */
                        });
                    }
                    else {
                        JOptionPane.showMessageDialog(this, "Invalid username or password",
                        "Login Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
        catch (SQLException se) {
            JOptionPane.showMessageDialog(this, "Database Error: " + se.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
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
    }        
}

