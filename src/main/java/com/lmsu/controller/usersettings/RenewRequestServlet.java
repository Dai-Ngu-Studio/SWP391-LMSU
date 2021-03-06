package com.lmsu.controller.usersettings;

import com.lmsu.renewalrequests.RenewalRequestDAO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "RenewRequestServlet", value = "/RenewRequestServlet")
public class RenewRequestServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(RenewRequestServlet.class);
    private static final String USER_SETTING_CONTROLLER = "ShowProfileServlet";
    private static final String USER_PAGE = "index.jsp";
    private final int RENEWAL_PENDING = 0;

    private final String ATTR_MEMBER_UPDATE_SETTING_SUCCESS = "MEMBER_UPDATE_SETTING_SUCCESS";
    private final String ATTR_MEMBER_UPDATE_SETTING_MESSAGE = "MEMBER_UPDATE_SETTING_MESSAGE";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = USER_PAGE;
        //String bookID = request.getParameter("bookPk");
        String orderItemsID = request.getParameter("orderItemPk");
        String reason = request.getParameter("txtReason");
        String extendDate = request.getParameter("txtExtendDate");

        try {
            RenewalRequestDAO dao = new RenewalRequestDAO();
            int orderItemsIDVal = Integer.parseInt(orderItemsID);
            boolean result = dao.addRenewal(orderItemsIDVal, reason.trim(), extendDate, RENEWAL_PENDING);
            if (result) {
                url = USER_SETTING_CONTROLLER;
                request.setAttribute(ATTR_MEMBER_UPDATE_SETTING_SUCCESS, true);
                request.setAttribute(ATTR_MEMBER_UPDATE_SETTING_MESSAGE, "Renewal requested successfully.");
            }

        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("RenewRequestServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("RenewRequestServlet _ Naming: " + ex.getMessage());
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
