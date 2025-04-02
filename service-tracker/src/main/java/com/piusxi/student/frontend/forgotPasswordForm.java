package com.piusxi.student.frontend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

public class forgotPasswordForm extends JFrame {
    
    public forgotPasswordForm() {
        setTitle("Student Service Tracker");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        createMenuBar();
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

        // Service menu
        JMenu serviceMenu = new JMenu("Service");
        JMenuItem serviceForm = new JMenuItem("Submit Service");
        serviceMenu.add(serviceForm);

        serviceForm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitServiceForm serviceForm = new submitServiceForm();
                // serviceForm.setVisible(true);
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

        
        // Add menus to menu bar
        menuBar.add(fileMenu);
        menuBar.add(serviceMenu);
        menuBar.add(helpMenu);
        
        // Set the menu bar to the frame
        setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            forgotPasswordForm form = new forgotPasswordForm();
            form.setVisible(true);
        });
    }
}
