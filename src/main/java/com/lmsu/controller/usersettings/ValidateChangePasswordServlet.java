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

            if (!currentPW.equals("")){
                String currentPasswordHashed = Hashing.sha256()
                        .hashString(currentPW, StandardCharsets.UTF_8).toString();
                if (!currentPasswordHashed.equals(dto.getPassword())) {
                    errors.put("errorCurrentPassword", "inputCurrentPassword");
                }
            } else {
                errors.put("errorCurrentPassword", "inputCurrentPassword");
            }

            if (!(confirmPW.equals("") && newPW.equals(""))){
                if (!confirmPW.trim().equals(newPW.trim())) {
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
