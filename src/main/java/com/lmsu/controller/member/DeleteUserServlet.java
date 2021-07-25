package com.lmsu.controller.member;

import com.lmsu.users.UserDAO;
import com.lmsu.users.UserDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DeleteUserServlet", value = "/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {

    private static final String SHOW_MEMBER_CONTROLLER = "ShowMemberServlet";
    private static final String SEARCH_USER_CONTROLLER = "SearchUserServlet";
    static final Logger LOGGER = Logger.getLogger(DeleteUserServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String url = SHOW_MEMBER_CONTROLLER;
        String userID = request.getParameter("userPk");
        String searchValue = request.getParameter("txtSearchValue");

        try {
            UserDAO dao = new UserDAO();

            boolean result = dao.deleteUser(userID);

            if (result) {
                if (searchValue == null || searchValue.trim().isEmpty()) {
                    url = SHOW_MEMBER_CONTROLLER;
                } else {
                    url = SEARCH_USER_CONTROLLER;
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("DeleteUserServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("DeleteUserServlet _ Naming: " + ex.getMessage());
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
