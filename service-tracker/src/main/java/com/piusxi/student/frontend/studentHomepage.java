package com.piusxi.student.frontend;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class studentHomepage extends JFrame {
   
    public studentHomepage() {
        setSize(500, 500);
        setLayout(new GridLayout(5, 1));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        app();
    }

    public void app() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem submitService = new JMenuItem("Submit Service");
        menu.add(submitService);
        add(menuBar);
    }

    public static void main(String[] args) {
        studentHomepage homepage = new studentHomepage();
        homepage.setVisible(true);
    }
}
