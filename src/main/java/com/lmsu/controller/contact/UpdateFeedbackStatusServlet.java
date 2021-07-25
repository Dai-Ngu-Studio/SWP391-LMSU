package com.lmsu.controller.contact;

import com.lmsu.feedback.FeedbackDAO;
import com.lmsu.feedback.FeedbackDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "UpdateFeedbackStatusServlet", value = "/UpdateFeedbackStatusServlet")
public class UpdateFeedbackStatusServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(ShowFeedbackServlet.class);
    private final String FEEDBACK_MANAGEMENT_CONTROLLER = "ShowFeedbackServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String pk = request.getParameter("feedbackID");

        String url = FEEDBACK_MANAGEMENT_CONTROLLER;
        try {

            FeedbackDAO dao = new FeedbackDAO();
            boolean result = dao.updateFeedbackStatus(Integer.parseInt(pk), true);
            if (result){
                url = FEEDBACK_MANAGEMENT_CONTROLLER;
            }

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            log("UpdateFeedbackStatusServlet _ SQL: " + e.getMessage());
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            log("UpdateFeedbackStatusServlet _ Naming: " + e.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
