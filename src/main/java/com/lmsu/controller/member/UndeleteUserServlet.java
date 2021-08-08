package com.lmsu.controller.member;

import com.lmsu.controller.staff.UndeleteStaffServlet;
import com.lmsu.users.UserDAO;
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

@WebServlet(name = "UndeleteUserServlet", value = "/UndeleteUserServlet")
public class UndeleteUserServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(UndeleteStaffServlet.class);
    private static final String SHOW_MEMBER_CONTROLLER = "ShowMemberServlet";
    private static final String SEARCH_MEMBER_CONTROLLER = "SearchMemberServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String url = SHOW_MEMBER_CONTROLLER;
        String userID = request.getParameter("userPk");
        String searchValue = request.getParameter("txtSearchValue");

        try {
            UserDAO dao = new UserDAO();

            boolean result = dao.undeleteUser(userID);

            if (result) {
                if (searchValue == null || searchValue.trim().isEmpty()) {
                    url = SHOW_MEMBER_CONTROLLER;
                } else {
                    url = SEARCH_MEMBER_CONTROLLER;
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
