package classes.mailer;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class JavaEmail {

    Properties emailProperties;
    Session mailSession;
    MimeMessage emailMessage;


/*
    public static void main(String args[]) throws AddressException,
            MessagingException {

        JavaEmail javaEmail = new JavaEmail();
        String [] table = {"fatimazahranaor@gmail.com","valdesche03@gmail.com"};
        javaEmail.setMailServerProperties();
        javaEmail.createEmailMessage(table, "réponse au message", "Hello comment allez-vous ?");
        javaEmail.sendEmail();
    }
    */


    public void setMailServerProperties() {

        String emailPort = "587";//gmail's smtp port

        emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", emailPort);
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");

    }

    public void createEmailMessage(String toEmail[], String subject, String body) throws AddressException,
            MessagingException {
        String[] toEmails = toEmail;
        String emailSubject = subject;
        String emailBody = body;

        mailSession = Session.getDefaultInstance(emailProperties, null);
        emailMessage = new MimeMessage(mailSession);

        for (int i = 0; i < toEmails.length; i++) {
            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[i]));
        }

        emailMessage.setSubject(emailSubject);
        emailMessage.setContent(emailBody, "text/html");
    }

    public void sendEmail() throws AddressException, MessagingException {

        String emailHost = "smtp.gmail.com";
        String fromUser = "ingeekteam@gmail.com";//just the id alone without @gmail.com
        String fromUserEmailPassword = "Test2018";

        Transport transport = mailSession.getTransport("smtp");

        transport.connect(emailHost, fromUser, fromUserEmailPassword);
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
        System.out.println("Email sent successfully.");
    }
}



