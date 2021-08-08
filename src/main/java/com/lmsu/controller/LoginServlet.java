package com.lmsu.controller;

import com.google.common.hash.Hashing;
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

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {

    private static final String LOGIN_FAIL = "login.jsp";
    private static final String INDEX_PAGE = "IndexServlet";
    private static final String DASHBOARD_PAGE = "ShowDashboardServlet";
    static final Logger LOGGER = Logger.getLogger(LoginServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String passwordHashed = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
        UserDAO dao = new UserDAO();

        String url = LOGIN_FAIL;

        try {
            if (!dao.isDelete(email)) {
                UserDTO dto = dao.checkLogin(email, passwordHashed);
                HttpSession session = request.getSession();

                if (dto != null) {
                    dao.updateActiveStatus(email);
                    AppUtils.storeLoginedUser(session, dto);
                    if (dto.getRoleID().equals("4")) {
                        url = INDEX_PAGE;
                    }
                    if (dto.getRoleID().equals("1") || dto.getRoleID().equals("2") || dto.getRoleID().equals("3")) {
                        url = DASHBOARD_PAGE;
                    }
                } else {
                    request.setAttribute("WRONG_USER_LOGIN", "Wrong user name or password!");
                }
            } else {
                request.setAttribute("DELETED_ACCOUNT",
                        "Your account have been removed from system. Any mistake please send us feedback.");
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
