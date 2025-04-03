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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

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
                System.out.println("Submit button clicked!");
                // Just print values to console for testing
                System.out.println("Hours: " + hoursServedField.getText());
                System.out.println("Date: " + eventDateField.getText());
                System.out.println("Location: " + locationField.getText());
                
                // Show confirmation dialog
                javax.swing.JOptionPane.showMessageDialog(
                    serviceReportingForm.this,
                    "Form submitted successfully! (This is a mockup only)",
                    "Success",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            serviceReportingForm form = new serviceReportingForm();
            form.setVisible(true);
        });
    }
}