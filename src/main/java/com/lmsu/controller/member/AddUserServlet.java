package com.lmsu.controller.member;

import com.lmsu.users.UserDAO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AddUserServlet", value = "/AddUserServlet")
public class AddUserServlet extends HttpServlet {

    private static final String SHOW_USER_CONTROLLER = "ShowMemberServlet";
    private static final String SEARCH_USER_CONTROLLER = "SearchUserServlet";
    static final Logger LOGGER = Logger.getLogger(AddUserServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String url = SHOW_USER_CONTROLLER;

        String searchValue = request.getParameter("txtSearchValue");
        String userID = request.getParameter("txtUserID");
        String userName = request.getParameter("txtUserName");
        String roleID = "4";
        String semester = request.getParameter("txtSemester");
        String email = request.getParameter("txtEmail");
        String phoneNumber = request.getParameter("txtPhoneNumber");
        String profilePicturePath = "images/default-user-icon.png";

        try {
            UserDAO dao = new UserDAO();
            if (!dao.isDelete(userID) && !dao.isDelete(email)) {
                if (!dao.checkUserExisted(userID) && !dao.checkUserExisted(email)) {
                    dao.addUser(userID, userName, roleID, "", email, phoneNumber, semester, profilePicturePath,
                            false, false, false, false);
                } else {
                    request.setAttribute("ADD_DUPLICATE", "User have existed! Please check again userID or email.");
                }
            } else {
                request.setAttribute("DELETED_USER", "This user have been deleted before. If you want to add again, please undelete it!");
            }

            if (searchValue == null || searchValue.trim().isEmpty()) {
                url = SHOW_USER_CONTROLLER;
            } else {
                url = SEARCH_USER_CONTROLLER;
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("AddUserServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("AddUserServlet _ Naming: " + ex.getMessage());
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
