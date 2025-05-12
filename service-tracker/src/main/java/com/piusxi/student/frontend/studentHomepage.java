package com.piusxi.student.frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.piusxi.student.backend.studentSession;
import com.piusxi.student.database.serviceSubmissionDatabase;

public class studentHomepage extends JFrame {

    private String studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String gradeYear;
    private String gradYear;

    private Map<String, Object> serviceStats;
    private JTable recentSubmissionsTable;
    private DefaultTableModel tableModel;

    public studentHomepage() {
        setTitle("Student Service Tracker");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        if (studentSession.getInstance().isSessionActive()) {
            setTitle("Student Service Tracker - " + studentSession.getInstance().getFullName());
            studentId = studentSession.getInstance().getStudentId();
            firstName = studentSession.getInstance().getFirstName();
            lastName = studentSession.getInstance().getLastName();
            email = studentSession.getInstance().getEmail();
            gradeYear = studentSession.getInstance().getGradeYear();
            gradYear = studentSession.getInstance().getGradYear();

            loadServiceStats();
        }
        else {
            JOptionPane.showMessageDialog(this,
                "No active session. Please log in first.",
                "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            
            SwingUtilities.invokeLater(() -> {
                new studentLogin().setVisible(true);
            });

            return;
        }
        
        // Create menu bar
        createMenuBar();
        
        // Create main content panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Create side panel (left side)
        JPanel sidePanel = createSidePanel();
        mainPanel.add(sidePanel, BorderLayout.WEST);
        
        // Create center content panel
        JPanel centerPanel = createCenterPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Add the main panel to the frame
        add(mainPanel);
    }

    private void loadServiceStats() {
        serviceStats = serviceSubmissionDatabase.getServiceStats(studentId);
    }   
    
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // File menu
        JMenu fileMenu = new JMenu("Home");
        JMenuItem exit = new JMenuItem("Exit");
        fileMenu.add(exit);
        exit.addActionListener((ActionEvent e) -> {
            dispose();

            System.exit(0);
        });
        
        // Service menu
        JMenu serviceMenu = new JMenu("Service");
        JMenuItem serviceForm = new JMenuItem("Submit Service");
        serviceMenu.add(serviceForm);

        serviceForm.addActionListener((ActionEvent e) -> {
            dispose();

            SwingUtilities.invokeLater(() -> {
                new serviceReportingForm().setVisible(true);
            });
        });

        JMenuItem submissions = new JMenuItem("Submissions");
        serviceMenu.add(submissions);

        submissions.addActionListener((ActionEvent e) -> {
            SwingUtilities.invokeLater(() -> {
                dispose();

                new viewAllSubmissions().setVisible(true);
            });
        });
        
        // Help menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem instructions = new JMenuItem("Instructions");
        helpMenu.add(instructions);
        
        instructions.addActionListener((ActionEvent e) -> {
            dispose();

            SwingUtilities.invokeLater(() -> {
                new instructionPage().setVisible(true);
            });
        });

        JMenuItem resetPassword = new JMenuItem("Reset Password");
        helpMenu.add(resetPassword);

        resetPassword.addActionListener((ActionEvent e) -> {
            dispose();
            
            SwingUtilities.invokeLater(() -> {
                new forgotPasswordForm().setVisible(true);
            });
        });
        
        // Add menus to menu bar
        menuBar.add(fileMenu);
        menuBar.add(serviceMenu);
        menuBar.add(helpMenu);
        
        // Set the menu bar to the frame
        setJMenuBar(menuBar);
    }
    
    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setPreferredSize(new Dimension(200, 0));
        sidePanel.setBorder(BorderFactory.createEtchedBorder());
        sidePanel.setBackground(new Color(230, 230, 250));

