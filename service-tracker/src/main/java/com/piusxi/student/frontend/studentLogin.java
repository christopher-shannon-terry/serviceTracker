package com.piusxi.student.frontend;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.piusxi.admin.backend.adminLogin;
import com.piusxi.student.backend.login;
import com.piusxi.student.backend.login.loginResult;
import com.piusxi.student.backend.studentSession;
import com.piusxi.admin.backend.adminLogin.adminResult;
import com.piusxi.admin.frontend.adminHomepage;
import com.piusxi.student.database.serviceSubmissionDatabase;
import com.piusxi.student.database.studentInformationDatabase;

public class studentLogin extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createAccountButton;

    private static final ScheduledExecutorService dbUpdates; // Scheduled maintenance for database updates

    // Static initializer block - runs once the class is loaded
    static {
        dbUpdates = Executors.newScheduledThreadPool(1);

        /* Runs wipeSubmissions(), wipeSeniors() and updateGradeYear() daily at midnight
         * See definition of wipeSubmissions(), wipeSeniors() and updateGradeYear() to see how they work
         */
        dbUpdates.scheduleAtFixedRate(() -> {
            try {
                Connection connection = studentInformationDatabase.connect();
                Connection submissionsConnection = serviceSubmissionDatabase.connect();

                if (connection != null && submissionsConnection != null) {
                    serviceSubmissionDatabase.wipeSubmissions(connection); // Delete all submissions
                    studentInformationDatabase.wipeSeniors(connection); // Remove all seniors
                    studentInformationDatabase.updateGradeYear(null, connection); // Update all grade years (9 -> 10, 10 -> 11, 11 -> 12);
                
                    connection.close();
                }
            }
            catch (SQLException se) {
                se.printStackTrace();
            }
        }, 0L, 24L, TimeUnit.HOURS);
    }
    
    public studentLogin() {
        setTitle("Pius XI Service Hour Tracker");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        loginPage();
    }

    public void loginPage() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Create the content panel with BoxLayout for vertical arrangement
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        // Add spacing at the top
        contentPanel.add(Box.createVerticalGlue());
        
        JLabel titleLabel = new JLabel("Pius XI Service Hour Tracker");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        contentPanel.add(titleLabel);
        
        // Add spacing after the title
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(150, 25));
        
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        
        // Wrap username panel in a fixed width panel
        JPanel usernameWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        usernameWrapper.add(usernamePanel);
        usernameWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(usernameWrapper);
        
        // Add spacing between username and password
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(150, 25));
        
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        
        // Wrap password panel in a fixed width panel
        JPanel passwordWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passwordWrapper.add(passwordPanel);
        passwordWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        contentPanel.add(passwordWrapper);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginToStudentHomePage();
            }
        });
        
        createAccountButton = new JButton("Create New Account");
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCreateAccountForm();
            }
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(createAccountButton);
        
        contentPanel.add(buttonPanel);
        contentPanel.add(Box.createVerticalGlue());

        JPanel centeringPanel = new JPanel(new GridBagLayout());
        centeringPanel.add(contentPanel);

        mainPanel.add(centeringPanel, BorderLayout.CENTER);
        add(mainPanel);
    }
    
    private void loginToStudentHomePage() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        loginResult student = login.authenticate(username, password);

        if (student.isAuthenticated()) {
            boolean sessionStarted = studentSession.getInstance().startSession(student.getStudentId());

            if (!sessionStarted) {
                JOptionPane.showMessageDialog(this,
                    "Session error: Could not load student information.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this,
                "Login Successful! Welcome back, " + student.getFirstName() + ".",
                "Success", JOptionPane.INFORMATION_MESSAGE);

            dispose();

            SwingUtilities.invokeLater(() -> {
                studentHomepage homepage = new studentHomepage();
                homepage.setVisible(true);
            });
            return;
        }

        adminResult administrator = adminLogin.authenticate(username, password);
        
        if (administrator.isAuthenticated()) {
            JOptionPane.showMessageDialog(this,
            "Admin Login Successful",
            "Success", JOptionPane.INFORMATION_MESSAGE);

            dispose();

            SwingUtilities.invokeLater(() -> {
                adminHomepage homepage = new adminHomepage();
                homepage.setVisible(true);
            });
            return;
        }

        JOptionPane.showMessageDialog(this,
        student.getErrorMessage(),
        "Login Error", JOptionPane.ERROR_MESSAGE);
    }

    private void openCreateAccountForm() {
        dispose();

        SwingUtilities.invokeLater(() -> {
            createAccountForm form = new createAccountForm();
            form.setVisible(true);
        });
    }
    
    public static void main(String[] args) { 
        SwingUtilities.invokeLater(() -> {
            studentLogin login = new studentLogin();
            login.setVisible(true);
        });
    }
}