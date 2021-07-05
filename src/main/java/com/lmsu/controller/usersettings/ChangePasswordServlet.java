package com.lmsu.controller.usersettings;

import com.lmsu.users.UserDAO;
import com.lmsu.users.UserDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ChangePasswordServlet", value = "/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {

    private static final String RESULT_PAGE = "usersettings.jsp";

    static final Logger LOGGER = Logger.getLogger(ChangePasswordServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pk = request.getParameter("pk");
        String currentPW = request.getParameter("txtCurrentPassword");
        String newPW = request.getParameter("txtNewPassword");
        String confirmPW = request.getParameter("txtConfirmPassword");

        String url = RESULT_PAGE;

        try {
            HttpSession session = request.getSession();
            UserDAO dao = new UserDAO();
            UserDTO dto = (UserDTO) session.getAttribute("LOGIN_USER");

            if (currentPW.equals(dto.getPassword())) {
                if (confirmPW.trim().equals(newPW.trim())) {
                    boolean result = dao.updatePassword(pk, newPW);
                    if (result) {
                        dto.setPassword(newPW);
                    }
                } else {
                    request.setAttribute("WRONG_CONFIRM_PASSWORD", "confirm password not match!");
                }
            } else {
                request.setAttribute("WRONG_PASSWORD", "password not match!");
            }
            url = RESULT_PAGE;
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("ChangePasswordServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("ChangePasswordServlet _ Naming: " + ex.getMessage());
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
