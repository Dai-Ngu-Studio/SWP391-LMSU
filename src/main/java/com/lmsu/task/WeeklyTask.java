package com.lmsu.task;

import com.lmsu.books.BookDAO;
import com.lmsu.books.BookDTO;
import com.lmsu.users.UserDAO;
import com.lmsu.users.UserDTO;
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
import java.util.Properties;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class WeeklyTask extends TimerTask implements Serializable {

    private static final String EMAIL = "***REMOVED***";
    private static final String PASSWORD = "***REMOVED***";
    static final Logger LOGGER = Logger.getLogger(WeeklyTask.class);

    public void notifyNewArrivalBooks() {
        try {
            System.out.println("===================== Notify New Arrival Books ===================");
            UserDAO userDAO = new UserDAO();
            EmailHelpers emailHelpers = new EmailHelpers();
            BookDAO bookDAO = new BookDAO();

            userDAO.getListUserNotifyNewArrivalBook();
            List<UserDTO> listUser = userDAO.getListAccount();

            List<BookDTO> listNewArrivalBooks = bookDAO.getNewArrivalBooks();
            String listBook = String.join("<br>", listNewArrivalBooks.stream()
                    .map(BookDTO::getTitle)
                    .collect(Collectors.joining("<br>")));

            if (listUser != null) {
                if (listBook != null) {
                    for (UserDTO user : listUser) {
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

                        String subject = "[INFORMATION AND LIBRARY CENTER]_NOTIFY NEW ARRIVAL BOOKS";

                        String body = "Dear " + user.getName() + "<br>" +
                                "<br>" +
                                "Information and Library center would like to inform " + user.getName() + " new arrival books<br>" +
                                listBook + "<br>" +
                                "<br>" +
                                "Have a nice week!<br>" +
                                "Sincerely,<br>" +
                                "Information and Library center";

                        emailHelpers.sendEmail(session, user.getEmail(), subject, body);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            System.err.println("WeeklyTask _ notifyNewArrivalBooks _ SQL: " + e.getMessage());
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            System.err.println("WeeklyTask _ notifyNewArrivalBooks _ Naming: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage());
            System.err.println("WeeklyTask _ UnsupportedEncoding: " + e.getMessage());
        } catch (MessagingException e) {
            LOGGER.error(e.getMessage());
            System.err.println("WeeklyTask _ Messaging: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            System.err.println("WeeklyTask _ notifyNewArrivalBooks _ Exception: " + e.getMessage());
        }
    }

    public void notifyHighestRatedBookOfTheWeek() {
        try {
            System.out.println("===================== Notify Highest Rated Books ===================");
            UserDAO userDAO = new UserDAO();
            EmailHelpers emailHelpers = new EmailHelpers();
            BookDAO bookDAO = new BookDAO();

            userDAO.getListUserNotifyHighestRatedBook();
            List<UserDTO> listUser = userDAO.getListAccount();

            List<BookDTO> listMostHighestRatedBooks = bookDAO.getMostFavoriteBooks();
            StringBuilder listBook = new StringBuilder();
            int count = 0;
            for (BookDTO book : listMostHighestRatedBooks) {
                String getBook = "Top " + ++count + ": " + book.getTitle() + " with " + book.getAvgRating() + " stars<br>";
                listBook.append(getBook);
            }

            if (listUser != null) {
                if (listMostHighestRatedBooks != null) {
                    for (UserDTO user : listUser) {
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

                        String subject = "[INFORMATION AND LIBRARY CENTER]_NOTIFY THE HIGHEST RATED BOOKS OF WEEK";

                        String body = "Dear " + user.getName() + "<br>" +
                                "<br>" +
                                "Information and Library center would like to inform " + user.getName() + " the highest rated books of last week<br>" +
                                "<br>" +
                                listBook +
                                "<br>" +
                                "Have a nice week!<br>" +
                                "Sincerely,<br>" +
                                "Information and Library center";

                        emailHelpers.sendEmail(session, user.getEmail(), subject, body);
                    }//end for each user
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            System.err.println("WeeklyTask _ notifyHighestRatedBookOfTheWeek _ SQL: " + e.getMessage());
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            System.err.println("WeeklyTask _ notifyHighestRatedBookOfTheWeek _ Naming: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage());
            System.err.println("WeeklyTask _ UnsupportedEncoding: " + e.getMessage());
        } catch (MessagingException e) {
            LOGGER.error(e.getMessage());
            System.err.println("WeeklyTask _ Messaging: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            System.err.println("WeeklyTask _ notifyHighestRatedBookOfTheWeek _ Exception: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        notifyNewArrivalBooks();
        notifyHighestRatedBookOfTheWeek();
    }
}
