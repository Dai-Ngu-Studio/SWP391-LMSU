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

@WebServlet(name = "UpdateNotificationSettingServlet", value = "/UpdateNotificationSettingServlet")
public class UpdateNotificationSettingServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(UpdateNotificationSettingServlet.class);
    private static final String USER_SETTING_CONTROLLER = "ShowProfileServlet";

    private final String PARAM_ARRIVAL_OPTION = "arrivalOpt";
    private final String PARAM_POPULAR_OPTION = "popularOpt";

    private final String OPTION_ON = "on";
    private final String OPTION_OFF = "off";

    private final String ATTR_LOGIN_USER = "LOGIN_USER";
    private final String ATTR_MEMBER_UPDATE_SETTING_SUCCESS = "MEMBER_UPDATE_SETTING_SUCCESS";
    private final String ATTR_MEMBER_UPDATE_SETTING_MESSAGE = "MEMBER_UPDATE_SETTING_MESSAGE";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = USER_SETTING_CONTROLLER;
        String txtArrivalOpt = request.getParameter(PARAM_ARRIVAL_OPTION);
        String txtPopularOpt = request.getParameter(PARAM_POPULAR_OPTION);
        try {
            HttpSession session = request.getSession();
            if (session != null) {
                UserDTO user = (UserDTO) session.getAttribute(ATTR_LOGIN_USER);
                if (user != null) {
                    boolean arrivalOpt = true;
                    boolean popularOpt = true;
                    UserDAO userDAO = new UserDAO();
                    if (txtArrivalOpt.equalsIgnoreCase(OPTION_OFF)) {
                        arrivalOpt = false;
                    }
                    if (txtPopularOpt.equalsIgnoreCase(OPTION_OFF)) {
                        popularOpt = false;
                    }
                    boolean updateResult = userDAO.updateMemberNotificationSetting(user.getId(), arrivalOpt, popularOpt);
                    if (updateResult) {
                        UserDTO updatedUser = userDAO.getUserByID(user.getId());
                        AppUtils.storeLoginedUser(session, updatedUser);
                        request.setAttribute(ATTR_MEMBER_UPDATE_SETTING_SUCCESS, true);
                        request.setAttribute(ATTR_MEMBER_UPDATE_SETTING_MESSAGE, "Notification settings updated successfully.");
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("UpdateNotificationSettingServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("UpdateNotificationSettingServlet _ Naming: " + ex.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        processRequest(request, response);
    }
}
