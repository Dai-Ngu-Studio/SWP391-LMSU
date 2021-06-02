package com.lmsu.accessgoogle.servlet;

import com.lmsu.accessgoogle.common.GooglePojo;
import com.lmsu.accessgoogle.common.GoogleUtils;
//import com.lmsu.controller.LoginServlet;
//import com.lmsu.user.UserDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

//@WebServlet(name = "LoginGoogleServlet", urlPatterns = {"/login-google"})
@WebServlet("/login-google")
public class LoginGoogleServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String ERROR_PAGE = "error.jsp";
    private static final String LOGIN_FAIL_PAGE = "loginfail.html";
    private static final String CREATE_ACCOUNT_GOOGLE_PAGE = "redirectCreateAccount.jsp";
    private static final String LOGIN_GOOGLE_CONTROLLER = "redirectLoginGoogle.jsp";
    static final Logger LOGGER = Logger.getLogger(LoginGoogleServlet.class);

    public LoginGoogleServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        String url = ERROR_PAGE;
        String url = "index.jsp";

        String code = request.getParameter("code");

//        UserDAO dao = new UserDAO();

        if (code == null || code.isEmpty()) {
            RequestDispatcher rd = request.getRequestDispatcher(LOGIN_FAIL_PAGE);
            rd.forward(request, response);
        } else {
            String accessToken = GoogleUtils.getToken(code);
            GooglePojo googlePojo = GoogleUtils.getUserInfo(accessToken);

            String email = googlePojo.getEmail();
            String fullname = googlePojo.getName();
            String password = googlePojo.getId();

            try {
                HttpSession sesion = request.getSession();
                sesion.setAttribute("USER_ID", email);
                sesion.setAttribute("FULLNAME", fullname);
                sesion.setAttribute("PASSWORD", password);
                sesion.setAttribute("IS_LOGIN_GOOGLE", true);

//                if (dao.checkDuplicate(email)) {
//                    url = LOGIN_GOOGLE_CONTROLLER;
//                } else {
//                    url = CREATE_ACCOUNT_GOOGLE_PAGE;
//                }
//            } catch (SQLException e) {
//                LOGGER.error(e.getMessage());
//                log("LoginGoogleServlet _ SQL: " + e.getMessage());
//            } catch (NamingException e) {
//                LOGGER.error(e.getMessage());
//                log("LoginGoogleServlet _ Naming: " + e.getMessage());
            } finally {
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
