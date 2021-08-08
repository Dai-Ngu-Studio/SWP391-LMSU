package com.lmsu.controller.contact;

import com.lmsu.feedback.FeedbackDAO;
import com.lmsu.feedback.FeedbackDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ShowFeedbackServlet", value = "/ShowFeedbackServlet")
public class ShowFeedbackServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(ShowFeedbackServlet.class);
    private final String FEEDBACK_MANAGEMENT_PAGE = "feedbackmanagement.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String url = FEEDBACK_MANAGEMENT_PAGE;
        try {
            List<FeedbackDTO> searchResultReceived = (List<FeedbackDTO>) request.getAttribute("SEARCH_RESULT");
            if (searchResultReceived != null) {
                request.setAttribute("FEEDBACK_LIST", searchResultReceived);
            } else {
                FeedbackDAO dao = new FeedbackDAO();
                dao.viewFeedbackList();
                List<FeedbackDTO> result = dao.getFeedbackList();
                request.setAttribute("FEEDBACK_LIST", result);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            log("ShowFeedbackServlet _ SQL: " + e.getMessage());
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            log("ShowFeedbackServlet _ Naming: " + e.getMessage());
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
