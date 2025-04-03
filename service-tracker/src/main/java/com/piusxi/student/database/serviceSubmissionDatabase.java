package com.piusxi.student.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This is class for the service submitted database
 */
public class serviceSubmissionDatabase {

    public final Connection connection = null;

    public static final String DB_URL = "jdbc:mariadb://localhost:3306/service_info";
    public static final String USER = "alanmitchell";
    public static final String PASSWORD = "922925";

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
     * This method inserts the service data from the form into the service_info.service_submissions database
     * @param studentId
     * @param serviceType
     * @param serviceEventLength
     * @param serviceDescription
     * @param supervisorEmail
     * @param status
     * @param submissionDate
     * @param connection
     */
    public static void insertServiceData(String studentId, String serviceType, String serviceEventLength, String serviceDescription, String supervisorEmail, String status, String submissionDate, Connection connection) {
        String insertServiceDataSQL = "INSERT INTO service_submissions (student_id, service_type, service_event_length, service_description, supervisor_email, status, submission_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertServiceDataSQL)) {
            preparedStatement.setString(1, studentId);
            preparedStatement.setString(2, serviceType);
            preparedStatement.setString(3, serviceEventLength);
            preparedStatement.setString(4, serviceDescription);
            preparedStatement.setString(5, supervisorEmail);
            preparedStatement.setString(6, status);
            preparedStatement.setString(7, submissionDate);

            System.out.println("Service event data inserted successfully");
        }
        catch (SQLException se) {
            System.out.println("Error: " + se.getMessage());
        }
    }
}
