package myApp;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {
    
    public static boolean sendOtpEmail(String toEmail, String otp, String firstName) {
        // Define email host and port (this example uses Gmail)
        String host = "smtp.gmail.com";
        String port = "587"; // TLS port for Gmail
        String fromEmail = "godzdemonz05@gmail.com"; // Change this to your email
        String fromPassword = "apwg zakm dpdq ngze"; // Change this to your email password
        
        // Setup properties for the mail session
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Create a new session
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, fromPassword);
            }
        });

        // Prepare the email message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Password Reset OTP");

            // Email content
            String emailContent = "Hello " + firstName + ",\n\n"
                                + "Your OTP for password reset is: " + otp + "\n\n"
                                
                                +"We received a request to reset your password for the Task Management System. " +
                                "Please use the following One-Time Password (OTP) to proceed with resetting your password:\n\n" +
                                "OTP: " + otp + "\n\n" +
                                "This OTP will expire in 10 minutes. If you did not request a password reset, please ignore this email.\n\n" +
                                "Best regards,\n" +
                                "Task Management System Support Team";

            message.setText(emailContent);

            // Send the email
            Transport.send(message);
            System.out.println("OTP sent successfully.");
            return true; // Email sent successfully
        } catch (MessagingException e) {
            e.printStackTrace();
            return false; // Email sending failed
        }
    }
}
