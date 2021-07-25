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

@WebServlet(name = "ShowUserContactServlet", value = "/ShowUserContactServlet")
public class ShowUserContactServlet extends HttpServlet {

    private final String CONTACT_PAGE = "contact.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = CONTACT_PAGE;

        try {
            HttpSession session = request.getSession();
            session.getAttribute("ALREADY_FEEDBACK");
            url = CONTACT_PAGE;
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
