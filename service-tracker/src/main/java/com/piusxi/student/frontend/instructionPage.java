package com.piusxi.student.frontend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class instructionPage extends JFrame {

    public instructionPage() {
        setSize(800, 600);
        setTitle("Service Tracker - Instructions");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        createMenuBar();
        instructions();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // File menu
        JMenu fileMenu = new JMenu("Home");
        JMenuItem backToDashboard = new JMenuItem("Dashboard");
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

    public void instructions() {
        
    }

    public static void main(String[] args) {
        instructionPage instructions = new instructionPage();
        instructions.setVisible(true);
    }
}
