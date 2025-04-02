package com.piusxi.student.frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
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
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.piusxi.student.backend.studentSession;

public class studentHomepage extends JFrame {

    private String studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String gradeYear;
    private String gradYear;

    public studentHomepage() {
        setTitle("Student Service Tracker");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        if (studentSession.getInstance().isSessionActive()) {
            setTitle("Student Service Tracker - " + studentSession.getInstance().getFullName());

            // loadServiceStats();
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
        String studentId = studentSession.getInstance().getStudentId();
        
        if (studentId == null || studentId.isEmpty()) {
            return;
        }

        // to be implemented
    }
        
    
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // File menu
        JMenu fileMenu = new JMenu("Home");
        fileMenu.addSeparator();
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
        JMenuItem serviceForm = new JMenuItem("Submit Service");
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
    
    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setPreferredSize(new Dimension(200, 0));
        sidePanel.setBorder(BorderFactory.createEtchedBorder());
        sidePanel.setBackground(new Color(230, 230, 250));
        
        // Add buttons to the side panel
        JButton profileBtn = new JButton("My Profile");
        JButton submitServiceBtn = new JButton("Submit Service");
        JButton viewCompletedBtn = new JButton("View Completed Service");
        
        // Make buttons fill width
        profileBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitServiceBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewCompletedBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add some space between components
        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(profileBtn);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(submitServiceBtn);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(viewCompletedBtn);
        
        return sidePanel;
    }
    
    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Add a welcome header
        JLabel headerLabel = new JLabel("Welcome to Student Service Tracker");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        centerPanel.add(headerLabel, BorderLayout.NORTH);
        
        // Add a dashboard panel
        JPanel dashboardPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // These values will be updated based on database for account signed in
        dashboardPanel.add(createDashboardCard("Total Hours", "45.5"));
        dashboardPanel.add(createDashboardCard("Services Completed", "12"));
        dashboardPanel.add(createDashboardCard("Pending Approval", "3"));
        dashboardPanel.add(createDashboardCard("Remaining Goal", "14.5"));
        
        centerPanel.add(dashboardPanel, BorderLayout.CENTER);
        
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            studentHomepage homepage = new studentHomepage();
            homepage.setVisible(true);
        });
    }
}