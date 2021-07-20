package com.lmsu.controller.usersettings;

import com.lmsu.renewalrequests.RenewalRequestDAO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "RenewRequestServlet", value = "/RenewRequestServlet")
public class RenewRequestServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(RenewRequestServlet.class);
    private static final String USER_SETTING_CONTROLLER = "ShowProfileServlet";
    private static final String USER_PAGE = "index.jsp";
    private final int RENEWAL_PENDING = 0;

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
            if(result){
                url = USER_SETTING_CONTROLLER;
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
