package com.lmsu.controller;

import com.google.common.hash.Hashing;
import com.lmsu.users.UserDAO;
import com.lmsu.users.UserDTO;
import com.lmsu.utils.AppUtils;
import com.lmsu.utils.RandomStringHelpers;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {

    private static final String ERROR_PAGE = "login.html";
    private static final String LOGIN_FAIL = "loginfail.html";
    private static final String INDEX_PAGE = "IndexServlet";
    private static final String DASHBOARD_PAGE = "dashboard.jsp";
    static final Logger LOGGER = Logger.getLogger(LoginServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        boolean isRememberMe = "true".equals(request.getParameter("cookie"));
        UserDAO dao = new UserDAO();
        RandomStringHelpers random = new RandomStringHelpers();

        String url = ERROR_PAGE;

        try {
            String passwordHashed = Hashing.sha256()
                    .hashString(password, StandardCharsets.UTF_8).toString();

            UserDTO dto = dao.checkLogin(email, passwordHashed);
            HttpSession session = request.getSession();

//            if (isRememberMe) {
//                String selector = random.generatingRandomString();
//                Cookie cookieSelector = new Cookie("selector", selector);
//                cookieSelector.setMaxAge(60 * 60 * 24 * 7); //7 days
//                response.addCookie(cookieSelector);
//
//                String validator = random.generatingRandomString();
//                Cookie cookieValidator = new Cookie("validator", validator);
//                cookieValidator.setMaxAge(60 * 60 * 24 * 7); //7 days
//                response.addCookie(cookieValidator);
//            }

            if (dto != null) {
                session.setAttribute("LOGIN_USER", dto);
                AppUtils.storeLoginedUser(session, dto);
                if (dto.getRoleID().equals("4")) {
                    url = INDEX_PAGE;
                }
                if (dto.getRoleID().equals("1") || dto.getRoleID().equals("2") || dto.getRoleID().equals("3")) {
                    url = DASHBOARD_PAGE;
                }
            } else {
                url = LOGIN_FAIL;
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
