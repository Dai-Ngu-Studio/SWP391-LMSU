package com.lmsu.controller.bookrental.renewal.ajax;

import com.google.gson.Gson;
import com.lmsu.renewalrequests.RenewalRequestDAO;
import com.lmsu.renewalrequests.RenewalRequestDTO;
import com.lmsu.users.UserDAO;
import com.lmsu.users.UserDTO;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "RejectRenewalServlet", value = "/RejectRenewalServlet")
public class RejectRenewalServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(RejectRenewalServlet.class);

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
                UserDAO userDAO = new UserDAO();
                UserDTO staff = (UserDTO) session.getAttribute(ATTR_LOGIN_USER);
                if (staff != null) {
                    int renewalID = Integer.parseInt(txtRenewalID);
                    RenewalRequestDAO renewalRequestDAO = new RenewalRequestDAO();
                    renewal = renewalRequestDAO.getRenewalByID(renewalID);
                    if ((renewal.getApprovalStatus() != RENEWAL_APPROVED) && (renewal.getApprovalStatus() != RENEWAL_CANCELLED)) {
                        boolean rejectRenewalResult = renewalRequestDAO.updateRenewalRequestStatus(renewalID, RENEWAL_REJECTED);
                        if (rejectRenewalResult) {
                            boolean librarianResult = renewalRequestDAO.updateLibrarianOfRenewalRequest(renewalID, staff.getId());
                            if (librarianResult) {
                                renewal = renewalRequestDAO.getRenewalByID(renewalID);
                                LOGGER.log(Level.INFO, "Staff " + staff.getName() + " [" + staff.getId() +
                                        "] has rejected renewal request " + renewalID);
                                UserDTO staffDTO = userDAO.getUserByID(renewal.getLibrarianID());
                                renewal.setApprovalStatus(renewal.getApprovalStatus());
                                renewal.setLibrarian(staffDTO);
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("RejectRenewalServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("RejectRenewalServlet _ Naming: " + ex.getMessage());
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
