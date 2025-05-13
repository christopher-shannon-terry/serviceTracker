package com.piusxi.student.frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.piusxi.student.backend.studentSession;
import com.piusxi.student.database.serviceSubmissionDatabase;

public class studentHomepage extends JFrame {

    // Pius XI school colors
    private static final Color PIUS_NAVY = new Color(0, 32, 91);
    private static final Color PIUS_GOLD = new Color(255, 215, 0);
    private static final Color PIUS_WHITE = Color.WHITE;
    private static final Color LIGHT_GRAY_BG = new Color(245, 245, 250);

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
        getContentPane().setBackground(PIUS_WHITE);

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
                studentLogin login = new studentLogin();
                login.setVisible(true);
            });

            return;
        }
        
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

    private void loadServiceStats() {
        serviceStats = serviceSubmissionDatabase.getServiceStats(studentId);
    }   
    
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(PIUS_NAVY);
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, PIUS_WHITE));
        
        // File menu
        JMenu fileMenu = new JMenu("Home");
        fileMenu.setForeground(PIUS_WHITE);
        fileMenu.setFont(new Font("Arial", Font.BOLD, 14));
        
        JMenuItem exit = new JMenuItem("Exit");
        exit.setBackground(PIUS_WHITE);
        exit.setForeground(PIUS_NAVY);
        exit.setFont(new Font("Arial", Font.PLAIN, 14));
        fileMenu.add(exit);
        exit.addActionListener((ActionEvent e) -> {
            dispose();
            
            System.exit(0);
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
                instructionPage instructionPage = new instructionPage();
                instructionPage.setVisible(true);
            }
        });

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
    
    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setPreferredSize(new Dimension(220, 0));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sidePanel.setBackground(PIUS_NAVY);

        // Student info section
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(PIUS_WHITE);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PIUS_GOLD, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.setMaximumSize(new Dimension(200, 180));

        // Style for info panel labels
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Color labelColor = PIUS_NAVY;

        JLabel nameLabel = new JLabel("Name: " + firstName + " " + lastName);
        nameLabel.setForeground(labelColor);
        nameLabel.setFont(labelFont);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel idLabel = new JLabel("ID: " + studentId);
        idLabel.setForeground(labelColor);
        idLabel.setFont(labelFont);
        idLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel gradeLabel = new JLabel("Grade: " + gradeYear);
        gradeLabel.setForeground(labelColor);
        gradeLabel.setFont(labelFont);
        gradeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel gradYearLabel = new JLabel("Graduation: " + gradYear);
        gradYearLabel.setForeground(labelColor);
        gradYearLabel.setFont(labelFont);
        gradYearLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(idLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(gradeLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(gradYearLabel);
        infoPanel.add(Box.createVerticalStrut(5));

        // Add buttons to the side panel with Pius colors
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        
        JButton submitServiceBtn = new JButton("Submit Service");
        submitServiceBtn.setBackground(PIUS_GOLD);
        submitServiceBtn.setForeground(PIUS_NAVY);
        submitServiceBtn.setFont(buttonFont);
        submitServiceBtn.setFocusPainted(false);
        submitServiceBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        submitServiceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                serviceReportingForm form = new serviceReportingForm();
                form.setVisible(true);
            }
        });

        JButton viewSubmissionsBtn = new JButton("View All Submissions");
        viewSubmissionsBtn.setBackground(PIUS_GOLD);
        viewSubmissionsBtn.setForeground(PIUS_NAVY);
        viewSubmissionsBtn.setFont(buttonFont);
        viewSubmissionsBtn.setFocusPainted(false);
        viewSubmissionsBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        viewSubmissionsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // This would open a detailed view of all submissions
                viewAllSubmissions allSubmissions = new viewAllSubmissions();
                allSubmissions.setVisible(true);
            }
        });

        JButton resetPasswordBtn = new JButton("Change Password");
        resetPasswordBtn.setBackground(PIUS_GOLD);
        resetPasswordBtn.setForeground(PIUS_NAVY);
        resetPasswordBtn.setFont(buttonFont);
        resetPasswordBtn.setFocusPainted(false);
        resetPasswordBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        resetPasswordBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                forgotPasswordForm form = new forgotPasswordForm();
                form.setVisible(true);
            }
        });

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(Color.LIGHT_GRAY);
        logoutBtn.setForeground(PIUS_NAVY);
        logoutBtn.setFont(buttonFont);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                studentSession.getInstance().endSession();
                dispose();
                studentLogin login = new studentLogin();
                login.setVisible(true);
            }
        });

        // Make buttons fill width
        submitServiceBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewSubmissionsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetPasswordBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Set maximum width for buttons
        Dimension btnDimension = new Dimension(200, 35);
        submitServiceBtn.setMaximumSize(btnDimension);
        viewSubmissionsBtn.setMaximumSize(btnDimension);
        resetPasswordBtn.setMaximumSize(btnDimension);
        logoutBtn.setMaximumSize(btnDimension);
        
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
        sidePanel.add(Box.createVerticalGlue());
        
        return sidePanel;
    }
    
    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        centerPanel.setBackground(PIUS_WHITE);

        // Add a welcome header
        JLabel headerLabel = new JLabel("Welcome, " + firstName + "!");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setForeground(PIUS_NAVY);
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        centerPanel.add(headerLabel, BorderLayout.NORTH);

        // Create a panel for dashboard contents
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        contentPanel.setBackground(PIUS_WHITE);
        
        // Dashboard stats at the top
        JPanel dashboardPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        dashboardPanel.setBackground(PIUS_WHITE);

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

        tableModel.addColumn("Date");
        tableModel.addColumn("Type");
        tableModel.addColumn("Hours");
        tableModel.addColumn("Status");

        // Create the table and set properties
        recentSubmissionsTable = new JTable(tableModel);
        recentSubmissionsTable.setFillsViewportHeight(true);
        recentSubmissionsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        recentSubmissionsTable.setRowHeight(25);
        recentSubmissionsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Style the table header
        JTableHeader header = recentSubmissionsTable.getTableHeader();
        header.setBackground(PIUS_NAVY);
        header.setForeground(PIUS_WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Style the table rows with alternating colors
        recentSubmissionsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (isSelected) {
                    c.setBackground(PIUS_GOLD);
                    c.setForeground(PIUS_NAVY);
                } else {
                    c.setBackground(row % 2 == 0 ? PIUS_WHITE : LIGHT_GRAY_BG);
                    c.setForeground(PIUS_NAVY);
                }
                
                return c;
            }
        });

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(recentSubmissionsTable);
        scrollPane.getViewport().setBackground(PIUS_WHITE);
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
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshData();
            }
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
        valueLabel.setFont(new Font("Arial", Font.BOLD, 36));
        valueLabel.setForeground(PIUS_GOLD);
        
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