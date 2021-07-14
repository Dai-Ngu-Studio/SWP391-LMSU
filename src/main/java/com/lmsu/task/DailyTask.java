package com.lmsu.task;

import com.lmsu.users.UserDAO;
import com.lmsu.utils.EmailHelpers;
import org.apache.log4j.Logger;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.naming.NamingException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimerTask;

public class DailyTask extends TimerTask implements Serializable {

    private static final String EMAIL = "***REMOVED***";
    private static final String PASSWORD = "***REMOVED***";
    static final Logger LOGGER = Logger.getLogger(EmailHelpers.class);

    public void notifyTenDaysLeft() {
//        if (check có phải còn 10 ngày so với deadline không) {
        try {
            UserDAO dao = new UserDAO();

            Map<String, List<String>> map = dao.getUserWithBorrowedBooks();

            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                /**
                 * @user[0] id
                 * @user[1] name
                 * @user[2] email
                 */
                String[] user = entry.getKey().split(", ");

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

                String subject = "[INFORMATION AND LIBRARY CENTER]_NOTIFY DUE DATE TO RETURN BOOKS";

                String body = "Dear " + user[1] + "<br>"
                        + "<br>"
                        + "Information and Library center reminder to return books before 14/7/2021<br>"
                        + "<br>"
                        + String.join("<br>", entry.getValue()) + "<br>"
                        + "<br>"
                        + "Any mistake please reply this email.<br>"
                        + "<br>"
                        + "Thanks.<br>"
                        + "Sincerely,<br>"
                        + "Information and Library center";

                EmailHelpers.sendEmail(session, user[2], subject, body);
            }//end for each user
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            System.err.println("DailyTask _ SQL: " + e.getMessage());
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            System.err.println("DailyTask _ Naming: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage());
            System.err.println("DailyTask _ UnsupportedEncoding: " + e.getMessage());
        } catch (MessagingException e) {
            LOGGER.error(e.getMessage());
            System.err.println("DailyTask _ Messaging: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            System.err.println("DailyTask _ Exception: " + e.getMessage());
        }
//        }
    }

    @Override
    public void run() {
        notifyTenDaysLeft();
    }
}
