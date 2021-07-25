package com.lmsu.controller.contact;

import com.lmsu.feedback.FeedbackDAO;
import com.lmsu.utils.ImageHelpers;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AddFeedbackServlet", value = "/AddFeedbackServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class AddFeedbackServlet extends HttpServlet {

    private final String CONTACT_PAGE = "contact.jsp";
    static final Logger LOGGER = Logger.getLogger(AddFeedbackServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("txtFullName");
        String email = request.getParameter("txtEmail");
        String phone = request.getParameter("txtPhone");
        String feedbackType = request.getParameter("typeOfFeedback");
        String feedbackMsg = request.getParameter("txtFeedbackMsg");

        String url = CONTACT_PAGE;

        try {
            FeedbackDAO dao = new FeedbackDAO();
            int feedbackID = 0;
            do {
                feedbackID++;
            } while (dao.checkFeedbackId(feedbackID));

            //Start to add img to server process
            String uploadPath = ImageHelpers.getPathImgFolder(getServletContext().getRealPath(""));
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
                url = CONTACT_PAGE;
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("ContactServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("ContactServlet _ Naming: " + ex.getMessage());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            log("ContactServlet _ Exception: " + ex.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
