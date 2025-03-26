package com.piusxi.student.frontend;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class createAccountForm extends JFrame {

    public createAccountForm() {
        setTitle("Create Account");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        form();
    }

    public void form() {

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            studentLogin login = new studentLogin();
            login.setVisible(true);
        });
    }
}
