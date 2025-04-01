package com.piusxi.student.frontend;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class studentHomepage extends JFrame {
   
    public studentHomepage() {
        setTitle("Student Service Tracker");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void setStudentId() {
        /*
         * Implementation needed
         */
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            studentHomepage homepage = new studentHomepage();
            homepage.setVisible(true);
        });
    }
}