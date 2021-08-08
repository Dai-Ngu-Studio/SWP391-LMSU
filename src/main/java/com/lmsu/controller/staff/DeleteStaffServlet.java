package com.lmsu.controller.staff;

import com.lmsu.users.UserDAO;
import com.lmsu.users.UserDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DeleteStaffServlet", value = "/DeleteStaffServlet")
public class DeleteStaffServlet extends HttpServlet {

    private static final String SHOW_STAFF_CONTROLLER = "ShowStaffServlet";
    private static final String SEARCH_STAFF_CONTROLLER = "SearchStaffServlet";
    static final Logger LOGGER = Logger.getLogger(DeleteStaffServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String url = SHOW_STAFF_CONTROLLER;
        String userID = request.getParameter("userPk");
        String searchValue = request.getParameter("txtSearchValue");

        try {
            UserDAO dao = new UserDAO();
            HttpSession session = request.getSession();
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            String loginUserID = "";

            if (loginUser != null) {
                loginUserID = loginUser.getId();
            }

            if (!loginUserID.equals(userID)) {
                boolean result = dao.deleteUser(userID);

                if (result) {
                    if (searchValue == null || searchValue.trim().isEmpty()) {
                        url = SHOW_STAFF_CONTROLLER;
                    } else {
                        url = SEARCH_STAFF_CONTROLLER;
                    }
                }
            } else {
                request.setAttribute("LOGGING_IN_USER", "You're now logging into this account. Cannot delete it!");
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("DeleteStaffServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("DeleteStaffServlet _ Naming: " + ex.getMessage());
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
