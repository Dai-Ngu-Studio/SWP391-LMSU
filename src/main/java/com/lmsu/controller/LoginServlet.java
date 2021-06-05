package com.lmsu.controller;

import com.lmsu.users.UserDAO;
import com.lmsu.users.UserDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {

    private static final String ERROR_PAGE = "login.html";
    private static final String INDEX_PAGE = "IndexServlet";
    private static final String DASHBOARD_PAGE = "dashboard.jsp";
    static final Logger LOGGER = Logger.getLogger(LoginServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        String isRemember = request.getParameter("cookie");
        UserDAO dao = new UserDAO();

        String url = ERROR_PAGE;

        try {
            String[] mail = email.split("@");

            if (mail[1].equalsIgnoreCase("fpt.edu.vn")) {
                UserDTO dto = dao.checkLogin(email, password);
                HttpSession session = request.getSession();

                if (isRemember != null) {
                    Cookie cookie = new Cookie(mail[0], password);
                    cookie.setMaxAge(60 * 60 * 24 * 15);
                    response.addCookie(cookie);
                }

                if (dto != null) {
                    session.setAttribute("LOGIN_USER", dto);
                    if (dto.getRoleID().equals("4")) {
                        url = INDEX_PAGE;
                    }
                    if (dto.getRoleID().equals("1") || dto.getRoleID().equals("2") || dto.getRoleID().equals("3")) {
                        url = DASHBOARD_PAGE;
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            log("LoginServlet _ SQL: " + e.getMessage());
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            log("LoginServlet _ Naming: " + e.getMessage());
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
