package com.piusxi;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;

import com.piusxi.student.backend.strongPasswordCheck;
import com.piusxi.student.database.studentInformationDatabase;
import com.piusxi.student.frontend.studentHomepage;

/**
 * This class implements the Forgot Password form UI based on the wireframe design.
 * It allows students to change their password by entering and confirming a new password.
 */
public class testForgotPasswordForm extends JFrame {
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private JButton changeButton;
    private JButton cancelButton;
    private String studentId; // To identify which student is changing their password
    
    public testForgotPasswordForm() {
        setTitle("Forgot Password - Pius XI Service Tracker");
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        
        createForm();
    }
    
    private void createForm() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Create the navigation bar at the top
        JPanel navBar = createNavBar();
        mainPanel.add(navBar, BorderLayout.NORTH);
        
        // Create the content panel with BoxLayout for vertical arrangement
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        // Add spacing at the top
        contentPanel.add(Box.createVerticalGlue());
        
        // Password fields
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.Y_AXIS));
        passwordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordPanel.setMaximumSize(new Dimension(300, 150));
        
        // New Password field
        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordPanel.add(newPasswordLabel);
        
        newPasswordField = new JPasswordField();
        newPasswordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordPanel.add(newPasswordField);
        passwordPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Confirm Password field
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordPanel.add(confirmPasswordLabel);
        
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordPanel.add(confirmPasswordField);
        
        contentPanel.add(passwordPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        changeButton = new JButton("Change");
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // changePassword();
            }
        });
        
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToHomepage();
            }
        });
        
        buttonPanel.add(changeButton);
        buttonPanel.add(cancelButton);
        
        contentPanel.add(buttonPanel);
        contentPanel.add(Box.createVerticalGlue());
        
        // Add content to main panel with centering
        JPanel centeringPanel = new JPanel(new GridBagLayout());
        centeringPanel.add(contentPanel);
        mainPanel.add(centeringPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JPanel createNavBar() {
        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JButton homeButton = new JButton("Home");
        homeButton.addActionListener(e -> returnToHomepage());
        
        JButton serviceButton = new JButton("Service");
        JButton helpButton = new JButton("Help");
        
        navBar.add(homeButton);
        navBar.add(serviceButton);
        navBar.add(helpButton);
        
        return navBar;
    }
    
    /* private void changePassword() {
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        
        // Validate passwords
        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showMessage("Both password fields are required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!newPassword.equals(confirmPassword)) {
            showMessage("Passwords do not match.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!strongPasswordCheck.isStrong(newPassword)) {
            String feedback = strongPasswordCheck.getPasswordFeedback(newPassword);
            showMessage("Password is not strong enough.\n" + feedback, "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Update password in database
        Connection connection = null;
        try {
            connection = studentInformationDatabase.connect();
            
            if (connection == null) {
                throw new SQLException("Failed to establish database connection");
            }
            
            // Call method to update password
            boolean success = updatePasswordInDatabase(newPassword, connection);
            
            if (success) {
                showSuccessMessage();
            } else {
                showFailedMessage();
            }
            
        } catch (SQLException se) {
            showMessage("Database error: " + se.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            showFailedMessage();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        }
    }
    
    private boolean updatePasswordInDatabase(String newPassword, Connection connection) {
        try {
            // This should call the backend method to update the password
            // For now, simulate success - in real implementation, call the actual method
            // studentInformationDatabase.updatePassword(newPassword, studentId, connection);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private void showSuccessMessage() {
        // Show success message in an option pane
        JOptionPane optionPane = new JOptionPane(
            "Password Updated\n(Write down somewhere)",
            JOptionPane.INFORMATION_MESSAGE
        );
        JDialog dialog = optionPane.createDialog(this, "Success");
        dialog.setVisible(true);
        
        // Close this form and return to homepage
        returnToHomepage();
    }
    
    private void showFailedMessage() {
        // Show failure message in an option pane
        JOptionPane optionPane = new JOptionPane(
            "Password Not Updated\n(Error with updating - try again later)",
            JOptionPane.ERROR_MESSAGE
        );
        JDialog dialog = optionPane.createDialog(this, "Error");
        dialog.setVisible(true);
    }
    
    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    } */
    
    private void returnToHomepage() {
        dispose();
        
        SwingUtilities.invokeLater(() -> {
            studentHomepage homepage = new studentHomepage();
            // homepage.setStudentId(studentId);
            homepage.setVisible(true);
        });
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // For testing purposes, pass a dummy student ID
            testForgotPasswordForm form = new testForgotPasswordForm();
            form.setVisible(true);
        });
    }
}