package com.piusxi.student.frontend;

import java.awt.BorderLayout;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class studentHomepage extends JFrame {

    private String studentId;
   
    public studentHomepage() {
        setTitle("Student Service Tracker");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        createMenuBar();

        JPanel mainPanel = new JPanel(new BorderLayout());

        /* JPanel sidePanel = createSidePanel();
        mainPanel.add(sidePanel, BorderLayout.WEST);

        JPanel centerPanel = createCenterPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER); */

        add(mainPanel);

    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
        // updateDashboard();
    }

    private void updateDashboard() {
        if (studentId == null) return;

        Connection connection = null;
        try {
            String studentInfoQuery = "SELECT grad_year";
        } 
        // catch (SQLException se) {
            // System.out.println("Error: " + se.getMessage());
        // }
        finally {
            System.out.println();
        }
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Home"); // on click return to homepage
        fileMenu.addSeparator();
        fileMenu.add(new JMenuItem("Exit")); // need button actionListener for exit to close the app

        JMenu serviceMenu = new JMenu("Service");
        serviceMenu.add(new JMenuItem("Submit Service")); // listener to take to service form page

        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(new JMenuItem("Instructions"));
        helpMenu.add(new JMenuItem("Questions"));
    }

    /*private JPanel createSidePanel() {


        // return sidePanel;
    }

    private JPanel createCenterPanel() {


        // return centerPanel;
    } */

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            studentHomepage homepage = new studentHomepage();
            homepage.setVisible(true);
        });
    }
}