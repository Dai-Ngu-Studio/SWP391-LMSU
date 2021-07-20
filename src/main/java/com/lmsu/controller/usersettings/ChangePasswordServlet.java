package com.lmsu.controller.usersettings;

import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.lmsu.users.UserDAO;
import com.lmsu.users.UserDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ChangePasswordServlet", value = "/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {

    private static final String RESULT_PAGE = "usersettings.jsp";
    private static final String ERROR_PAGE = "error.html";

    static final Logger LOGGER = Logger.getLogger(ChangePasswordServlet.class);

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
//
//            String currentPasswordHashed = Hashing.sha256()
//                    .hashString(currentPW, StandardCharsets.UTF_8).toString();
//            if (currentPasswordHashed.equals(dto.getPassword())) {
//                if (confirmPW.trim().equals(newPW.trim())) {
//                    String passwordHashed = Hashing.sha256()
//                            .hashString(newPW, StandardCharsets.UTF_8).toString();
//                    boolean result = dao.updatePassword(pk, passwordHashed);
//                    if (result) {
//                        dto.setPassword(passwordHashed);
//                        url = RESULT_PAGE;
//                    }
//                } else {
//                    errors.put("errorConfirmPassword", "inputConfirmPassword");
//                }
//            } else {
//                errors.put("errorCurrentPassword", "inputCurrentPassword");
//            }
            String passwordHashed = Hashing.sha256()
                            .hashString(newPW, StandardCharsets.UTF_8).toString();
            boolean result = dao.updatePassword(pk, passwordHashed);
            if (result) {
                dto.setPassword(passwordHashed);
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
