package com.lmsu.utils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

public class EmailHelpers implements Serializable {

    private static final String EMAIL = "***REMOVED***";
    private static final String PASSWORD = "***REMOVED***";

    public static void sendAttachmentEmail(Session session, String toEmail, String subject,
                                           String body, String picturePath, String fileName) {
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("***REMOVED***", "LMSU"));

            msg.setReplyTo(InternetAddress.parse("***REMOVED***", false));

            msg.setSubject(subject, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            // Create the message body part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Fill the message
            messageBodyPart.setContent(body, "text/html");

            // Create a multipart message for attachment
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Second part is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = picturePath;
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            msg.setContent(multipart);

            // Send message
            Transport.send(msg);
            System.out.println("Email Sent To " + toEmail + " Successfully with attachment!!");
        } catch (UnsupportedEncodingException | MessagingException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Utility method to send simple email
     *
     * @param session
     * @param toEmail
     * @param subject
     * @param body
     */
    public void sendEmail(Session session, String toEmail, String subject, String body)
            throws UnsupportedEncodingException, MessagingException {
        MimeMessage msg = new MimeMessage(session);
        //set message headers
        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
        msg.addHeader("format", "flowed");
        msg.addHeader("Content-Transfer-Encoding", "8bit");

        msg.setFrom(new InternetAddress("swp.lmsu@gmail.com", "LMSU"));

        msg.setReplyTo(InternetAddress.parse("swp.lmsu@gmail.com", false));

        msg.setSubject(subject, "UTF-8");

        msg.setContent(body, "text/html");

        msg.setSentDate(new Date());

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
        Transport.send(msg);

        System.out.println("Email Sent To " + toEmail + " Successfully!\n");
    }

    public Session createEmailSession() throws UnsupportedEncodingException, MessagingException {
        System.out.println("SSLEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, PASSWORD);
            }
        };

        Session session = Session.getDefaultInstance(props, auth);
        System.out.println("Session created");
        if (session != null) {
            return session;
        }
        return null;
    }
}
