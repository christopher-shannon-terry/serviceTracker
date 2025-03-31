package com.piusxi.student.frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.piusxi.student.backend.strongPasswordCheck;

public class createAccountForm extends JFrame {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField studentIdField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton createButton;
    private JButton cancelButton;
    
    public createAccountForm() {
        setTitle("Create Account - Pius XI Service Tracker");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        form();
    }

    public void form() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Create the content panel with BoxLayout for vertical arrangement
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        // Add spacing at the top
        contentPanel.add(Box.createVerticalGlue());
        
        // Title and instructions
        JLabel titleLabel = new JLabel("Create New Account");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        contentPanel.add(titleLabel);
        
        JLabel instructionsLabel = new JLabel("Please fill out all fields to create your account");
        instructionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        contentPanel.add(instructionsLabel);
        
        // Add spacing after the instructions
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Create form fields panel
        JPanel formPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        formPanel.setMaximumSize(new Dimension(400, 300));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // First Name
        JPanel firstNamePanel = new JPanel(new BorderLayout());
        firstNamePanel.add(new JLabel("First Name:"), BorderLayout.NORTH);
        firstNameField = new JTextField();
        firstNamePanel.add(firstNameField, BorderLayout.CENTER);
        formPanel.add(firstNamePanel);
        
        // Last Name
        JPanel lastNamePanel = new JPanel(new BorderLayout());
        lastNamePanel.add(new JLabel("Last Name:"), BorderLayout.NORTH);
        lastNameField = new JTextField();
        lastNamePanel.add(lastNameField, BorderLayout.CENTER);
        formPanel.add(lastNamePanel);
        
        // Student ID
        JPanel studentIdPanel = new JPanel(new BorderLayout());
        studentIdPanel.add(new JLabel("Student ID:"), BorderLayout.NORTH);
        studentIdField = new JTextField();
        studentIdPanel.add(studentIdField, BorderLayout.CENTER);
        formPanel.add(studentIdPanel);
        
        // Email
        JPanel emailPanel = new JPanel(new BorderLayout());
        emailPanel.add(new JLabel("Email:"), BorderLayout.NORTH);
        emailField = new JTextField();
        emailPanel.add(emailField, BorderLayout.CENTER);
        formPanel.add(emailPanel);
        
        // Password
        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.add(new JLabel("Password:"), BorderLayout.NORTH);
        passwordField = new JPasswordField();
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        formPanel.add(passwordPanel);
        
        // Confirm Password
        JPanel confirmPasswordPanel = new JPanel(new BorderLayout());
        confirmPasswordPanel.add(new JLabel("Confirm Password:"), BorderLayout.NORTH);
        confirmPasswordField = new JPasswordField();
        confirmPasswordPanel.add(confirmPasswordField, BorderLayout.CENTER);
        formPanel.add(confirmPasswordPanel);
        
        contentPanel.add(formPanel);
        
        // Add spacing after the form
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        createButton = new JButton("Create Account");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount();
            }
        });
        
        cancelButton = new JButton("Cancel");
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
        centeringPanel.add(contentPanel);

        mainPanel.add(centeringPanel, BorderLayout.CENTER);
        add(mainPanel);
    }
    
    private void createAccount() {
        // Get form data
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String studentId = studentIdField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        
        // Validate form fields
        if (firstName.isEmpty() || lastName.isEmpty() || studentId.isEmpty() || 
            email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validate student ID is numeric
        try {
            int id = Integer.parseInt(studentId);
        } 
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Student ID must be a number.", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validate passwords match
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.toLowerCase().endsWith("@piusxi.org")) {
            JOptionPane.showMessageDialog(this, "Email must end with @piusxi.org",
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!strongPasswordCheck.isStrong(password)) {
            String feedback = strongPasswordCheck.getPasswordFeedback(password);
            JOptionPane.showMessageDialog(this, "Password is not strong enough\n" + feedback,
            "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
            
        /*
        * TODO: Send data to createAccount backend class
        * Will need to queue the database to insert these values
        * 
        */ 
    }
    
    private void returnToLogin() {
        dispose();
        
        SwingUtilities.invokeLater(() -> {
            studentLogin login = new studentLogin();
            login.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAccountForm form = new createAccountForm();
            form.setVisible(true);
        });
    }
}