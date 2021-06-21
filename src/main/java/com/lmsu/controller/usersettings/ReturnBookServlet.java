package com.lmsu.controller.usersettings;

import com.lmsu.orderdata.orderitems.OrderItemDAO;
import com.lmsu.orderdata.orderitems.OrderItemDTO;
import com.lmsu.users.UserDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ReturnBookServlet", value = "/ReturnBookServlet")
public class ReturnBookServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(ReturnBookServlet.class);
    private static final String SHOW_PROFILE_CONTROLLER = "ShowProfileServlet";
    private static final String INDEX_CONTROLLER = "IndexServlet";

    private final int ITEM_CANCELLED = -1;
    private final int ITEM_PENDING = 0;
    private final int ITEM_APPROVED = 1;
    private final int ITEM_RECEIVED = 2;
    private final int ITEM_RETURN_SCHEDULED = 3;
    private final int ITEM_RETURNED = 4;
    private final int ITEM_OVERDUE = 5;
    private final int ITEM_OVERDUE_RETURN_SCHEDULED = 6;
    private final int ITEM_OVERDUE_RETURNED = 7;
    private final int ITEM_REJECTED = 8;
    private final int ITEM_LOST = 9;
    private final int ITEM_RESERVED = 10;

    private final String ATTR_LOGIN_USER = "LOGIN_USER";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = INDEX_CONTROLLER;
        String orderItemID = request.getParameter("orderItemPk");

        try {
            HttpSession session = request.getSession();
            if (session != null) {
                UserDTO userDTO = (UserDTO) session.getAttribute(ATTR_LOGIN_USER);
                if (userDTO != null) {
                    OrderItemDAO orderItemDAO = new OrderItemDAO();
                    OrderItemDTO orderItemDTO = orderItemDAO.getOrderItemByID(Integer.valueOf(orderItemID));
                    int lendStatus = orderItemDTO.getLendStatus();
                    if (lendStatus == ITEM_RECEIVED) {
                        boolean result = orderItemDAO.returnBook(orderItemID, ITEM_RETURN_SCHEDULED);
                        if (result) {
                            url = SHOW_PROFILE_CONTROLLER;
                        }
                    } else if (lendStatus == ITEM_OVERDUE) {
                        boolean result = orderItemDAO.returnBook(orderItemID, ITEM_OVERDUE_RETURN_SCHEDULED);
                        if (result) {
                            url = SHOW_PROFILE_CONTROLLER;
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("ReturnBookServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("ReturnBookServlet _ Naming: " + ex.getMessage());
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
