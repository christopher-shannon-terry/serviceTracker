package com.piusxi.admin.backend;

import com.piusxi.student.database.serviceSubmissionDatabase;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class serviceStatus {

    private static Connection serviceConnection = null;
    
    public static boolean setApproved(int submissionId) {
        serviceConnection = serviceSubmissionDatabase.connect();

        if (serviceConnection == null) {
            return false;
        }

        String approveQuery = "UPDATE service_submissions SET status = 'approved' WHERE submission_id = ?";
        try (PreparedStatement approvePS = serviceConnection.prepareStatement(approveQuery)) {
            approvePS.setInt(1, submissionId);
            int rowsAffected = approvePS.executeUpdate();
            return rowsAffected > 0;
        }
        catch (SQLException se) {
            se.printStackTrace();
            return false;
        }
        finally {
            try {
                if (serviceConnection != null && !serviceConnection.isClosed()) {
                    serviceConnection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean setRejected(int submissionId) {
        serviceConnection = serviceSubmissionDatabase.connect();

        if (serviceConnection == null) {
            return false;
        }

        String rejectQuery = "UPDATE service_submissions SET status = 'rejected' WHERE submission_id = ?";
        try (PreparedStatement rejectPS = serviceConnection.prepareStatement(rejectQuery)) {
            rejectPS.setInt(1, submissionId);
            int rowsAffected = rejectPS.executeUpdate();
            return rowsAffected > 0;
        }
        catch (SQLException se) {
            se.printStackTrace();
            return false;
        }
        finally {
            try {
                if (serviceConnection != null && !serviceConnection.isClosed()) {
                    serviceConnection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}