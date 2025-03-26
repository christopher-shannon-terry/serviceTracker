package com.piusxi.student.frontend;

import javax.swing.JFrame;

public class verifyEmailPage extends JFrame {

    public verifyEmailPage() {
        setTitle("Pius XI Service Traker - Email Verification");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        verify();
    }

    public void verify() {
        
        /*
         * Display -> An email with a verification code was sent to 'user@piusxi.org'
            * Get user from the email text field in createAccountForm.java 
         * Prompt for entering verification code
         * Authenticate code based on the value in the database (to be implemented)
         * Update verified state in database
         */

    }


    public static void main(String[] args) {

    }
}
