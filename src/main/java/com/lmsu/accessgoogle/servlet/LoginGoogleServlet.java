package com.lmsu.accessgoogle.servlet;

import com.google.common.hash.Hashing;
import com.lmsu.accessgoogle.common.GooglePojo;
import com.lmsu.accessgoogle.common.GoogleUtils;
import com.lmsu.users.UserDAO;
import com.lmsu.users.UserDTO;
import com.lmsu.utils.AppUtils;
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
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

//@WebServlet(name = "LoginGoogleServlet", urlPatterns = {"/login-google"})
@WebServlet("/login-google")
public class LoginGoogleServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String ERROR_PAGE = "error.jsp";
    private static final String LOGIN_FAIL_PAGE = "login.html";
    private static final String INDEX_CONTROLLER = "IndexServlet";
    private static final String DASHBOARD_PAGE = "dashboard.jsp";
    static final Logger LOGGER = Logger.getLogger(LoginGoogleServlet.class);

    public LoginGoogleServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = ERROR_PAGE;

        String code = request.getParameter("code");

        if (code == null || code.isEmpty()) {
            RequestDispatcher rd = request.getRequestDispatcher(LOGIN_FAIL_PAGE);
            rd.forward(request, response);
        } else {
            String accessToken = GoogleUtils.getToken(code);
            GooglePojo googlePojo = GoogleUtils.getUserInfo(accessToken);

            String email = googlePojo.getEmail();
            String id = googlePojo.getId();
            String hashCode = "LMSU";
            String passwordHashed = Hashing.sha256()
                    .hashString(id + hashCode, StandardCharsets.UTF_8).toString();
            String profilePicture = googlePojo.getPicture();

            try {
                UserDAO dao = new UserDAO();
                HttpSession session = request.getSession();
                request.setAttribute("EMAIL", email);
                request.setAttribute("PASSWORD", passwordHashed);
                request.setAttribute("PROFILE_PICTURE", profilePicture);

                boolean isActive = dao.isActive(email);
                if (!isActive) {
                    dao.updateOnFirstLogin(email, passwordHashed, profilePicture);
                    UserDTO dto = dao.checkLogin(email, passwordHashed);
                    if (dto != null) {
                        session.setAttribute("LOGIN_USER", dto);
                        if (dto.getRoleID().equals("4")) {
                            url = INDEX_CONTROLLER;
                        }
                        if (dto.getRoleID().equals("1") || dto.getRoleID().equals("2") || dto.getRoleID().equals("3")) {
                            url = DASHBOARD_PAGE;
                        }
                    }
                } else {
                    UserDTO dto = dao.checkLogin(email, passwordHashed);
                    if (dto != null) {
//                        session.setAttribute("LOGIN_USER", dto);
                        AppUtils.storeLoginedUser(session, dto);
                        if (dto.getRoleID().equals("4")) {
                            url = INDEX_CONTROLLER;
                        }
                        if (dto.getRoleID().equals("1") || dto.getRoleID().equals("2") || dto.getRoleID().equals("3")) {
                            url = DASHBOARD_PAGE;
                        }
                    }
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                log("LoginGoogleServlet _ SQL: " + e.getMessage());
            } catch (NamingException e) {
                LOGGER.error(e.getMessage());
                log("LoginGoogleServlet _ Naming: " + e.getMessage());
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
