package com.lmsu.controller.usersettings.ajax;

import com.google.gson.Gson;
import com.lmsu.renewalrequests.RenewalRequestDAO;
import com.lmsu.renewalrequests.RenewalRequestDTO;
import com.lmsu.users.UserDTO;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CancelRenewalServlet", value = "/CancelRenewalServlet")
public class CancelRenewalServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(CancelRenewalServlet.class);

    private final int RENEWAL_CANCELLED = -1;
    private final int RENEWAL_PENDING = 0;
    private final int RENEWAL_APPROVED = 1;
    private final int RENEWAL_REJECTED = 2;

    private final String PARAM_TXT_RENEWALID = "txtRenewalID";
    private final String ATTR_LOGIN_USER = "LOGIN_USER";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String txtRenewalID = request.getParameter(PARAM_TXT_RENEWALID);
        RenewalRequestDTO renewal = null;

        try {
            // 1. Check if session existed
            HttpSession session = request.getSession(false);
            if (session != null) {
                // 2. Check if staff existed
                UserDTO member = (UserDTO) session.getAttribute(ATTR_LOGIN_USER);
                if (member != null) {
                    int renewalID = Integer.parseInt(txtRenewalID);
                    RenewalRequestDAO renewalRequestDAO = new RenewalRequestDAO();
                    renewal = renewalRequestDAO.getRenewalByID(renewalID);
                    if ((renewal.getApprovalStatus() != RENEWAL_APPROVED)
                            && (renewal.getApprovalStatus() != RENEWAL_REJECTED)) {
                        boolean cancelRenewalResult = renewalRequestDAO.updateRenewalRequestStatus(renewalID, RENEWAL_CANCELLED);
                        if (cancelRenewalResult) {
                            renewal = renewalRequestDAO.getRenewalByID(renewalID);
                            LOGGER.log(Level.INFO, "Member " + member.getName() + " [" + member.getId() +
                                    "] has cancelled renewal request " + renewalID);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("CancelRenewalServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("CancelRenewalServlet _ Naming: " + ex.getMessage());
        } finally {
            String json = new Gson().toJson(renewal);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
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
