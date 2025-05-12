package com.piusxi.student.frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
    // Pius XI school colors
    private static final Color PIUS_NAVY = new Color(0, 32, 91);
    private static final Color PIUS_GOLD = new Color(255, 215, 0);
    private static final Color PIUS_WHITE = Color.WHITE;
    private static final Color LIGHT_GRAY_BG = new Color(245, 245, 250);

    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private JButton changeButton;
    private JButton cancelButton;
    private String studentId;

    public forgotPasswordForm() {
        setTitle("Student Service Tracker");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(PIUS_WHITE);

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
        menuBar.setBackground(PIUS_NAVY);
        menuBar.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        
        // File menu
        JMenu fileMenu = new JMenu("Home");
        fileMenu.setForeground(PIUS_WHITE);
        fileMenu.setFont(new Font("Arial", Font.BOLD, 14));
        
        JMenuItem backToDashboard = new JMenuItem("Dashboard");
        backToDashboard.setBackground(PIUS_WHITE);
        backToDashboard.setForeground(PIUS_NAVY);
        backToDashboard.setFont(new Font("Arial", Font.PLAIN, 14));
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
        serviceMenu.setForeground(PIUS_WHITE);
        serviceMenu.setFont(new Font("Arial", Font.BOLD, 14));
        
        JMenuItem serviceForm = new JMenuItem("Submit Service");
        serviceForm.setBackground(PIUS_WHITE);
        serviceForm.setForeground(PIUS_NAVY);
        serviceForm.setFont(new Font("Arial", Font.PLAIN, 14));
        serviceMenu.add(serviceForm);

        serviceForm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serviceReportingForm serviceForm = new serviceReportingForm();
                serviceForm.setVisible(true);
            }
        });
        
        // Help menu
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setForeground(PIUS_WHITE);
        helpMenu.setFont(new Font("Arial", Font.BOLD, 14));
        
        JMenuItem instructions = new JMenuItem("Instructions");
        instructions.setBackground(PIUS_WHITE);
        instructions.setForeground(PIUS_NAVY);
        instructions.setFont(new Font("Arial", Font.PLAIN, 14));
        helpMenu.add(instructions);
        
        instructions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

                instructionPage instructionPage = new instructionPage();
                instructionPage.setVisible(true);
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
        mainPanel.setBackground(PIUS_WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(PIUS_WHITE);
        contentPanel.add(Box.createVerticalGlue());
    
        // Title at the top
        JLabel titleLabel = new JLabel("Change Password");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(PIUS_NAVY);
        contentPanel.add(titleLabel);
        
        // Add spacing after title
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
    
        // Password fields
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.Y_AXIS));
        passwordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordPanel.setMaximumSize(new Dimension(400, 200));
        passwordPanel.setBackground(PIUS_WHITE);
        passwordPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PIUS_GOLD, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // New Password field
        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        newPasswordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        newPasswordLabel.setForeground(PIUS_NAVY);
        passwordPanel.add(newPasswordLabel);
        
        newPasswordField = new JPasswordField();
        newPasswordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        newPasswordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PIUS_NAVY, 1),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        newPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordPanel.add(newPasswordField);
        passwordPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Confirm Password field
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        confirmPasswordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        confirmPasswordLabel.setForeground(PIUS_NAVY);
        passwordPanel.add(confirmPasswordLabel);
        
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        confirmPasswordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PIUS_NAVY, 1),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordPanel.add(confirmPasswordField);
        
        // Password requirements
        passwordPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        contentPanel.add(passwordPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
    
        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(PIUS_WHITE);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        changeButton = new JButton("Change Password");
        changeButton.setBackground(PIUS_GOLD);
        changeButton.setForeground(PIUS_NAVY);
        changeButton.setFont(new Font("Arial", Font.BOLD, 14));
        changeButton.setFocusPainted(false);
        changeButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
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
        cancelButton.setBackground(LIGHT_GRAY_BG);
        cancelButton.setForeground(PIUS_NAVY);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setFocusPainted(false);
        cancelButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            forgotPasswordForm form = new forgotPasswordForm();
            form.setVisible(true);
        });
    }
}