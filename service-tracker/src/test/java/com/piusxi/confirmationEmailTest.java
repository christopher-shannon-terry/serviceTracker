package com.piusxi;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
 * Add to database
 * supervisor_confirmed column
 * YES OR NO -> default NO
 * Update status in emailConfirmation.java file in student backend directory 
 */

public class confirmationEmailTest {
    
    public static boolean sendTestEmail(String recipientEmail) {
        String smtpHost = "smtp.gmail.com";
        String smtpPort = "587";
        String senderEmail = "mitchellalan1999@gmail.com";
        String password = "wsvu bahf kqbq xmjo";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Service Tracker Test Email");

            String htmlContent = 
                "<html><body>" +
                "<h2>Service Tracker Email Test</h2>" +
                "<p>This is a test email from the Pius XI Service Tracker application.</p>" +
                "<p>If you're receiving this, the email functionality is working correctly!</p>" +
                "</body></html>";
            
            message.setContent(htmlContent, "text/html");
            Transport.send(message);
            System.out.printf("Test email sent successfully to: %s\n", recipientEmail);

            return true;
        }
        catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
            e.printStackTrace();
            
            return false;
        }
    }

    public static void main(String[] args) {
        String testEmail = "alan.mitchell@piusxi.org";

        System.out.println("Starting email test...");
        boolean result = sendTestEmail(testEmail);
        
        if (result) {
            System.out.println("Email test completed successfully!");
        } 
        else {
            System.out.println("Email test failed. See error messages above.");
            System.out.println("Consider trying an alternative email service like Outlook or a mail testing service.");
        }
    }
}
