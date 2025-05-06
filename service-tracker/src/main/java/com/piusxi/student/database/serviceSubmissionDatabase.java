package com.piusxi.student.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is class for the service submitted database
 */
public class serviceSubmissionDatabase {

    public final Connection connection = null;

    public static final String DB_URL = "jdbc:mariadb://localhost:3306/service_info";
    public static final String USER = "general";
    public static final String PASSWORD = "123456";

    /* public static final String DB_URL = "jdbc:mariadb://localhost:3306/service_info";
    public static final String USER = "alanmitchell";
    public static final String PASSWORD = "922925"; */

    /* public static final String DB_URL = "jdbc:mariadb://localhost:3306/service_info";
    public static final String USER = "joshuachristian";
    public static final String PASSWORD = "123456"; */

    /* public static final String DB_URL = "jdbc:mariadb://localhost:3306/service_info";
    public static final String USER = "ethancobb";
    public static final String PASSWORD = "123456"; */

    /* public static final String DB_URL = "jdbc:mariadb://localhost:3306/service_info";
    public static final String USER = "shannonterry";
    public static final String PASSWORD = "123456"; */

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
     * This method gets the service statistics for users from the service_submissions table in the service_info database
     * @param studentId
     * @return stats 
     */
    public static Map<String, Object> getServiceStats(String studentId) {
        Map<String, Object> stats = new HashMap<>();
    
        stats.put("totalHours", 0.0);
        stats.put("completedServices", 0);
        stats.put("pendingServices", 0);
    
        Connection connection = null;
    
        try {
            connection = connect();
            if (connection == null) {
                throw new SQLException("Failed to establish database connection");
            }
    
            // Get total approved hours
            String totalHoursQuery = "SELECT SUM(service_event_length) AS total_hours " +
                                    "FROM service_submissions " +
                                    "WHERE student_id = ? AND status = 'approved'";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(totalHoursQuery)) {
                preparedStatement.setString(1, studentId);
    
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        double totalHours = resultSet.getDouble("total_hours");
    
                        if (!resultSet.wasNull()) {
                            stats.put("totalHours", totalHours);
                        }
                    }
                }
            }
    
            // Get count of completed (approved) services
            String completedQuery = "SELECT COUNT(*) AS approved_count " +
                                    "FROM service_submissions " +
                                    "WHERE student_id = ? AND status = 'approved'";
    
            try (PreparedStatement preparedStatement = connection.prepareStatement(completedQuery)) {
                preparedStatement.setString(1, studentId);
    
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        stats.put("completedServices", resultSet.getInt("approved_count"));
                    }
                }
            }
    
            // Get count of pending services
            String pendingQuery = "SELECT COUNT(*) AS pending_count " +
                                "FROM service_submissions " +
                                "WHERE student_id = ? AND status = 'pending'";
    
            try (PreparedStatement preparedStatement = connection.prepareStatement(pendingQuery)) {
                preparedStatement.setString(1, studentId);
    
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        stats.put("pendingServices", resultSet.getInt("pending_count"));
                    }
                }
            }
        } 
        catch (SQLException se) {
            System.err.println("Error loading service statistics: " + se.getMessage());
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
    
        return stats;
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
    public static void insertServiceData(String studentId, String serviceType, String serviceEventLength, 
                                     String serviceDescription, String supervisorEmail, Connection connection) {
    String insertServiceDataSQL = "INSERT INTO service_submissions (student_id, service_type, service_event_length, " +
                                 "service_description, supervisor_email) VALUES (?, ?, ?, ?, ?)";

    try (PreparedStatement preparedStatement = connection.prepareStatement(insertServiceDataSQL)) {
        preparedStatement.setString(1, studentId);
        preparedStatement.setString(2, serviceType);
        preparedStatement.setString(3, serviceEventLength);
        preparedStatement.setString(4, serviceDescription);
        preparedStatement.setString(5, supervisorEmail);
        
        preparedStatement.executeUpdate();
        System.out.println("Service event data inserted successfully");
    }
    catch (SQLException se) {
        System.out.println("Error: " + se.getMessage());
        se.printStackTrace();
    }
}

    public static List<Map<String, String>> getStudentServiceSubmissions(String studentId, Connection connection) {
        List<Map<String, String>> submissions = new ArrayList<>();

        String submissionQuery = "SELECT * FROM service_submissions WHERE student_id = ? ORDER BY submission_date DESC";

        try (PreparedStatement preparedStatement = connection.prepareStatement(submissionQuery)) {
            preparedStatement.setString(1, studentId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Map<String, String> submissionData = new HashMap<>();

                    submissionData.put("id", resultSet.getString("submission_id"));
                    submissionData.put("service_type", resultSet.getString("service_type"));
                    submissionData.put("service_event_length", resultSet.getString("service_event_length"));
                    submissionData.put("service_description", resultSet.getString("service_description"));
                    submissionData.put("supervisor_email", resultSet.getString("supervisor_email"));
                    submissionData.put("status", resultSet.getString("status"));
                    submissionData.put("submission_date", resultSet.getString("submission_date"));

                    submissions.add(submissionData);
                }
            }
        }
        catch (SQLException se) {
            System.out.println("Error retrieving service submissions: " + se.getMessage());
            se.printStackTrace();
        }

        return submissions;
    }

    // Wipe all student submissions sometime in June maybe
    public static void wipeSubmissions(Connection connection) throws SQLException {
        LocalDate date = LocalDate.now();

        if (date.getMonthValue() == 6 && date.getDayOfMonth() == 30) {
            String deleteSubmissionsSQL = "DELETE * FROM service_submissions";

            try (PreparedStatement ps = connection.prepareStatement(deleteSubmissionsSQL)) {
                int deleted = ps.executeUpdate(); // Delete all entries

                // System.out.printf("Deleted %d submissions from database\n", deleted);
            }
        }
        else {
            System.out.println("Today is not June 30th");
        }
    }
}
