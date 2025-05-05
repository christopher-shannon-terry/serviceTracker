package com.piusxi.admin.frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.piusxi.admin.backend.generateReport;

public class adminHomepage extends JFrame {
    
    public adminHomepage() {
        setTitle("Service Tracker - Administration");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        createMenuBar();

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel sidePanel = createSidePanel();
        mainPanel.add(sidePanel, BorderLayout.WEST);
        
        add(mainPanel);
    }

    public void createMenuBar() {
        JMenuBar navigationBar = new JMenuBar();

        JMenu home = new JMenu("Home");
        JMenuItem exit = new JMenuItem("Exit");
        home.add(exit);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JMenu studentsMenu = new JMenu("Students");
        JMenuItem allStudents = new JMenuItem("All");
        studentsMenu.add(allStudents);
        allStudents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* 
                 * Will go to a new page
                 * Shows their name and grade probably and ability to click on them 
                 * maybe sorted alphabetically or by grade
                 * will have a search bar to search for name in
                 * Clicking on them will take them to page
                 * Where it shows their submissions or something (will come back to it) 
                 */
            }
        });

        JMenuItem freshmenStudents = new JMenuItem("Freshmen");
        studentsMenu.add(freshmenStudents);
        freshmenStudents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* 
                 * Will go to a new page
                 * Shows all freshmen with their name probably and ability to click on them
                 * maybe sorted alphabetically or by grade
                 * Will have a search bar to search for name of specific freshmen
                 * Clicking on them will take them to page
                 * Where it shows their submissions or something (will worry about that later) 
                 */
            }
        });

        JMenuItem sophomoreStudents = new JMenuItem("Sophomores");
        studentsMenu.add(sophomoreStudents);
        sophomoreStudents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* 
                 * Will go to a new page
                 * Shows all sophomores with their name probably and ability to click on them
                 * maybe sorted alphabetically or by grade
                 * Will have a search bar to search for name of specific sophomore
                 * Clicking on them will take them to page
                 * Where it shows their submissions or something (will worry about that later) 
                 */
            }
        });
        
        JMenuItem juniorStudents = new JMenuItem("Juniors");
        studentsMenu.add(juniorStudents);
        juniorStudents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* 
                 * Will go to a new page
                 * Shows all juniors with their name probably and ability to click on them
                 * maybe sorted alphabetically or by grade
                 * Will have a search bar to search for name of specific juniors
                 * Clicking on them will take them to page
                 * Where it shows their submissions or something (will worry about that later) 
                 */
            }
        });

        JMenuItem seniorStudents = new JMenuItem("Seniors");
        studentsMenu.add(seniorStudents);
        seniorStudents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* 
                 * Will go to a new page
                 * Shows all seniors with their name probably and ability to click on them
                 * maybe sorted alphabetically or by grade
                 * Will have a search bar to search for name of specific seniors
                 * Clicking on them will take them to page
                 * Where it shows their submissions or something (will worry about that later) 
                 */
            }
        });

        JMenu reports = new JMenu("Reports");
        JMenuItem generateReports = new JMenuItem("Generate Report");
        reports.add(generateReports);
        generateReports.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String report = generateReport.generateFile(null);
                }
                catch (SQLException se) {
                    se.printStackTrace();
                }
                catch (IOException ie) {
                    ie.printStackTrace();
                }
            }
        });

        navigationBar.add(home);
        navigationBar.add(studentsMenu);
        navigationBar.add(reports);

        setJMenuBar(navigationBar);
    }

    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setPreferredSize(new Dimension(200, 0));
        sidePanel.setBorder(BorderFactory.createEtchedBorder());
        sidePanel.setBackground(new Color(230, 230, 250));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(220, 220, 240));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Administrator User"));
        infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.setMaximumSize(new Dimension(190, 150));

        JLabel nameLabel = new JLabel("");

        JButton viewSubmissionsButton = new JButton("View Submissions");
        viewSubmissionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                 * Just redirects to the same page the all dropdown option in the students navBar does
                 */
            }
        });

        
        
        viewSubmissionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidePanel.add(viewSubmissionsButton);

        return sidePanel;
    }

    public static void main(String[] args) {
        adminHomepage adminHomepage = new adminHomepage();
        adminHomepage.setVisible(true);
    }
}
