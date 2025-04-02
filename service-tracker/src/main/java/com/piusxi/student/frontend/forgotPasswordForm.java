package com.piusxi.student.frontend;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import com.piusxi.student.backend.forgotPassword;
import com.piusxi.student.backend.studentSession;

public class forgotPasswordForm extends JFrame {

    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private JButton changeButton;
    private JButton cancelButton;
    private String studentId;

    public forgotPasswordForm() {
        setTitle("Student Service Tracker");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        if (studentSession.getInstance().isSessionActive()) {
            setTitle("Change Password - " + studentSession.getInstance().getFullName());
        }
        else {
            JOptionPane.showMessageDialog(this,
                "No active session. Please log in first.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        createMenuBar();
        createForm();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // File menu
        JMenu fileMenu = new JMenu("Home");
        JMenuItem backToDashboard = new JMenuItem("Dashboard");
        fileMenu.add(backToDashboard);
        
        backToDashboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                studentHomepage homepage = new studentHomepage();
                homepage.setVisible(true);
            }
        });

        // Service menu
        JMenu serviceMenu = new JMenu("Service");
        JMenuItem serviceForm = new JMenuItem("Submit Service");
        serviceMenu.add(serviceForm);

        serviceForm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serviceReportingForm serviceForm = new serviceReportingForm();
                // serviceForm.setVisible(true);
            }
        });
        
        // Help menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem instructions = new JMenuItem("Instructions");
        helpMenu.add(instructions);
        
        instructions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                instructionPage instructionPage = new instructionPage();
                instructionPage.setVisible(true);
                 */
            }
        });

        // Add menus to menu bar
        menuBar.add(fileMenu);
        menuBar.add(serviceMenu);
        menuBar.add(helpMenu);
        
        // Set the menu bar to the frame
        setJMenuBar(menuBar);
    }

    private void createForm() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
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
                if (!studentSession.getInstance().isSessionActive()) {
                    JOptionPane.showMessageDialog(forgotPasswordForm.this,
                        "No active session. Please log in first.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String studentId = studentSession.getInstance().getStudentId();
                String newPassword = new String(newPasswordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (!forgotPassword.validatePasswordChange(newPassword, confirmPassword)) {
                    String feedback = forgotPassword.getPasswordStrengthFeedback(newPassword);
                    JOptionPane.showMessageDialog(forgotPasswordForm.this,
                        "Password validation failed. " + 
                        (newPassword.equals(confirmPassword) ? feedback : "Passwords do not match."),
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!forgotPassword.isPasswordSame(studentId, newPassword)) {
                    JOptionPane.showMessageDialog(forgotPasswordForm.this,
                        "New password cannot be the same as your current password.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean success = forgotPassword.updateStudentPassword(studentId, newPassword);

                if (success) {
                    JOptionPane.showMessageDialog(forgotPasswordForm.this,
                        "Password updated successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    
                    dispose();

                    SwingUtilities.invokeLater(() -> {
                        studentHomepage homepage = new studentHomepage();
                        homepage.setVisible(true);
                    });
                }
                else {
                    JOptionPane.showMessageDialog(forgotPasswordForm.this,
                        "Failed to update password. Please try again later.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
    
                studentHomepage homepage = new studentHomepage();
                homepage.setVisible(true);
            }
        });
    
        // Add buttons to button panel
        buttonPanel.add(changeButton);
        buttonPanel.add(cancelButton);
        
        // Add button panel to content panel
        contentPanel.add(buttonPanel);
        contentPanel.add(Box.createVerticalGlue());
        
        // Add content panel to main panel
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Set main panel as the content pane
        setContentPane(mainPanel);
    }

    private void changePassword() {
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (studentId == null || studentId.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Error: Student ID not set. Please log in again.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!forgotPassword.validatePasswordChange(newPassword, confirmPassword)) {
            String feedback = forgotPassword.getPasswordStrengthFeedback(newPassword);
            JOptionPane.showMessageDialog(this,
                "Password validation failed. " + 
                (newPassword.equals(confirmPassword) ? feedback : "Passwords do not match."),
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = forgotPassword.updateStudentPassword(studentId, newPassword);

        if (success) {
            JOptionPane.showMessageDialog(this,
                "Password updated successfully!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
            
            dispose();
            SwingUtilities.invokeLater(() -> {
                studentHomepage homepage = new studentHomepage();
                homepage.setVisible(true);
            });
        }
        else {
            JOptionPane.showMessageDialog(this,
                "Failed to update password. Please try again later.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            forgotPasswordForm form = new forgotPasswordForm();
            form.setVisible(true);
        });
    }
}
