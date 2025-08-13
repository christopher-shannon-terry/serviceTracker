package com.piusxi.student.frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.piusxi.student.backend.strongPasswordCheck;
import com.piusxi.student.database.studentInformationDatabase;

public class createAccountForm extends JFrame {
    // Pius XI school colors
    private static final Color PIUS_NAVY = new Color(0, 32, 91);
    private static final Color PIUS_GOLD = new Color(255, 215, 0);
    private static final Color PIUS_WHITE = Color.WHITE;
    private static final Color LIGHT_GRAY_BG = new Color(245, 245, 250);
    
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField studentIdField;
    private JComboBox<String> gradeYearField;
    private JTextField graduationYearField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton createButton;
    private JButton cancelButton;
    
    public createAccountForm() {
        setTitle("Create Account - Pius XI Service Tracker");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(PIUS_WHITE);

        form();
    }

    public void form() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(PIUS_WHITE);
        
        // Create the content panel with BoxLayout for vertical arrangement
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(PIUS_WHITE);
        
        // Add spacing at the top
        contentPanel.add(Box.createVerticalGlue());
        
        // Title and instructions
        JLabel titleLabel = new JLabel("Create New Account");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(PIUS_NAVY);
        contentPanel.add(titleLabel);
        
        JLabel instructionsLabel = new JLabel("Please fill out all fields to create your account");
        instructionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        instructionsLabel.setForeground(PIUS_NAVY);
        contentPanel.add(instructionsLabel);
        
        // Add spacing after the instructions
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Create form fields panel
        JPanel formPanel = new JPanel(new GridLayout(9, 1, 10, 10));
        formPanel.setMaximumSize(new Dimension(450, 450));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.setBackground(PIUS_WHITE);
        
        // Style for form fields
        Font fieldLabelFont = new Font("Arial", Font.BOLD, 14);
        
