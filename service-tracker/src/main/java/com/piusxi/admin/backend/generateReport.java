package com.piusxi.admin.backend;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.piusxi.student.database.serviceSubmissionDatabase;
import com.piusxi.student.database.studentInformationDatabase;

/**
 * Class that will generate and download an excel / google sheet to admin computer containing all service submissions at the moment of download
 */
public class generateReport {
    
    public static String generateFile(Connection connection) throws SQLException, IOException {
        Connection serviceConnection = connection;

        if (serviceConnection == null) {
            serviceConnection = serviceSubmissionDatabase.connect();
        }

        Connection studentConnection = studentInformationDatabase.connect();
        
        try {
            Map<String, String[]> studentInfo = new HashMap<>();
            
            String studentQuery = "SELECT student_id, first_name, last_name FROM student_info.Students";
            PreparedStatement studentPS = studentConnection.prepareStatement(studentQuery);
            ResultSet studentResults = studentPS.executeQuery();

            while (studentResults.next()) {
                String studentId = studentResults.getString("student_id");
                String firstName = studentResults.getString("first_name");
                String lastName = studentResults.getString("last_name");

                studentInfo.put(studentId, new String[] {firstName, lastName});
            }

            String serviceQuery = "SELECT student_id, service_type, service_event_length, service_description, supervisor_email, submission_date FROM service_info.service_submissions";
            PreparedStatement servicePS = serviceConnection.prepareStatement(serviceQuery);
            ResultSet serviceResults = servicePS.executeQuery();

            String userHome = System.getProperty("user.home");
            File reportDirectory = new File(userHome + File.separator + "Service Reports");
            
            if (!reportDirectory.exists()) {
                reportDirectory.mkdir();
            }

            String timestamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String file = "service_submissions-" + timestamp + ".csv";
            String filePath = reportDirectory.getAbsolutePath() + File.separator + file;

            try (FileWriter writer = new FileWriter(filePath)) {
                writer.append("First Name, Last Name, Student ID, Service Type, Service Length, Service Description, Supervisor Email, Submission Date\n");

                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");

                while (serviceResults.next()) {
                    String studentID = serviceResults.getString("student_id");
                    String[] student = studentInfo.getOrDefault(studentID, new String[] {"", ""});

                    writer.append(escapeCsv(student[0])).append(", "); // first_name
                    writer.append(escapeCsv(student[1])).append(", "); // last_name
                    writer.append(escapeCsv(studentID)).append(", "); // studentId

                    writer.append(escapeCsv(serviceResults.getString("service_type"))).append(", ");
                    writer.append(escapeCsv(serviceResults.getString("service_event_length"))).append(", ");
                    writer.append(escapeCsv(serviceResults.getString("service_description"))).append(", ");
                    writer.append(escapeCsv(serviceResults.getString("supervisor_email"))).append(", ");

                    Date submissionDate = serviceResults.getDate("submission_date");
                    String formattedDate = submissionDate != null ? dateFormat.format(submissionDate) : "";
                    writer.append(escapeCsv(formattedDate)).append("\n");
                }

                studentResults.close();
                studentPS.close();
                serviceResults.close();
                servicePS.close();

                return filePath;
            }
        } 
        finally {
            if (connection == null && serviceConnection != null) {
                serviceConnection.close();
            }

            if (studentConnection != null) {
                studentConnection.close();
            }
        }
    }

    private static String escapeCsv(String value) {
        if (value == null) {
            return "";
        }
        
        // If the value contains commas, quotes, or newlines, wrap it in quotes and escape any quotes
        if (value.contains("\"") || value.contains(",") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
