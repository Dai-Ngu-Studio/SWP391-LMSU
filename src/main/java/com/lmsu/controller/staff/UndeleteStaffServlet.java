package com.lmsu.controller.staff;

import com.lmsu.users.UserDAO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UndeleteStaffServlet", value = "/UndeleteStaffServlet")
public class UndeleteStaffServlet extends HttpServlet {

    private static final String SHOW_STAFF_CONTROLLER = "ShowStaffServlet";
    private static final String SEARCH_STAFF_CONTROLLER = "SearchStaffServlet";
    static final Logger LOGGER = Logger.getLogger(UndeleteStaffServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String url = SHOW_STAFF_CONTROLLER;
        String userID = request.getParameter("userPk");
        String searchValue = request.getParameter("txtSearchValue");

        try {
            UserDAO dao = new UserDAO();

            boolean result = dao.undeleteUser(userID);

            if (result) {
                if (searchValue == null || searchValue.trim().isEmpty()) {
                    url = SHOW_STAFF_CONTROLLER;
                } else {
                    url = SEARCH_STAFF_CONTROLLER;
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("UndeleteStaffServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("UndeleteStaffServlet _ Naming: " + ex.getMessage());
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
