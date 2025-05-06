package com.piusxi.admin.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.piusxi.student.database.serviceSubmissionDatabase;
import com.piusxi.student.database.studentInformationDatabase;

public class showJuniors {

    public static Connection studentConnection = null;
    public static Connection serviceConnection = null;

    public static Object[][] getJuniorInfo(Connection connection) {
        PreparedStatement countPS = null;
        PreparedStatement infoPS = null;
        ResultSet countResults = null;
        ResultSet infoResults = null;

        try {
            studentConnection = studentInformationDatabase.connect();

            if (studentConnection == null) {
                return new Object[0][0];
            }
            String juniorCountQuery = "SELECT COUNT(*) FROM Students WHERE grade_year = 11";
            countPS = studentConnection.prepareStatement(juniorCountQuery);
            countResults = countPS.executeQuery();
            int rowCount = 0;

            if (countResults.next()) {
                rowCount = countResults.getInt(1);
            }

            String juniorInfoQuery = "SELECT student_id, first_name, last_name FROM Students WHERE grade_year = 11";
            infoPS = studentConnection.prepareStatement(juniorInfoQuery);
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

    public static Object[][] getJuniorService(Connection connection) {
        PreparedStatement servicePS = null;
        ResultSet serviceResults = null;
        ArrayList<Object[]> serviceData = new ArrayList<>();

        try {
            Object[][] juniorInfo = getJuniorInfo(connection);

            if (juniorInfo.length == 0) {
                return new Object[0][0];
            }

            // Hash map for quick and easy look up
            Map<Integer, String[]> juniorMap = new HashMap<>();
            StringBuilder idList = new StringBuilder();
            boolean first = true;

            for (Object[] student : juniorInfo) {
                int studentID = (Integer) student[0];
                String firstName = (String) student[1];
                String lastName = (String) student[2];

                juniorMap.put(studentID, new String[] {firstName, lastName});

                if (!first) {
                    idList.append(",");
                }
                else {
                    first = false;
                }
                idList.append(studentID);
            }

            serviceConnection = serviceSubmissionDatabase.connect();

            if (serviceConnection == null) {
                return new Object[0][0];
            }

            String serviceQuery = "SELECT student_id, service_type, service_event_length, " +
            "supervisor_email, submission_date FROM service_submissions " +
            "WHERE student_id IN (" + idList.toString() + ")";

            servicePS = serviceConnection.prepareStatement(serviceQuery);
            serviceResults = servicePS.executeQuery();

            while (serviceResults.next()) {
                int studentID = serviceResults.getInt("student_id");

                if (juniorMap.containsKey(studentID)) {
                    String[] studentInfo = juniorMap.get(studentID);

                    Object[] row = new Object[7];
                    row[0] = studentID;
                    row[1] = studentInfo[0]; // first_name
                    row[2] = studentInfo[1]; // last_name
                    row[3] = serviceResults.getString("service_type");
                    row[4] = serviceResults.getDouble("service_event_length");
                    row[5] = serviceResults.getString("supervisor_email");
                    row[6] = serviceResults.getTimestamp("submission_date");

                    serviceData.add(row);
                }
            }

            Object[][] resultData = new Object[serviceData.size()][7];
            for (int i = 0; i < serviceData.size(); i++) {
                resultData[i] = serviceData.get(i);
            }

            return resultData;
        }
        catch (SQLException se) {
            se.printStackTrace();
            return new Object[0][0];
        }
        finally {
            try {
                if (serviceResults != null) serviceResults.close();

                if (servicePS != null) servicePS.close();

                if (serviceConnection != null) serviceConnection.close();
            }
            catch (SQLException se) {
                se.printStackTrace();
            }   
        }
    }

    public static void main(String[] args) {
        System.out.println("Attempting database connection...");
        Connection connection = studentInformationDatabase.connect();
        System.out.println("Connection successful, fetching data...");
    
        Object[][] data = getJuniorInfo(connection);
        System.out.println("Data fetched, printing results...");
    
        System.out.println(Arrays.deepToString(data));

        System.out.println("Fetching service information for juniors...");
        Object[][] serviceData = getJuniorService(connection);
        System.out.println("Service data fetched, printing results...");
        
        System.out.println(Arrays.deepToString(serviceData));
    }
}
