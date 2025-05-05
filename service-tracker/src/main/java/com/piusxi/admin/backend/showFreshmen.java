package com.piusxi.admin.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import com.piusxi.student.database.serviceSubmissionDatabase;
import com.piusxi.student.database.studentInformationDatabase;

public class showFreshmen {

    public static Connection studentConnection = null;
    public static Connection serviceConnection = null;

    public static Object[][] getFreshmenInfo(Connection connection) {
        PreparedStatement countPS = null;
        PreparedStatement infoPS = null;
        ResultSet countResults = null;
        ResultSet infoResults = null;

        try {
            studentConnection = studentInformationDatabase.connect();

            if (studentConnection == null) {
                return new Object[0][0];
            }
            String freshmenCountQuery = "SELECT COUNT(*) FROM Students WHERE grade_year = 9";
            countPS = studentConnection.prepareStatement(freshmenCountQuery);
            countResults = countPS.executeQuery();
            int rowCount = 0;

            if (countResults.next()) {
                rowCount = countResults.getInt(1);
            }

            String freshmenInfoQuery = "SELECT student_id, first_name, last_name FROM Students WHERE grade_year = 9";
            infoPS = studentConnection.prepareStatement(freshmenInfoQuery);
            infoResults = infoPS.executeQuery();

            Object[][] data = new Object[rowCount][3];
            int row = 0;

            while (infoResults.next()) {
                data[row][0] = infoResults.getInt("student_id");
                data[row][1] = infoResults.getString("first_name");
                data[row][2] = infoResults.getString("last_name");
                row++;
            }

            return data;
        }
        catch (SQLException se) {
            se.printStackTrace();

            return new Object[0][0];
        }
        finally {
            try {
                if (countResults != null) countResults.close();

                if (infoResults != null) infoResults.close();

                if (countPS != null) countPS.close();

                if (infoPS != null) infoPS.close();

                if (studentConnection != null) studentConnection.close();

                if (serviceConnection != null) serviceConnection.close();
            }
            catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static Object[][] getFreshmenService(Connection connection) {
        try {
            serviceConnection = serviceSubmissionDatabase.connect();

            if (serviceConnection == null) {
                return new Object[0][0];
            }

            String freshmenServiceQuery = "SELECT service_type, service_event_length, supervisor_email, submission_date FROM service_submissions"; // need to join the two databases so that it retrieves the submissions of each unique student
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return new Object[0][0];
    }

    public static void main(String[] args) {
        System.out.println("Attempting database connection...");
        Connection connection = studentInformationDatabase.connect();
        System.out.println("Connection successful, fetching data...");
    
        Object[][] data = getFreshmenInfo(connection);
        System.out.println("Data fetched, printing results...");
    
        System.out.println(Arrays.deepToString(data));
    }
}