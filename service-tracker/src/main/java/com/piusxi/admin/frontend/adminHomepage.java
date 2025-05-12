package com.piusxi.admin.frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import com.piusxi.admin.backend.generateReport;
import com.piusxi.student.database.serviceSubmissionDatabase;
import com.piusxi.student.database.studentInformationDatabase;

public class adminHomepage extends JFrame {
    
    // Pius XI school colors - added for consistency with student side
    private static final Color PIUS_NAVY = new Color(0, 32, 91);
    private static final Color PIUS_GOLD = new Color(255, 215, 0);
    private static final Color PIUS_WHITE = Color.WHITE;
    private static final Color LIGHT_GRAY_BG = new Color(245, 245, 250);
    
    private JTable recentSubmissionsTable;
    private DefaultTableModel tableModel;

    private JLabel totalStudentsValue;
    private JLabel totalSubmissionsValue;
    private JLabel pendingApprovalsValue;
    private JLabel approvedServicesValue;
    
    public adminHomepage() {
        setTitle("Service Tracker - Administration");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(PIUS_WHITE);
        
        // Create menu bar
        createMenuBar();
        
        // Create main content panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(PIUS_WHITE);
        
        // Create side panel (left side)
        JPanel sidePanel = createSidePanel();
        mainPanel.add(sidePanel, BorderLayout.WEST);
        
        // Create center content panel
        JPanel centerPanel = createCenterPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Add the main panel to the frame
        add(mainPanel);
    }
    
