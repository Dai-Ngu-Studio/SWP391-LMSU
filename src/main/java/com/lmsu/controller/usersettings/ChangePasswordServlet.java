package com.lmsu.controller.usersettings;

import com.google.common.hash.Hashing;
import com.lmsu.users.UserDAO;
import com.lmsu.users.UserDTO;
import com.lmsu.utils.AppUtils;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ChangePasswordServlet", value = "/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(ChangePasswordServlet.class);
    private static final String RESULT_PAGE = "usersettings.jsp";
    private static final String ERROR_PAGE = "error.html";
    private final String ATTR_MEMBER_UPDATE_SETTING_SUCCESS = "MEMBER_UPDATE_SETTING_SUCCESS";
    private final String ATTR_MEMBER_UPDATE_SETTING_MESSAGE = "MEMBER_UPDATE_SETTING_MESSAGE";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pk = request.getParameter("pk");
        String currentPW = request.getParameter("txtCurrentPassword");
        String newPW = request.getParameter("txtNewPassword");
        String confirmPW = request.getParameter("txtConfirmPassword");
        Map<String, String> errors = new HashMap<>();
        String url = ERROR_PAGE;

        try {
            HttpSession session = request.getSession();
            UserDAO dao = new UserDAO();
            UserDTO dto = (UserDTO) session.getAttribute("LOGIN_USER");
            String passwordHashed = Hashing.sha256()
                    .hashString(newPW, StandardCharsets.UTF_8).toString();
            boolean result = dao.updatePassword(pk, passwordHashed);
            if (result) {
                dto.setPassword(passwordHashed);
                UserDTO updatedUser = dao.getUserByID(dto.getId());
                AppUtils.storeLoginedUser(session, updatedUser);
                request.setAttribute(ATTR_MEMBER_UPDATE_SETTING_SUCCESS, true);
                request.setAttribute(ATTR_MEMBER_UPDATE_SETTING_MESSAGE, "Password updated successfully.");
                url = RESULT_PAGE;
            }
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
