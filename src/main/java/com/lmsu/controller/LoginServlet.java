package com.lmsu.controller;

import com.lmsu.users.UserDAO;
import com.lmsu.users.UserDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {

    public static final String ERROR_PAGE = "login.html";
    public static final String INDEX_PAGE = "index.jsp";
    public static final String DASHBOARD_PAGE = "dashboard.jsp";
    static final Logger LOGGER = Logger.getLogger(LoginServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        String rememberMe = request.getParameter("cookie");
        UserDAO dao = new UserDAO();

        String url = ERROR_PAGE;

        try {
            UserDTO dto = dao.checkLogin(email, password);
            HttpSession session = request.getSession();

            //get param của checkbox, nếu check thì addCookie

            if (dto != null) {
                session.setAttribute("LOGIN_USER", dto);
                if (dto.getRoleID().equals("STU") || dto.getRoleID().equals("LEC")) {
                    url = INDEX_PAGE;
                }
                if (dto.getRoleID().equals("ADM") || dto.getRoleID().equals("MAG") || dto.getRoleID().equals("LIB")) {
                    url = DASHBOARD_PAGE;
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
