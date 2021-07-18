package com.lmsu.controller.penalty.ajax;

import com.google.gson.Gson;
import com.lmsu.orderdata.orderitems.OrderItemDAO;
import com.lmsu.orderdata.orderitems.OrderItemDTO;
import com.lmsu.users.UserDTO;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UpdatePenaltyServlet", value = "/UpdatePenaltyServlet")
public class UpdatePenaltyServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(UpdatePenaltyServlet.class);

    private final String PARAM_TXT_ORDERITEMID = "txtOrderItemID";
    private final String PARAM_TXT_PENALTYSTATUS = "txtPenaltyStatus";

    private final String ATTR_LOGIN_USER = "LOGIN_USER";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String txtOrderItemID = request.getParameter(PARAM_TXT_ORDERITEMID);
        String txtPenaltyStatus = request.getParameter(PARAM_TXT_PENALTYSTATUS);
        OrderItemDTO orderItem = null;

        try {
            // 1. Check if session existed
            HttpSession session = request.getSession(false);
            if (session != null) {
                // 2. Check if staff existed
                UserDTO staff = (UserDTO) session.getAttribute(ATTR_LOGIN_USER);
                if (staff != null) {
                    int orderItemID = Integer.parseInt(txtOrderItemID);
                    int penaltyStatus = Integer.parseInt(txtPenaltyStatus);
                    OrderItemDAO orderItemDAO = new OrderItemDAO();
                    boolean updatePenaltyResult = orderItemDAO.updateOrderItemPenaltyStatus(orderItemID, penaltyStatus);
                    if (updatePenaltyResult) {
                        LOGGER.log(Level.INFO, "Staff " + staff.getName() + " [" + staff.getId() +
                                "] has updated penalty of Order Item " + orderItemID + " to " + penaltyStatus);
                        orderItem = orderItemDAO.getOrderItemByID(orderItemID);
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("UpdatePenaltyServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("UpdatePenaltyServlet _ Naming: " + ex.getMessage());
        } finally {
            String json = new Gson().toJson(orderItem);
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
