package com.piusxi.admin.backend;

import com.piusxi.student.database.serviceSubmissionDatabase;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class serviceStatus {

    private static Connection studentConnection = null;
    private static Connection serviceConnection = null;
    
    public static void setApproved() {
        serviceConnection = serviceSubmissionDatabase.connect();

        if (serviceConnection == null) {
            return;
        }

        String approveQuery = "UPDATE service_submissions SET 'status' = approved WHERE student_id = ? AND stauts = 'pending'";
        try (PreparedStatement approvePS = serviceConnection.prepareStatement(approveQuery)) {
            
        }
        catch (SQLException se)  {
            se.printStackTrace();
        }
    }


    public static void setRejected() {
        serviceConnection = serviceSubmissionDatabase.connect();

        if (serviceConnection == null) {
            return;
        }

        String rejectQuery = "UPDATE 'service_submissions' SET 'status' = rejected WHERE student_id = ? AND status = 'pending'";
        try (PreparedStatement rejectPS = serviceConnection.prepareStatement(rejectQuery)) {

        }
        catch (SQLException se)  {
            se.printStackTrace();
        }
    }
}