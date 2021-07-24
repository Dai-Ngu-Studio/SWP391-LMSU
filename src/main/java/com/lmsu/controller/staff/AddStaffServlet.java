package com.lmsu.controller.staff;

import com.google.common.hash.Hashing;
import com.lmsu.users.UserDAO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

@WebServlet(name = "AddStaffServlet", value = "/AddStaffServlet")
public class AddStaffServlet extends HttpServlet {

    private static final String SHOW_STAFF_CONTROLLER = "ShowStaffServlet";
    private static final String SEARCH_STAFF_CONTROLLER = "SearchStaffServlet";
    static final Logger LOGGER = Logger.getLogger(AddStaffServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String url = SHOW_STAFF_CONTROLLER;

        String searchValue = request.getParameter("txtSearchValue");
        String userID = request.getParameter("txtUserID");
        String userName = request.getParameter("txtUserName");
        String roleID = request.getParameter("txtRoleID");
        String semester = "0";
        String password = request.getParameter("txtPassword");
        String passwordHashed = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
        String email = request.getParameter("txtEmail");
        String phoneNumber = request.getParameter("txtPhoneNumber");
        String profilePicturePath = "images/default-user-icon.png";

        try {
            UserDAO dao = new UserDAO();
            boolean result = dao.checkUserExisted(userID);
            if (!result) {
                if (userID.substring(0, 3).equalsIgnoreCase("LIB") || userID.substring(0, 3).equalsIgnoreCase("MNG")) {
                    dao.addUser(userID, userName, roleID, passwordHashed, email, phoneNumber, semester, profilePicturePath,
                            false, false, false, false);
                } else {
                    request.setAttribute("WRONG_ID_FORMAT", "Wrong format for userID (The prefix must be LIB or MNG)");
                }
            } else {
                request.setAttribute("ADD_DUPLICATE", "User have existed");
            }
            if (!(searchValue == null || searchValue.trim().isEmpty())) {
                url = SEARCH_STAFF_CONTROLLER;
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("AddStaffServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("AddStaffServlet _ Naming: " + ex.getMessage());
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
