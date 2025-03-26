package com.piusxi.student.frontend;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class studentLogin extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createAccountButton;
    
    public studentLogin() {
        setTitle("Pius XI Service Tracker - Login");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        loginPage();
    }

    public void loginPage() {
        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Pius XI Service Hour Tracker", SwingConstants.CENTER);
        main.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(2, 2, 5, 10));

        JLabel username = new JLabel("Usrename: ");
        usernameField = new JTextField(20);
        form.add(username);
        form.add(usernameField);

        JLabel password = new JLabel("Password: ");
        passwordField = new JPasswordField(20);
        form.add(password);
        form.add(passwordField);

        main.add(form, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 0, 10));

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // login();
            }
        });
    }
}
