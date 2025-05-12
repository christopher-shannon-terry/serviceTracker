package com.piusxi.student.frontend;

import java.awt.BorderLayout;
import java.awt.Color;
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
    // Pius XI school colors
    private static final Color PIUS_NAVY = new Color(0, 32, 91);
    private static final Color PIUS_GOLD = new Color(255, 215, 0);
    private static final Color PIUS_WHITE = Color.WHITE;
    private static final Color LIGHT_GRAY_BG = new Color(245, 245, 250);
    
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
        getContentPane().setBackground(PIUS_WHITE);

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
        
        JMenuItem dashboard = new JMenuItem("Dashboard");
        dashboard.setBackground(PIUS_WHITE);
        dashboard.setForeground(PIUS_NAVY);
        dashboard.setFont(new Font("Arial", Font.PLAIN, 14));
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
        exit.setBackground(PIUS_WHITE);
        exit.setForeground(PIUS_NAVY);
        exit.setFont(new Font("Arial", Font.PLAIN, 14));
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
        serviceMenu.setForeground(PIUS_WHITE);
        serviceMenu.setFont(new Font("Arial", Font.BOLD, 14));
        JMenuItem submissions = new JMenuItem("Submissions");
        serviceMenu.add(submissions);
        submissions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    dispose();

                    new viewAllSubmissions().setVisible(true);
                });
            }
        });
        
        // Help menu
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setForeground(PIUS_WHITE);
        helpMenu.setFont(new Font("Arial", Font.BOLD, 14));
        
        JMenuItem resetPassword = new JMenuItem("Reset Password");
        resetPassword.setBackground(PIUS_WHITE);
        resetPassword.setForeground(PIUS_NAVY);
        resetPassword.setFont(new Font("Arial", Font.PLAIN, 14));
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
        mainPanel.setBackground(PIUS_WHITE);
        
        // Title and Checkboxes Panel - vertical layout
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(PIUS_WHITE);
        
        // Title
        JLabel titleLabel = new JLabel("Service Reporting Form");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(PIUS_NAVY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(PIUS_WHITE);
        titlePanel.add(titleLabel);
        topPanel.add(titlePanel);
        
        // Add more vertical space after title
        topPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Service Type Label
        JLabel serviceTypeLabel = new JLabel("Select Service Type:");
        serviceTypeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        serviceTypeLabel.setForeground(PIUS_NAVY);
        serviceTypeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(serviceTypeLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Service Type checkboxes in a 2x2 grid
        JPanel serviceTypePanel = new JPanel(new GridLayout(2, 2, 10, 10));
        serviceTypePanel.setBackground(PIUS_WHITE);
        serviceTypePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PIUS_NAVY, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Create checkboxes with larger font
        Font checkboxFont = new Font("Arial", Font.BOLD, 14);
        
        familyCheckBox = new JCheckBox("Family");
        familyCheckBox.setFont(checkboxFont);
        familyCheckBox.setForeground(PIUS_NAVY);
        familyCheckBox.setBackground(PIUS_WHITE);
        
        vulnerablePopCheckBox = new JCheckBox("Vulnerable Populations");
        vulnerablePopCheckBox.setFont(checkboxFont);
        vulnerablePopCheckBox.setForeground(PIUS_NAVY);
        vulnerablePopCheckBox.setBackground(PIUS_WHITE);
        
        communityCheckBox = new JCheckBox("Community");
        communityCheckBox.setFont(checkboxFont);
        communityCheckBox.setForeground(PIUS_NAVY);
        communityCheckBox.setBackground(PIUS_WHITE);
        
        environmentalCheckBox = new JCheckBox("Environmental");
        environmentalCheckBox.setFont(checkboxFont);
        environmentalCheckBox.setForeground(PIUS_NAVY);
        environmentalCheckBox.setBackground(PIUS_WHITE);
        
        serviceTypePanel.add(familyCheckBox);
        serviceTypePanel.add(vulnerablePopCheckBox);
        serviceTypePanel.add(communityCheckBox);
        serviceTypePanel.add(environmentalCheckBox);
        
        // Center the service type panel
        JPanel centeredServiceTypePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centeredServiceTypePanel.setBackground(PIUS_WHITE);
        centeredServiceTypePanel.add(serviceTypePanel);
        topPanel.add(centeredServiceTypePanel);
        
        // Add adequate space after checkboxes
        topPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Add top panel to main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        // Form fields in a grid
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(PIUS_WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Form field styling
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        
        // Description - Large text area
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        JLabel descriptionLabel = new JLabel("Description of service event:");
        descriptionLabel.setFont(labelFont);
        descriptionLabel.setForeground(PIUS_NAVY);
        formPanel.add(descriptionLabel, gbc);
        
        descriptionArea = new JTextArea(8, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PIUS_NAVY, 1),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionArea.setForeground(PIUS_NAVY);
        
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setPreferredSize(new Dimension(400, 150));
        scrollPane.setBorder(BorderFactory.createLineBorder(PIUS_NAVY, 1));
        
        gbc.gridx = 1;
        formPanel.add(scrollPane, gbc);
        
        // Length of service (Hours served)
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel hoursLabel = new JLabel("Length of service (hours):");
        hoursLabel.setFont(labelFont);
        hoursLabel.setForeground(PIUS_NAVY);
        formPanel.add(hoursLabel, gbc);
        
        hoursServedField = new JTextField(10);
        hoursServedField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PIUS_NAVY, 1),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        hoursServedField.setFont(new Font("Arial", Font.PLAIN, 14));
        hoursServedField.setForeground(PIUS_NAVY);
        gbc.gridx = 1;
        formPanel.add(hoursServedField, gbc);
        
        // Date of service
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel dateLabel = new JLabel("Date of service (MM/DD/YYYY):");
        dateLabel.setFont(labelFont);
        dateLabel.setForeground(PIUS_NAVY);
        formPanel.add(dateLabel, gbc);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        eventDateField = new JFormattedTextField(dateFormat);
        eventDateField.setColumns(10);
        eventDateField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PIUS_NAVY, 1),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        eventDateField.setFont(new Font("Arial", Font.PLAIN, 14));
        eventDateField.setForeground(PIUS_NAVY);
        gbc.gridx = 1;
        formPanel.add(eventDateField, gbc);
        
        // Supervisor Email
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel emailLabel = new JLabel("Supervisor Email:");
        emailLabel.setFont(labelFont);
        emailLabel.setForeground(PIUS_NAVY);
        formPanel.add(emailLabel, gbc);
        
        supervisorEmailField = new JTextField(20);
        supervisorEmailField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PIUS_NAVY, 1),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        supervisorEmailField.setFont(new Font("Arial", Font.PLAIN, 14));
        supervisorEmailField.setForeground(PIUS_NAVY);
        gbc.gridx = 1;
        formPanel.add(supervisorEmailField, gbc);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(PIUS_WHITE);
        
        submitButton = new JButton("Submit");
        submitButton.setBackground(PIUS_GOLD);
        submitButton.setForeground(PIUS_NAVY);
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setFocusPainted(false);
        submitButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitServiceData();
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
        
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        
        // Add buttons to the bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(PIUS_WHITE);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 15)));
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