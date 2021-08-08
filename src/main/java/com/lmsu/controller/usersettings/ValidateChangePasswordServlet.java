package com.lmsu.controller.usersettings;

import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.lmsu.users.UserDTO;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ValidateChangePasswordServlet", value = "/ValidateChangePasswordServlet")
public class ValidateChangePasswordServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(ValidateChangePasswordServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentPW = request.getParameter("txtCurrentPassword");
        String newPW = request.getParameter("txtNewPassword");
        String confirmPW = request.getParameter("txtConfirmPassword");
        Map<String, String> errors = new HashMap<>();

        try {
            HttpSession session = request.getSession();
            UserDTO dto = (UserDTO) session.getAttribute("LOGIN_USER");

            if (!currentPW.equals("")) {
                String currentPasswordHashed = Hashing.sha256()
                        .hashString(currentPW, StandardCharsets.UTF_8).toString();
                if (!currentPasswordHashed.equals(dto.getPassword())) {
                    errors.put("errorCurrentPassword", "inputCurrentPassword");
                }
            } else {
                errors.put("errorCurrentPassword", "inputCurrentPassword");
            }

            if (!newPW.matches("^[a-z0-9A-Z]{1,50}$")) {
                errors.put("errorPatternPassword", "inputPasswordPattern");
            }

            if (confirmPW != null) {
                if (confirmPW.matches("^[a-z0-9A-Z]{1,50}$")) {
                    if (!confirmPW.trim().equals(newPW.trim())) {
                        errors.put("errorConfirmPassword", "inputConfirmPassword");
                    }
                } else {
                    errors.put("errorConfirmPassword", "inputConfirmPassword");
                }
            } else {
                errors.put("errorConfirmPassword", "inputConfirmPassword");
            }

        } finally {
            String json = new Gson().toJson(errors);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
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
