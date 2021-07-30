package com.lmsu.controller.usersettings;

import com.lmsu.users.UserDAO;
import com.lmsu.users.UserDTO;
import com.lmsu.utils.AppUtils;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ChangePhoneServlet", value = "/ChangePhoneServlet")
public class ChangePhoneServlet extends HttpServlet {

    private static final String RESULT_PAGE = "usersettings.jsp";
    static final Logger LOGGER = Logger.getLogger(ChangePasswordServlet.class);

    private final String ATTR_LOGIN_USER = "LOGIN_USER";
    private final String ATTR_MEMBER_UPDATE_SETTING_SUCCESS = "MEMBER_UPDATE_SETTING_SUCCESS";
    private final String ATTR_MEMBER_UPDATE_SETTING_MESSAGE = "MEMBER_UPDATE_SETTING_MESSAGE";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pk = request.getParameter("pk");
        String phone = request.getParameter("txtPhone");
        HttpSession session = request.getSession();

        String url = RESULT_PAGE;
        try {
            UserDAO dao = new UserDAO();
            if (session != null) {
                UserDTO dto = (UserDTO) session.getAttribute(ATTR_LOGIN_USER);
                if (dto != null) {
                    if (phone.trim().length() > 0) {
                        boolean result = dao.updatePhone(pk, phone);
                        if (result) {
                            dto.setPhoneNumber(phone);
                            UserDTO updatedUser = dao.getUserByID(dto.getId());
                            AppUtils.storeLoginedUser(session, updatedUser);
                            request.setAttribute(ATTR_MEMBER_UPDATE_SETTING_SUCCESS, true);
                            request.setAttribute(ATTR_MEMBER_UPDATE_SETTING_MESSAGE, "Contact information updated successfully.");
                        }
                    }
                }
            }
            url = RESULT_PAGE;
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