        // Student info section
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(220, 220, 240));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Student Information"));
        infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.setMaximumSize(new Dimension(190, 150));

        JLabel nameLabel = new JLabel("Name: " + firstName + " " + lastName);
        JLabel idLabel = new JLabel("ID: " + studentId);
        JLabel gradeLabel = new JLabel("Grade: " + gradeYear);
        JLabel gradYearLabel = new JLabel("Graduation: " + gradYear);

        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(idLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(gradeLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(gradYearLabel);
        infoPanel.add(Box.createVerticalStrut(5));

        // Add buttons to the side panel
        JButton submitServiceBtn = new JButton("Submit Service");
        submitServiceBtn.addActionListener((ActionEvent e) -> {
            dispose();

            SwingUtilities.invokeLater(() -> {
                new serviceReportingForm().setVisible(true);
            });
        });

        JButton viewSubmissionsBtn = new JButton("View All Submissions");
        viewSubmissionsBtn.addActionListener((ActionEvent e) -> {
            dispose();

            SwingUtilities.invokeLater(() -> {
                new viewAllSubmissions().setVisible(true);
            });
        });

        JButton resetPasswordBtn = new JButton("Change Password");
        resetPasswordBtn.addActionListener((ActionEvent e) -> {
            dispose();

            SwingUtilities.invokeLater(() -> {
                new forgotPasswordForm().setVisible(true);
            });
        });

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener((ActionEvent e) -> {
            studentSession.getInstance().endSession();
            dispose();

            SwingUtilities.invokeLater(() -> {
                new studentLogin().setVisible(true);
            });
        });

        // Make buttons fill width
        submitServiceBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewSubmissionsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetPasswordBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add some space between components
        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(infoPanel);
        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(submitServiceBtn);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(viewSubmissionsBtn);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(resetPasswordBtn);
        sidePanel.add(Box.createVerticalStrut(30));
        sidePanel.add(logoutBtn);
        
        return sidePanel;
    }
    
    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add a welcome header
        JLabel headerLabel = new JLabel("Welcome, " + firstName + "!");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        centerPanel.add(headerLabel, BorderLayout.NORTH);

        // Create a tabbed panel for dashboard contents
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        
        // Dashboard stats at the top
        JPanel dashboardPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Format for displaying hours with one decimal place
        DecimalFormat df = new DecimalFormat("0.0");

        // Service stats from database
        dashboardPanel.add(createDashboardCard("Total Hours", df.format((Double)serviceStats.get("totalHours"))));
        dashboardPanel.add(createDashboardCard("Completed Services", String.valueOf(serviceStats.get("completedServices"))));
        dashboardPanel.add(createDashboardCard("Pending Approval", String.valueOf(serviceStats.get("pendingServices"))));
        dashboardPanel.add(createDashboardCard("Recent Submissions", String.valueOf(
            (Integer)serviceStats.get("completedServices") + (Integer)serviceStats.get("pendingServices"))));

        contentPanel.add(dashboardPanel, BorderLayout.NORTH);

        // Recent submissions table
        JPanel recentSubmissionsPanel = new JPanel(new BorderLayout());
        recentSubmissionsPanel.setBorder(BorderFactory.createTitledBorder("Recent Service Submissions"));

        // Create table model with column names
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make the table non-editable
            }
        };

        tableModel.addColumn("Date");
        tableModel.addColumn("Type");
        tableModel.addColumn("Hours");
        tableModel.addColumn("Status");

        // Create the table and set properties
        recentSubmissionsTable = new JTable(tableModel);
        recentSubmissionsTable.setFillsViewportHeight(true);
        recentSubmissionsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

         // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(recentSubmissionsTable);
        recentSubmissionsPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Load recent submissions into the table
        loadRecentSubmissions();
        
        contentPanel.add(recentSubmissionsPanel, BorderLayout.CENTER);
        
        // Add refresh button at the bottom
        JButton refreshButton = new JButton("Refresh Data");
        refreshButton.addActionListener((ActionEvent e) -> {
            refreshData();
        });
        
        JPanel refreshPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        refreshPanel.add(refreshButton);
        contentPanel.add(refreshPanel, BorderLayout.SOUTH);
        
        centerPanel.add(contentPanel, BorderLayout.CENTER);
        
        return centerPanel;
    }
    
    private JPanel createDashboardCard(String title, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
        card.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setHorizontalAlignment(JLabel.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 32));
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }

    private void loadRecentSubmissions() {
        tableModel.setRowCount(0);

        Connection connection = null;
        try {
            connection = serviceSubmissionDatabase.connect();
            if (connection == null) {
                throw new SQLException("Failed to establish database connection");
            }
            
            List<Map<String, String>> submissions = serviceSubmissionDatabase.getStudentServiceSubmissions(studentId, connection);
            
            // Add submissions to the table (limited to 5 most recent)
            int count = 0;
            for (Map<String, String> submission : submissions) {
                if (count >= 5) break; // Limit to 5 rows
                
                String submissionDate = submission.get("submission_date");
                String serviceType = submission.get("service_type");
                String hours = submission.get("service_event_length");
                String status = submission.get("status");
                
                tableModel.addRow(new Object[]{submissionDate, serviceType, hours, status});
                count++;
            }
        }
        catch (SQLException se) {
            JOptionPane.showMessageDialog(this,
                "Error loading service submissions: " + se.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
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

    private void refreshData() {
        // Reload service stats
        loadServiceStats();
        
        // Reload recent submissions
        loadRecentSubmissions();
        
        // Redraw the UI
        repaint();
        
        JOptionPane.showMessageDialog(this,
            "Data refreshed successfully!",
            "Refresh", JOptionPane.INFORMATION_MESSAGE);
    }
}