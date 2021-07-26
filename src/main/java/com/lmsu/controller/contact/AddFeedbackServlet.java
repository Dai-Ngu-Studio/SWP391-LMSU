package com.lmsu.controller.contact;

import com.lmsu.feedback.FeedbackDAO;
import com.lmsu.utils.EmailHelpers;
import com.lmsu.utils.ImageHelpers;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

@WebServlet(name = "AddFeedbackServlet", value = "/AddFeedbackServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class AddFeedbackServlet extends HttpServlet {

    private final String USER_CONTACT_CONTROLLER = "ShowUserContactServlet";
    private static final String EMAIL = "***REMOVED***";
    private static final String PASSWORD = "***REMOVED***";
    static final Logger LOGGER = Logger.getLogger(AddFeedbackServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("txtFullName");
        String email = request.getParameter("txtEmail");
        String phone = request.getParameter("txtPhone");
        String feedbackType = request.getParameter("typeOfFeedback");
        String feedbackMsg = request.getParameter("txtFeedbackMsg");

        String url = USER_CONTACT_CONTROLLER;

        try {
            HttpSession session = request.getSession();
            FeedbackDAO dao = new FeedbackDAO();
            EmailHelpers emailHelpers = new EmailHelpers();

            int feedbackID = dao.getFeedbackID();

            //Start to add img to server process
            String uploadPath = ImageHelpers.getPathFeedbackImgFolder(getServletContext().getRealPath(""));
            String fileName = "";
            for (Part part : request.getParts()) {
                fileName = part.getSubmittedFileName();
                if (!(fileName == null || fileName.trim().isEmpty())) {
                    fileName = "feedback-" + feedbackID + "." + FilenameUtils.getExtension(fileName);
                    part.write(uploadPath + fileName);
                    break;
                }
            }
            boolean result = dao.addFeedback(fullName, email, phone, feedbackType, fileName, feedbackMsg.trim(), false);
            if (result) {
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

                Session mailSession = Session.getDefaultInstance(props, auth);
                System.out.println("Session created");

                String subject = "[INFORMATION AND LIBRARY CENTER]_FEED BACK";

                String body = "Dear " + fullName + "<br>"
                        + "<br>"
                        + "Information and Library Center have receive your feedback with message:" + "<br>"
                        + feedbackMsg + "<br>"
                        + "<br>"
                        + "We will response soon.<br>"
                        + "<br>"
                        + "Thank you.<br>"
                        + "Sincerely,<br>"
                        + "Information and Library Center.";

                if (fileName == null) {
                    emailHelpers.sendEmail(mailSession, email, subject, body);
                } else {
                    emailHelpers.sendAttachmentEmail(mailSession, email, subject, body, uploadPath + fileName, fileName);
                }

                request.setAttribute("RECEIVED_MESSAGE", "Message have been sent! We will response soon");
                session.setAttribute("ALREADY_FEEDBACK", true);
                url = USER_CONTACT_CONTROLLER;
            } else {
                request.setAttribute("SEND_FAIL", "There was an error during processing. Please send again!");
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("AddFeedbackServlet _ SQL: " + ex.getMessage());
            request.setAttribute("SEND_FAIL", "There was an error during processing. Please send again!");
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("AddFeedbackServlet _ Naming: " + ex.getMessage());
            request.setAttribute("SEND_FAIL", "There was an error during processing. Please send again!");
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            log("AddFeedbackServlet _ Exception: " + ex.getMessage());
            request.setAttribute("SEND_FAIL", "There was an error during processing. Please send again!");
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