    public void createMenuBar() {
        JMenuBar navigationBar = new JMenuBar();
        navigationBar.setBackground(PIUS_NAVY);
        navigationBar.setForeground(PIUS_WHITE);
        navigationBar.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, PIUS_WHITE));

        // Home menu
        JMenu homeMenu = new JMenu("Home");
        homeMenu.setForeground(PIUS_WHITE);
        homeMenu.setFont(new Font("Arial", Font.BOLD, 14));
        
        JMenuItem exit = new JMenuItem("Exit");
        exit.setBackground(PIUS_WHITE);
        exit.setForeground(PIUS_NAVY);
        exit.setFont(new Font("Arial", Font.PLAIN, 14));
        homeMenu.add(exit);
        exit.addActionListener((ActionEvent e) -> {
            dispose();
            
            System.exit(0);
        });

        // Students menu
        JMenu studentsMenu = new JMenu("Students");
        studentsMenu.setForeground(PIUS_WHITE);
        studentsMenu.setFont(new Font("Arial", Font.BOLD, 14));
        
        JMenuItem allStudents = new JMenuItem("All");
        allStudents.setBackground(PIUS_WHITE);
        allStudents.setForeground(PIUS_NAVY);
        allStudents.setFont(new Font("Arial", Font.PLAIN, 14));
        studentsMenu.add(allStudents);
        allStudents.addActionListener((ActionEvent e) -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                new allStudents().setVisible(true);
            });
        });

        JMenuItem freshmenStudents = new JMenuItem("Freshmen");
        freshmenStudents.setBackground(PIUS_WHITE);
        freshmenStudents.setForeground(PIUS_NAVY);
        freshmenStudents.setFont(new Font("Arial", Font.PLAIN, 14));
        studentsMenu.add(freshmenStudents);
        freshmenStudents.addActionListener((ActionEvent e) -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                new allFreshmen().setVisible(true);
            });
        });

        JMenuItem sophomoreStudents = new JMenuItem("Sophomores");
        sophomoreStudents.setBackground(PIUS_WHITE);
        sophomoreStudents.setForeground(PIUS_NAVY);
        sophomoreStudents.setFont(new Font("Arial", Font.PLAIN, 14));
        studentsMenu.add(sophomoreStudents);
        sophomoreStudents.addActionListener((ActionEvent e) -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                new allSophomores().setVisible(true);
            });
        });
        
        JMenuItem juniorStudents = new JMenuItem("Juniors");
        juniorStudents.setBackground(PIUS_WHITE);
        juniorStudents.setForeground(PIUS_NAVY);
        juniorStudents.setFont(new Font("Arial", Font.PLAIN, 14));
        studentsMenu.add(juniorStudents);
        juniorStudents.addActionListener((ActionEvent e) -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                new allJuniors().setVisible(true);
            });
        });

        JMenuItem seniorStudents = new JMenuItem("Seniors");
        seniorStudents.setBackground(PIUS_WHITE);
        seniorStudents.setForeground(PIUS_NAVY);
        seniorStudents.setFont(new Font("Arial", Font.PLAIN, 14));
        studentsMenu.add(seniorStudents);
        seniorStudents.addActionListener((ActionEvent e) -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                new allSeniors().setVisible(true);
            });
        });

        // Reports menu
        JMenu reportsMenu = new JMenu("Reports");
        reportsMenu.setForeground(PIUS_WHITE);
        reportsMenu.setFont(new Font("Arial", Font.BOLD, 14));
        
        JMenuItem generateReports = new JMenuItem("Generate Report");
        generateReports.setBackground(PIUS_WHITE);
        generateReports.setForeground(PIUS_NAVY);
        generateReports.setFont(new Font("Arial", Font.PLAIN, 14));
        reportsMenu.add(generateReports);
        generateReports.addActionListener((ActionEvent e) -> {
            try {
                String reportPath = generateReport.generateFile(null);
                JOptionPane.showMessageDialog(this, 
                    "Report generated successfully!\nSaved to: " + reportPath, 
                    "Report Generated", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (SQLException se) {
                JOptionPane.showMessageDialog(this, 
                    "Database error: " + se.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
                se.printStackTrace();
            }
            catch (IOException ie) {
                JOptionPane.showMessageDialog(this, 
                    "File error: " + ie.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
                ie.printStackTrace();
            }
        });

        navigationBar.add(homeMenu);
        navigationBar.add(studentsMenu);
        navigationBar.add(reportsMenu);

        setJMenuBar(navigationBar);
    }

    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setPreferredSize(new Dimension(200, 0));
        sidePanel.setBorder(BorderFactory.createEtchedBorder());
        sidePanel.setBackground(PIUS_NAVY);

        // Admin info section
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(PIUS_WHITE);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PIUS_GOLD, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.setMaximumSize(new Dimension(180, 90));

        JLabel roleLabel = new JLabel("Role: Administrator");
        roleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        roleLabel.setForeground(PIUS_NAVY);
        roleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(roleLabel);
        infoPanel.add(Box.createVerticalStrut(5));

        // Navigation buttons
        JButton viewAllStudentsBtn = createNavButton("View All Students");
        viewAllStudentsBtn.addActionListener((ActionEvent e) -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                new allStudents().setVisible(true);
            });
        });

        JButton viewFreshmenBtn = createNavButton("View Freshmen");
        viewFreshmenBtn.addActionListener((ActionEvent e) -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                new allFreshmen().setVisible(true);
            });
        });

        JButton viewSophomoresBtn = createNavButton("View Sophomores");
        viewSophomoresBtn.addActionListener((ActionEvent e) -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                new allSophomores().setVisible(true);
            });
        });

        JButton viewJuniorsBtn = createNavButton("View Juniors");
        viewJuniorsBtn.addActionListener((ActionEvent e) -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                new allJuniors().setVisible(true);
            });
        });

        JButton viewSeniorsBtn = createNavButton("View Seniors");
        viewSeniorsBtn.addActionListener((ActionEvent e) -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                new allSeniors().setVisible(true);
            });
        });

        JButton generateReportBtn = createNavButton("Generate Report");
        generateReportBtn.addActionListener((ActionEvent e) -> {
            try {
                String reportPath = generateReport.generateFile(null);
                JOptionPane.showMessageDialog(this, 
                    "Report generated successfully!\nSaved to: " + reportPath, 
                    "Report Generated", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (SQLException se) {
                JOptionPane.showMessageDialog(this, 
                    "Database error: " + se.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
                se.printStackTrace();
            }
            catch (IOException ie) {
                JOptionPane.showMessageDialog(this, 
                    "File error: " + ie.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
                ie.printStackTrace();
            }
        });

        // Add some space between components
        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(infoPanel);
        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(viewAllStudentsBtn);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(viewFreshmenBtn);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(viewSophomoresBtn);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(viewJuniorsBtn);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(viewSeniorsBtn);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(generateReportBtn);
        
        return sidePanel;
    }
    
    // Helper method to create consistently styled navigation buttons
    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(PIUS_GOLD);
        button.setForeground(PIUS_NAVY);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(180, 35));
        return button;
    }
    
    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        centerPanel.setBackground(PIUS_WHITE);

        // Add a welcome header
        JLabel headerLabel = new JLabel("Dashboard");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setForeground(PIUS_NAVY);
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        centerPanel.add(headerLabel, BorderLayout.NORTH);

        // Create a panel for dashboard contents
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        contentPanel.setBackground(PIUS_WHITE);
        
        // Dashboard stats at the top
        JPanel dashboardPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        dashboardPanel.setBackground(PIUS_WHITE);

        // Loading stats from database
        int[] stats = loadAdminDashboardStats();
        
        dashboardPanel.add(createDashboardCard("Total Students", String.valueOf(stats[0])));
        dashboardPanel.add(createDashboardCard("Total Submissions", String.valueOf(stats[1])));
        dashboardPanel.add(createDashboardCard("Pending Approvals", String.valueOf(stats[2])));
        dashboardPanel.add(createDashboardCard("Approved Services", String.valueOf(stats[3])));

        contentPanel.add(dashboardPanel, BorderLayout.NORTH);

        // Recent submissions table
        JPanel recentSubmissionsPanel = new JPanel(new BorderLayout());
        recentSubmissionsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PIUS_GOLD, 2),
            "Recent Service Submissions",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 16),
            PIUS_NAVY
        ));
        recentSubmissionsPanel.setBackground(PIUS_WHITE);

        // Create table model with column names
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make the table non-editable
            }
        };

        tableModel.addColumn("Student ID");
        tableModel.addColumn("Student Name");
        tableModel.addColumn("Service Type");
        tableModel.addColumn("Hours");
        tableModel.addColumn("Submission Date");
        tableModel.addColumn("Status");

        // Create the table and set properties
        recentSubmissionsTable = new JTable(tableModel);
        recentSubmissionsTable.setFillsViewportHeight(true);
        recentSubmissionsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        recentSubmissionsTable.setRowHeight(25);
        recentSubmissionsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Style the table header
        recentSubmissionsTable.getTableHeader().setBackground(PIUS_NAVY);
        recentSubmissionsTable.getTableHeader().setForeground(PIUS_WHITE);
        recentSubmissionsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(recentSubmissionsTable);
        scrollPane.getViewport().setBackground(PIUS_WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(PIUS_NAVY, 1));
        recentSubmissionsPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Load recent submissions into the table
        loadRecentSubmissions();
        
        contentPanel.add(recentSubmissionsPanel, BorderLayout.CENTER);
        
        // Add refresh button at the bottom
        JButton refreshButton = new JButton("Refresh Data");
        refreshButton.setBackground(PIUS_GOLD);
        refreshButton.setForeground(PIUS_NAVY);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        refreshButton.setFocusPainted(false);
        refreshButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        refreshButton.addActionListener((ActionEvent e) -> {
            refreshData();
        });
        
        JPanel refreshPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        refreshPanel.setBackground(PIUS_WHITE);
        refreshPanel.add(refreshButton);
        contentPanel.add(refreshPanel, BorderLayout.SOUTH);
        
        centerPanel.add(contentPanel, BorderLayout.CENTER);
        
        return centerPanel;
    }
    
    private JPanel createDashboardCard(String title, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PIUS_NAVY, 2, true),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(PIUS_WHITE);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(PIUS_NAVY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setHorizontalAlignment(JLabel.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 32));
        valueLabel.setForeground(PIUS_GOLD);
        
        // Store references to value labels for later updates
        switch (title) {
            case "Total Students" -> totalStudentsValue = valueLabel;
            case "Total Submissions" -> totalSubmissionsValue = valueLabel;
            case "Pending Approvals" -> pendingApprovalsValue = valueLabel;
            case "Approved Services" -> approvedServicesValue = valueLabel;
            default -> {
            }
        }
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }
    
    private int[] loadAdminDashboardStats() {
        int[] stats = new int[4]; // [totalStudents, totalSubmissions, pendingApprovals, approvedServices]
        
        Connection studentConnection = null;
        Connection serviceConnection = null;
        
        try {
            // Get total students count
            studentConnection = studentInformationDatabase.connect();
            if (studentConnection != null) {
                String studentQuery = "SELECT COUNT(*) FROM Students";
                PreparedStatement studentPS = studentConnection.prepareStatement(studentQuery);
                ResultSet studentResult = studentPS.executeQuery();
                
                if (studentResult.next()) {
                    stats[0] = studentResult.getInt(1);
                }
                
                studentResult.close();
                studentPS.close();
            }
            
            // Get service submission stats
            serviceConnection = serviceSubmissionDatabase.connect();
            if (serviceConnection != null) {
                // Total submissions
                String totalQuery = "SELECT COUNT(*) FROM service_submissions";
                PreparedStatement totalPS = serviceConnection.prepareStatement(totalQuery);
                ResultSet totalResult = totalPS.executeQuery();
                
                if (totalResult.next()) {
                    stats[1] = totalResult.getInt(1);
                }
                
                totalResult.close();
                totalPS.close();
                
                // Pending approvals
                String pendingQuery = "SELECT COUNT(*) FROM service_submissions WHERE status = 'pending'";
                PreparedStatement pendingPS = serviceConnection.prepareStatement(pendingQuery);
                ResultSet pendingResult = pendingPS.executeQuery();
                
                if (pendingResult.next()) {
                    stats[2] = pendingResult.getInt(1);
                }
                
                pendingResult.close();
                pendingPS.close();
                
                // Approved services
                String approvedQuery = "SELECT COUNT(*) FROM service_submissions WHERE status = 'approved'";
                PreparedStatement approvedPS = serviceConnection.prepareStatement(approvedQuery);
                ResultSet approvedResult = approvedPS.executeQuery();
                
                if (approvedResult.next()) {
                    stats[3] = approvedResult.getInt(1);
                }
                
                approvedResult.close();
                approvedPS.close();
            }
        }
        catch (SQLException se) {
            System.err.println("Error loading dashboard stats: " + se.getMessage());
            se.printStackTrace();
        }
        finally {
            try {
                if (studentConnection != null) studentConnection.close();
                if (serviceConnection != null) serviceConnection.close();
            }
            catch (SQLException se) {
                se.printStackTrace();
            }
        }
        
        return stats;
    }
    
    private void loadRecentSubmissions() {
        tableModel.setRowCount(0);
        
        Connection serviceConnection = null;
        Connection studentConnection = null;
        
        try {
            serviceConnection = serviceSubmissionDatabase.connect();
            studentConnection = studentInformationDatabase.connect();
            
            if (serviceConnection != null && studentConnection != null) {
                // Query to get recent service submissions
                String query = "SELECT s.student_id, s.service_type, s.service_event_length, " +
                               "s.submission_date, s.status FROM service_submissions s " +
                               "ORDER BY s.submission_date DESC LIMIT 10";
                
                PreparedStatement ps = serviceConnection.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                
                while (rs.next()) {
                    int studentId = rs.getInt("student_id");
                    String serviceType = rs.getString("service_type");
                    double hours = rs.getDouble("service_event_length");
                    Date submissionDate = rs.getTimestamp("submission_date");
                    String status = rs.getString("status");
                    
                    // Get student name from student database
                    String studentName = "Unknown";
                    String studentQuery = "SELECT first_name, last_name FROM Students WHERE student_id = ?";
                    PreparedStatement studentPS = studentConnection.prepareStatement(studentQuery);
                    studentPS.setInt(1, studentId);
                    ResultSet studentRS = studentPS.executeQuery();
                    
                    if (studentRS.next()) {
                        studentName = studentRS.getString("first_name") + " " + studentRS.getString("last_name");
                    }
                    
                    studentRS.close();
                    studentPS.close();
                    
                    // Add row to table
                    tableModel.addRow(new Object[]{
                        studentId,
                        studentName,
                        serviceType,
                        hours,
                        dateFormat.format(submissionDate),
                        status
                    });
                }
                
                rs.close();
                ps.close();
            }
        }
        catch (SQLException se) {
            System.err.println("Error loading recent submissions: " + se.getMessage());
            se.printStackTrace();
        }
        finally {
            try {
                if (serviceConnection != null) serviceConnection.close();
                if (studentConnection != null) studentConnection.close();
            }
            catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    
    private void refreshData() {
        // Reload dashboard stats
        int[] stats = loadAdminDashboardStats();
        
        // Update dashboard cards using stored references
        totalStudentsValue.setText(String.valueOf(stats[0]));
        totalSubmissionsValue.setText(String.valueOf(stats[1]));
        pendingApprovalsValue.setText(String.valueOf(stats[2]));
        approvedServicesValue.setText(String.valueOf(stats[3]));
        
        // Reload recent submissions
        loadRecentSubmissions();
        
        // Redraw the UI
        repaint();
        
        JOptionPane.showMessageDialog(this,
            "Data refreshed successfully!",
            "Refresh", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            adminHomepage homepage = new adminHomepage();
            homepage.setVisible(true);
        });
    }
}