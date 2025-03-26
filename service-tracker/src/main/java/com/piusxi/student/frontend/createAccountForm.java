package com.piusxi.student.frontend;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class createAccountForm extends JFrame {

    public createAccountForm() {
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            studentLogin login = new studentLogin();
            login.setVisible(true);
        });
    }
}