        // First Name
        JPanel firstNamePanel = new JPanel(new BorderLayout());
        firstNamePanel.setBackground(PIUS_WHITE);
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setFont(fieldLabelFont);
        firstNameLabel.setForeground(PIUS_NAVY);
        firstNamePanel.add(firstNameLabel, BorderLayout.NORTH);
        firstNameField = new JTextField();
        firstNameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PIUS_NAVY, 1),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        firstNamePanel.add(firstNameField, BorderLayout.CENTER);
        formPanel.add(firstNamePanel);
        
        // Last Name
        JPanel lastNamePanel = new JPanel(new BorderLayout());
        lastNamePanel.setBackground(PIUS_WHITE);
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setFont(fieldLabelFont);
        lastNameLabel.setForeground(PIUS_NAVY);
        lastNamePanel.add(lastNameLabel, BorderLayout.NORTH);
        lastNameField = new JTextField();
        lastNameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PIUS_NAVY, 1),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        lastNamePanel.add(lastNameField, BorderLayout.CENTER);
        formPanel.add(lastNamePanel);
        
        // Student ID
        JPanel studentIdPanel = new JPanel(new BorderLayout());
        studentIdPanel.setBackground(PIUS_WHITE);
        JLabel studentIdLabel = new JLabel("Student ID:");
        studentIdLabel.setFont(fieldLabelFont);
        studentIdLabel.setForeground(PIUS_NAVY);
        studentIdPanel.add(studentIdLabel, BorderLayout.NORTH);
        studentIdField = new JTextField();
        studentIdField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PIUS_NAVY, 1),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        studentIdPanel.add(studentIdField, BorderLayout.CENTER);
        formPanel.add(studentIdPanel);

        // Grade Year
        JPanel gradeYearPanel = new JPanel(new BorderLayout());
        gradeYearPanel.setBackground(PIUS_WHITE);
        JLabel gradeYearLabel = new JLabel("Grade Year:");
        gradeYearLabel.setFont(fieldLabelFont);
        gradeYearLabel.setForeground(PIUS_NAVY);
        gradeYearPanel.add(gradeYearLabel, BorderLayout.NORTH);
        String[] gradeOptions = {"9 - Freshman", "10 - Sophomore", "11 - Junior", "12 - Senior"};
        gradeYearField = new JComboBox<>(gradeOptions);
        gradeYearField.setFont(new Font("Arial", Font.PLAIN, 14));
        gradeYearField.setBackground(PIUS_WHITE);
        gradeYearField.setForeground(PIUS_NAVY);
        gradeYearPanel.add(gradeYearField, BorderLayout.CENTER);
        formPanel.add(gradeYearPanel);

        // Graduation Year
        JPanel graduationYearPanel = new JPanel(new BorderLayout());
        graduationYearPanel.setBackground(PIUS_WHITE);
        JLabel graduationYearLabel = new JLabel("Graduation Year:");
        graduationYearLabel.setFont(fieldLabelFont);
        graduationYearLabel.setForeground(PIUS_NAVY);
        graduationYearPanel.add(graduationYearLabel, BorderLayout.NORTH);
        graduationYearField = new JTextField();
        graduationYearField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PIUS_NAVY, 1),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        graduationYearPanel.add(graduationYearField, BorderLayout.CENTER);
        formPanel.add(graduationYearPanel);
        
        // Email
        JPanel emailPanel = new JPanel(new BorderLayout());
        emailPanel.setBackground(PIUS_WHITE);
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(fieldLabelFont);
        emailLabel.setForeground(PIUS_NAVY);
        emailPanel.add(emailLabel, BorderLayout.NORTH);
        emailField = new JTextField();
        emailField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PIUS_NAVY, 1),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        emailPanel.add(emailField, BorderLayout.CENTER);
        formPanel.add(emailPanel);
        
        // Password
        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBackground(PIUS_WHITE);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(fieldLabelFont);
        passwordLabel.setForeground(PIUS_NAVY);
        passwordPanel.add(passwordLabel, BorderLayout.NORTH);
        passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PIUS_NAVY, 1),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        formPanel.add(passwordPanel);
        
        // Confirm Password
        JPanel confirmPasswordPanel = new JPanel(new BorderLayout());
        confirmPasswordPanel.setBackground(PIUS_WHITE);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(fieldLabelFont);
        confirmPasswordLabel.setForeground(PIUS_NAVY);
        confirmPasswordPanel.add(confirmPasswordLabel, BorderLayout.NORTH);
        
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PIUS_NAVY, 1),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));

        confirmPasswordPanel.add(confirmPasswordField, BorderLayout.CENTER);
        formPanel.add(confirmPasswordPanel);

        contentPanel.add(formPanel);
        
        // Add spacing after the form
        contentPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(PIUS_WHITE);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        createButton = new JButton("Create Account");
        createButton.setBackground(PIUS_GOLD);
        createButton.setForeground(PIUS_NAVY);
        createButton.setFont(new Font("Arial", Font.BOLD, 14));
        createButton.setFocusPainted(false);
        createButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateAccount()) {
                    Connection connection = null;

                    try {
                        connection = studentInformationDatabase.connect();

                        if (connection == null) {
                            throw new SQLException("Failed to establish database connection");
                        }

                        String firstName = firstNameField.getText();
                        String lastName = lastNameField.getText();
                        String studentId = studentIdField.getText();
                        String gradeYear = ((String)gradeYearField.getSelectedItem()).substring(0, 2).trim();
                        String gradYear = graduationYearField.getText();
                        String email = emailField.getText();
                        String password = new String(passwordField.getPassword());

                        studentInformationDatabase.insertStudentData(firstName, lastName, studentId, gradYear, gradeYear, email, password, connection);

                        JOptionPane.showMessageDialog(createAccountForm.this, 
                            "Account created successfully!", 
                            "Success", JOptionPane.INFORMATION_MESSAGE);

                        returnToLogin();
                    }
                    catch (SQLException se) {
                        JOptionPane.showMessageDialog(createAccountForm.this, 
                            "Database error: " + se.getMessage(), 
                            "Error", JOptionPane.ERROR_MESSAGE);
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
                returnToLogin();
            }
        });

        buttonPanel.add(createButton);
        buttonPanel.add(cancelButton);
        
        contentPanel.add(buttonPanel);
        contentPanel.add(Box.createVerticalGlue());

        JPanel centeringPanel = new JPanel(new GridBagLayout());
        centeringPanel.setBackground(PIUS_WHITE);
        centeringPanel.add(contentPanel);

        mainPanel.add(centeringPanel, BorderLayout.CENTER);
        add(mainPanel);
    }


    /**
     * This method should return trues if all validation passes, false otherwise
     */
    private boolean validateAccount() {
        // Get form data
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String studentId = studentIdField.getText();
        String graduationYear = graduationYearField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        
        // Validate form fields
        if (firstName.isEmpty() || lastName.isEmpty() || studentId.isEmpty() || 
            graduationYear.isEmpty() || email.isEmpty() || password.isEmpty() || 
            confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validate student ID is numeric
        try {
            int id = Integer.parseInt(studentId);
        } 
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Student ID must be a number.", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            int year = Integer.parseInt(graduationYear);
            int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
            
            if (year < currentYear) {
                JOptionPane.showMessageDialog(this, "Please enter a valid graduation year.", 
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Graduation year must be a valid number.", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validate passwords match
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!email.toLowerCase().endsWith("@piusxi.org")) {
            JOptionPane.showMessageDialog(this, "Email must end with @piusxi.org",
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!strongPasswordCheck.isStrong(password)) {
            String feedback = strongPasswordCheck.getPasswordFeedback(password);
            JOptionPane.showMessageDialog(this, "Password is not strong enough\n" + feedback,
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } 

        return true;
    }
    
    private void returnToLogin() {
        dispose();
        
        SwingUtilities.invokeLater(() -> {
            studentLogin login = new studentLogin();
            login.setVisible(true);
        });
    }
}
