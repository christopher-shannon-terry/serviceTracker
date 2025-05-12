package com.piusxi.admin.frontend;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import com.piusxi.admin.backend.generateReport;

public class allStudents extends JFrame {
    

    public allStudents() {

        createMenuBar();
    }

    public void createMenuBar() {
        JMenuBar navigationBar = new JMenuBar();

        JMenu home = new JMenu("Home");
        JMenuItem dashboard = new JMenuItem("Dashboard");
        home.add(dashboard);
        dashboard.addActionListener((ActionEvent e) -> {
            dispose();

            SwingUtilities.invokeLater(() -> {
                new adminHomepage().setVisible(true);
            });
        });


        JMenuItem exit = new JMenuItem("Exit");
        home.add(exit);
        exit.addActionListener((ActionEvent e) -> {
            dispose();
            
            System.exit(0);
        });

        JMenu studentsMenu = new JMenu("Students");
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
}
