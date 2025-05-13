package com.piusxi.student.frontend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class instructionPage extends JFrame {
    // Pius XI school colors
    private static final Color PIUS_NAVY = new Color(0, 32, 91);
    private static final Color PIUS_GOLD = new Color(255, 215, 0);
    private static final Color PIUS_WHITE = Color.WHITE;
    private static final Color LIGHT_GRAY_BG = new Color(245, 245, 250);

    public instructionPage() {
        setSize(800, 600);
        setTitle("Service Tracker - Instructions");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(PIUS_WHITE);

        createMenuBar();
        instructions();
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

    public void instructions() {
        // Main panel with appropriate layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(PIUS_WHITE);
        
        // Title for the instructions page
        JLabel titleLabel = new JLabel("Service Tracker Instructions");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(PIUS_NAVY);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Create a panel for the instruction content with a scroll pane
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.setBackground(PIUS_WHITE);
        
        // Add instruction sections
        addSection(contentPanel, "Overview", 
            "The Pius XI Service Tracker helps you track and manage your service hours. " +
            "All students are required to complete a minimum number of service experiences each year " +
            "one across each service category.");
        
        addSection(contentPanel, "Submitting Service Hours", 
            "1. Click on 'Submit Service' in the Service menu or on the dashboard.\n" +
            "2. Select the appropriate service type category (Family, Vulnerable Populations, Community, or Environmental).\n" +
            "3. Provide a description of your service activity and the impact it had with at least 3 sentences.\n" +
            "4. Enter the number of hours you served.\n" +
            "5. Enter the date when you performed the service.\n" +
            "6. Provide your supervisor's email for verification.\n" +
            "7. Click Submit to record your service.");
        
        addSection(contentPanel, "Service Categories", 
            "• Family: Service to immediate or extended family members.\n" +
            "• Vulnerable Populations: Service to those who are disadvantaged, at-risk, or in need.\n" +
            "• Community: Service that benefits your local community or neighborhood.\n" +
            "• Environmental: Service that benefits the environment in some way\n.");
        
        addSection(contentPanel, "Service Requirements", 
            "• Freshmen (Grade 9): 4 services total for at least 1 hour each\n" +
            "• Sophomores (Grade 10): 4 services total for at least 1 hour each\n" +
            "• Juniors (Grade 11): 4 services total for at least 2 hours each\n" +
            "• Seniors (Grade 12): 4 services total for at least 2 hours each\n\n" +
            "Students should complete hours across at least two different service categories each year.");
        
        addSection(contentPanel, "Verification Process", 
            "• After you submit your service hours, your supervisor will receive an email to verify your participation.\n" +
            "• After your supervisor verifies your participation, Tom will approve or decline your service.\n" + 
            "• Services will remain 'Pending' until approved by Tom, after which they will be marked as 'Completed'\n");
        
        addSection(contentPanel, "Dashboard", 
            "Your dashboard shows:\n" +
            "• Total hours completed\n" +
            "• Number of service submissions\n" +
            "• Pending approvals\n" +
            "• Recent submissions\n\n" +
            "Click 'Refresh Data' to update your dashboard with the latest information.");
        
        addSection(contentPanel, "Account Management", 
            "• To change your password, select 'Reset Password' from the Help menu.\n" +
            "• For technical issues, contact the IT department at support@piusxi.org. or Mr. Horky at jhorky@piusxi.org.\n" +
            "• For questions about service requirements and/or exemptions, contact Tom at tholschuh@piusxi.org.\n");
        
        // Add the content panel to a scroll pane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(PIUS_NAVY, 1));
        scrollPane.getViewport().setBackground(PIUS_WHITE);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Create button to return to dashboard
        JButton returnButton = new JButton("Return to Dashboard");
        returnButton.setBackground(PIUS_GOLD);
        returnButton.setForeground(PIUS_NAVY);
        returnButton.setFont(new Font("Arial", Font.BOLD, 14));
        returnButton.setFocusPainted(false);
        returnButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                studentHomepage homepage = new studentHomepage();
                homepage.setVisible(true);
            }
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(PIUS_WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        buttonPanel.add(returnButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Set the main panel as content pane
        setContentPane(mainPanel);
    }

    /**
     * Helper method to add a section to the instructions
     */
    private void addSection(JPanel panel, String title, String content) {
        // Section title with blue color
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(PIUS_NAVY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(titleLabel);
        
        // Add gold separator line
        JPanel separator = new JPanel();
        separator.setMaximumSize(new Dimension(600, 2));
        separator.setPreferredSize(new Dimension(600, 2));
        separator.setBackground(PIUS_GOLD);
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(separator);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        
        // Section content with navy text
        JTextArea contentArea = new JTextArea(content);
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setOpaque(false);
        contentArea.setFont(new Font("Arial", Font.PLAIN, 14));
        contentArea.setForeground(PIUS_NAVY);
        contentArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(contentArea);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
    }
}