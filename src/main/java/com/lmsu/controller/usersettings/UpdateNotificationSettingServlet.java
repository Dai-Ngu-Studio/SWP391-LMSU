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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String newArrival = request.getParameter("newArrival");
        String highestRated = request.getParameter("highestRated");

        String url = USER_SETTING_CONTROLLER;

        try {
            UserDAO userDAO = new UserDAO();
            HttpSession session = request.getSession();

            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");

            boolean newArrivalB = false;
            if (newArrival != null) {
                newArrivalB = true;
            }

            boolean highestRatedB = false;
            if (highestRated != null) {
                highestRatedB = true;
            }

            boolean updateResult = userDAO.updateMemberNotificationSetting(loginUser.getId(), newArrivalB, highestRatedB);

            if (updateResult) {
                loginUser.setNotifyArrival(newArrivalB);
                loginUser.setNotifyPopular(highestRatedB);
                session.setAttribute("LOGIN_USER", loginUser);
                request.setAttribute("MEMBER_UPDATE_SETTING_MESSAGE", "Notification settings updated successfully.");
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
