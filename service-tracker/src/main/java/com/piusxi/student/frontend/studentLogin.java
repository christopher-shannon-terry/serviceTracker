package com.piusxi.student.frontend;

import com.piusxi.student.backend.login;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class studentLogin extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createAccountButton;
    
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
    
    // Gonna move the logic to login.java in backend
    private void loginToStudentHomePage() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Authenticate against database goes to login.java in backend (change file name, not good)

        login.authenticateLogin();

        openStudentHomepage();
    }
    
    private void openCreateAccountForm() {
        dispose();

        SwingUtilities.invokeLater(() -> {
            createAccountForm form = new createAccountForm();
            form.setVisible(true);
        });
    }
    
    private void openStudentHomepage() {
        dispose();
    
        SwingUtilities.invokeLater(() -> {
            studentHomepage homepage = new studentHomepage();
            homepage.setVisible(true);
        });
    }
    
    public static void main(String[] args) { 
        SwingUtilities.invokeLater(() -> {
            studentLogin login = new studentLogin();
            login.setVisible(true);
        });
    }
}