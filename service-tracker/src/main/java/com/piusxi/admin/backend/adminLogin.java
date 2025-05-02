package com.piusxi.admin.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.piusxi.admin.database.adminDatabase;

public class adminLogin {

    public static adminResult authenticate(String email, String password) {
        adminResult result = new adminResult();
        result.setAuthenticated(false);

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            result.setErrorMessage("Email and password are required");
            return result;
        }

        Connection connection = null;
        try {
            connection = adminDatabase.connect();

            if (connection == null) {
                throw new SQLException("Failed to connect to database");
            }

            String loginQuery = "SELECT * FROM Administrators WHERE email = ? AND PASSWORD = BINARY ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(loginQuery)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        result.setAuthenticated(true);

                        result.setEmail(resultSet.getString(email));
                    }
                    else {
                        result.setErrorMessage("Invalid email or password");
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
    
    public static class adminResult {
        private boolean authenticated;
        private String errorMessage;
        private String email;

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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
