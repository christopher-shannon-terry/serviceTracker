package com.piusxi.admin.frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
        exit.addActionListener((ActionEvent e) -> {
            dispose();
            
            System.exit(0);
        });

        JMenu studentsMenu = new JMenu("Students");
        JMenuItem allStudents = new JMenuItem("All");
        studentsMenu.add(allStudents);
        allStudents.addActionListener((ActionEvent e) -> {
            dispose();

            SwingUtilities.invokeLater(() -> {
                new allStudents().setVisible(true);
            });
        });

        JMenuItem freshmenStudents = new JMenuItem("Freshmen");
        studentsMenu.add(freshmenStudents);
        freshmenStudents.addActionListener((ActionEvent e) -> {
            dispose();
            
            SwingUtilities.invokeLater(() -> {
                new allFreshmen().setVisible(true);
            });
        });

        JMenuItem sophomoreStudents = new JMenuItem("Sophomores");
        studentsMenu.add(sophomoreStudents);
        sophomoreStudents.addActionListener((ActionEvent e) -> {
            dispose();
            
            SwingUtilities.invokeLater(() -> {
                new allSophomores().setVisible(true);
            });
        });
        
        JMenuItem juniorStudents = new JMenuItem("Juniors");
        studentsMenu.add(juniorStudents);
        juniorStudents.addActionListener((ActionEvent e) -> {
            dispose();
            
            SwingUtilities.invokeLater(() -> {
                new allJuniors().setVisible(true);
            });
        });

        JMenuItem seniorStudents = new JMenuItem("Seniors");
        studentsMenu.add(seniorStudents);
        seniorStudents.addActionListener((ActionEvent e) -> {
            dispose();
            
            SwingUtilities.invokeLater(() -> {
                new allSeniors().setVisible(true);
            });
        });

        JMenu reports = new JMenu("Reports");
        JMenuItem generateReports = new JMenuItem("Generate Report");
        reports.add(generateReports);
        generateReports.addActionListener((ActionEvent e) -> {
            try {
                String report = generateReport.generateFile(null);
            }
            catch (SQLException se) {
                se.printStackTrace();
            }
            catch (IOException ie) {
                ie.printStackTrace();
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
        viewSubmissionsButton.addActionListener((ActionEvent e) -> {
            dispose();

            SwingUtilities.invokeLater(() -> {
                new allStudents().setVisible(true);
            });
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
