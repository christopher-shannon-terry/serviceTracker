package com.piusxi.student.backend;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
* When submit button clicked in serviceReportingForm page
* Send email to whatever email entered in supervisor email field
* supervisorEmailField.getText() -> probably?
* Email should say something like 
* "Your email was entered on <student_name> service submission
* Please click yes or no to whether you were the supervisor or beneficiary of the service event"
* Email should contain a yes or no button that idrk how its gonna work but somehow communicate with the database and update the superviosr_confirmed column 
*/

/*
 * Add to database
 * supervisor_confirmed column
 * YES OR NO -> default NO
 * Update status in emailConfirmation.java file in student backend directory 
 */

public class confirmationEmail {
    
    public static boolean sendEmail(String recipient) {
        String smtpHost = "smtp.gmail.com";
        String smtpPort = "587";
        String senderEmail = "service@piusxi.org"; // will need to ask IT to make this for us
        String password = " "; // then give us the app password

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
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject("Pius Xi Service Tracker - Supervisor Confirmation");

            String emailContent =
                "<html><body>" +
                "<p>You are receiving this email because ";
        } 
        catch (MessagingException e) {

        }

        return true;
    }
}
