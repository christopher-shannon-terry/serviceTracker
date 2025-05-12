package com.piusxi.student.frontend;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.piusxi.student.backend.studentSession;
import com.piusxi.student.database.serviceSubmissionDatabase;

public class serviceReportingForm extends JFrame {
    
    private JFormattedTextField eventDateField;
    private JTextField hoursServedField;
    private JTextField locationField;
    private JTextArea descriptionArea;
    private JCheckBox familyCheckBox;
    private JCheckBox vulnerablePopCheckBox;
    private JCheckBox communityCheckBox;
    private JCheckBox environmentalCheckBox;
    private JTextField supervisorEmailField;
    private JButton submitButton;
    private JButton cancelButton;
    
    public serviceReportingForm() {
        setTitle("Service Reporting Form");
        setSize(800, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        createMenuBar();
        createForm();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // File menu
        JMenu fileMenu = new JMenu("Home");
        JMenuItem dashboard = new JMenuItem("Dashboard");
        fileMenu.add(dashboard);
        dashboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

                SwingUtilities.invokeLater(() -> {
                    studentHomepage homepage = new studentHomepage();
                    homepage.setVisible(true);
                });
            }
        });

        JMenuItem exit = new JMenuItem("Exit");
        fileMenu.add(exit);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                System.exit(0);
            }
        });
        
        // Service menu
        JMenu serviceMenu = new JMenu("Service");
        JMenuItem submissions = new JMenuItem("Submissions");
        serviceMenu.add(submissions);

        submissions.addActionListener((ActionEvent e) -> {
            SwingUtilities.invokeLater(() -> {
                new viewAllSubmissions().setVisible(true);
            });
        });
        
        // Help menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem instructions = new JMenuItem("Instructions");
        helpMenu.add(instructions);
        
        instructions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 

                SwingUtilities.invokeLater(() -> {
                    new instructionPage().setVisible(true);
                });
            }
        });

        JMenuItem resetPassword = new JMenuItem("Reset Password");
        helpMenu.add(resetPassword);

        resetPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

                forgotPasswordForm form = new forgotPasswordForm();
                form.setVisible(true);
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
        // Main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title and Checkboxes Panel - vertical layout
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        
        // Title
        JLabel titleLabel = new JLabel("Service Reporting Form");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(titleLabel);
        topPanel.add(titlePanel);
        
        // Add more vertical space after title
        topPanel.add(Box.createRigidArea(new Dimension(0, 60)));
        
        // Service Type checkboxes in a 2x2 grid
        JPanel serviceTypePanel = new JPanel(new GridLayout(2, 2, 10, 10));
        
        // Create checkboxes with larger font
        Font checkboxFont = new Font("Arial", Font.BOLD, 14);
        
        familyCheckBox = new JCheckBox("Family");
        familyCheckBox.setFont(checkboxFont);
        
        vulnerablePopCheckBox = new JCheckBox("Vulnerable Populations");
        vulnerablePopCheckBox.setFont(checkboxFont);
        
        communityCheckBox = new JCheckBox("Community");
        communityCheckBox.setFont(checkboxFont);
        
        environmentalCheckBox = new JCheckBox("Environmental");
        environmentalCheckBox.setFont(checkboxFont);
        
        serviceTypePanel.add(familyCheckBox);
        serviceTypePanel.add(vulnerablePopCheckBox);
        serviceTypePanel.add(communityCheckBox);
        serviceTypePanel.add(environmentalCheckBox);
        
        // Center the service type panel
        JPanel centeredServiceTypePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centeredServiceTypePanel.add(serviceTypePanel);
        topPanel.add(centeredServiceTypePanel);
        
        // Add adequate space after checkboxes
        topPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Add top panel to main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        // Form fields in a grid
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Description - Large text area
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Description of service event:"), gbc);
        
        descriptionArea = new JTextArea(8, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setPreferredSize(new Dimension(400, 150));
        
        gbc.gridx = 1;
        formPanel.add(scrollPane, gbc);
        
        // Length of service (Hours served)
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Length of service (hours):"), gbc);
        
        hoursServedField = new JTextField(10);
        gbc.gridx = 1;
        formPanel.add(hoursServedField, gbc);
        
        // Date of service
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Date of service (MM/DD/YYYY):"), gbc);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        eventDateField = new JFormattedTextField(dateFormat);
        eventDateField.setColumns(10);
        gbc.gridx = 1;
        formPanel.add(eventDateField, gbc);
        
        // Supervisor Email
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Supervisor Email:"), gbc);
        
        supervisorEmailField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(supervisorEmailField, gbc);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitServiceData();
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
        
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        
        // Add buttons to the bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(buttonPanel);
        
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }

    private void submitServiceData() {
        if (!validateForm()) {
            return;
        }
    
        String serviceType = getSelectedServiceType();
        if (serviceType == null) {
            JOptionPane.showMessageDialog(this,
                "Please select at least one service type.",
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        String studentId = studentSession.getInstance().getStudentId();
        String serviceDescription = descriptionArea.getText();
        String serviceEventLength = hoursServedField.getText();
        String supervisorEmail = supervisorEmailField.getText();
    
        Connection connection = null;
        try {
            connection = serviceSubmissionDatabase.connect();
            if (connection == null) {
                throw new SQLException("Failed to establish database connection");
            }
    
            serviceSubmissionDatabase.insertServiceData(
                studentId, 
                serviceType, 
                serviceEventLength, 
                serviceDescription, 
                supervisorEmail, 
                connection
            );
    
            JOptionPane.showMessageDialog(this,
                "Service event submitted successfully!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
            
            dispose();
            SwingUtilities.invokeLater(() -> {
                studentHomepage homepage = new studentHomepage();
                homepage.setVisible(true);
            });
        } 
        catch (SQLException se) {
            JOptionPane.showMessageDialog(this,
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

    private boolean validateForm() {
        if (descriptionArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please provide a description of the service event.",
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String hoursServed = hoursServedField.getText().trim();
        if (hoursServed.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter the number of hours served.",
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            double hours = Double.parseDouble(hoursServed);
            if (hours <= 0) {
                JOptionPane.showMessageDialog(this,
                    "Hours served must be greater than zero.",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Hours served must be a valid number.",
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String eventDate = eventDateField.getText().trim();
        if (eventDate.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter the date of the service event.",
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String supervisorEmail = supervisorEmailField.getText().trim();
        if (supervisorEmail.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter the supervisor's email address.",
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!isValidEmail(supervisorEmail)) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid email address for the supervisor.",
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }

    private String getSelectedServiceType() {
        StringBuilder serviceType = new StringBuilder();
        
        if (familyCheckBox.isSelected()) {
            serviceType.append("Family");
        }
        
        if (vulnerablePopCheckBox.isSelected()) {
            if (serviceType.length() > 0) {
                serviceType.append(", ");
            }
            serviceType.append("Vulnerable Populations");
        }
        
        if (communityCheckBox.isSelected()) {
            if (serviceType.length() > 0) {
                serviceType.append(", ");
            }
            serviceType.append("Community");
        }
        
        if (environmentalCheckBox.isSelected()) {
            if (serviceType.length() > 0) {
                serviceType.append(", ");
            }
            serviceType.append("Environmental");
        }
        
        return serviceType.length() > 0 ? serviceType.toString() : null;
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            serviceReportingForm form = new serviceReportingForm();
            form.setVisible(true);
        });
    }
}