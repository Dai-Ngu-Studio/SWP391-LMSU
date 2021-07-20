package com.lmsu.controller.bookrental.renewal.ajax;

import com.google.gson.Gson;
import com.lmsu.bean.renewal.RenewalRequestObj;
import com.lmsu.renewalrequests.RenewalRequestDAO;
import com.lmsu.renewalrequests.RenewalRequestDTO;
import com.lmsu.users.UserDTO;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ApproveRenewalServlet", value = "/ApproveRenewalServlet")
public class ApproveRenewalServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(ApproveRenewalServlet.class);

    private final int RENEWAL_CANCELLED = -1;
    private final int RENEWAL_PENDING = 0;
    private final int RENEWAL_APPROVED = 1;
    private final int RENEWAL_REJECTED = 2;

    private final String PARAM_TXT_RENEWALID = "txtRenewalID";
    private final String ATTR_LOGIN_USER = "LOGIN_USER";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String txtRenewalID = request.getParameter(PARAM_TXT_RENEWALID);
        RenewalRequestObj renewalObj = null;

        try {
            // 1. Check if session existed
            HttpSession session = request.getSession(false);
            if (session != null) {
                // 2. Check if staff existed
                UserDTO staff = (UserDTO) session.getAttribute(ATTR_LOGIN_USER);
                if (staff != null) {
                    int renewalID = Integer.parseInt(txtRenewalID);
                    RenewalRequestDAO renewalRequestDAO = new RenewalRequestDAO();
                    boolean approveRenewalResult = renewalRequestDAO.updateRenewalRequestStatus(renewalID, RENEWAL_APPROVED);
                    if (approveRenewalResult) {
                        boolean librarianResult = renewalRequestDAO.updateLibrarianOfRenewalRequest(renewalID, staff.getId());
                        if (librarianResult) {
                            LOGGER.log(Level.INFO, "Staff " + staff.getName() + " [" + staff.getId() +
                                    "] has approved renewal of Request " + renewalID);
                            RenewalRequestDTO renewal = renewalRequestDAO.getRenewalByID(renewalID);
                            renewalObj = new RenewalRequestObj();
                            renewalObj.setApprovalStatus(renewal.getApprovalStatus());
                            renewalObj.setLibrarian(staff);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("ApproveRenewalServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("ApproveRenewalServlet _ Naming: " + ex.getMessage());
        } finally {
            String json = new Gson().toJson(renewalObj);
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
