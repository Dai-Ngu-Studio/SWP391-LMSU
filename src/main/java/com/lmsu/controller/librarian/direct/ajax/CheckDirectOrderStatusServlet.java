package com.lmsu.controller.librarian.direct.ajax;

import com.google.gson.Gson;
import com.lmsu.orderdata.directorders.DirectOrderDAO;
import com.lmsu.orderdata.directorders.DirectOrderDTO;
import com.lmsu.orderdata.orderitems.OrderItemDAO;
import com.lmsu.orderdata.orderitems.OrderItemDTO;
import com.lmsu.orderdata.orders.OrderDAO;
import com.lmsu.orderdata.orders.OrderDTO;
import com.lmsu.users.UserDAO;
import com.lmsu.users.UserDTO;
import com.lmsu.utils.DBHelpers;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CheckDirectOrderStatusServlet", value = "/CheckDirectOrderStatusServlet")
public class CheckDirectOrderStatusServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(CheckDirectOrderStatusServlet.class);

    private final boolean CONNECTION_USE_BATCH = true;

    private final int ORDER_CANCELLED = -1;
    private final int ORDER_PENDING = 0;
    private final int ORDER_APPROVED = 1;
    private final int ORDER_RECEIVED = 2;
    private final int ORDER_RETURNED = 3;
    private final int ORDER_OVERDUE = 4;
    private final int ORDER_REJECTED = 5;
    private final int ORDER_RESERVE_ONLY = 6;
    private final int ORDER_RETURN_SCHEDULED = 7;
    private final int ORDER_RETURN_RETURNED = 8;

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
    private final int ITEM_RESERVED_INACTIVE = 11;

    private final String PARAM_TXT_ORDERID = "txtOrderID";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String txtOrderID = request.getParameter(PARAM_TXT_ORDERID);
        Connection conn = null;
        Pair<OrderDTO, UserDTO> orderInformation = null;
        try {
            // 1. Create connection for rollback
            conn = DBHelpers.makeConnection();
            if (conn != null) {
                conn.setAutoCommit(false);
                int orderID = Integer.parseInt(txtOrderID);
                OrderDAO orderDAO = new OrderDAO(conn);
                OrderItemDAO orderItemDAO = new OrderItemDAO(conn);
                int totalItemAmount = 0;
                int receivedItemAmount = 0;
                int returnedItemAmount = 0;
                // Check if order is suited for received status (ORDER_RECEIVED | ITEM_RECEIVED)
                // get total number of item in order (ignoring RESERVED, RESERVED_INACTIVE)
                orderItemDAO.clearOrderItemList();
                orderItemDAO.getOrderItemsFromOrderID(orderID);
                List<OrderItemDTO> orderItems = orderItemDAO.getOrderItemList();
                for (OrderItemDTO orderItem : orderItems) {
                    int lendStatus = orderItem.getLendStatus();
                    if ((lendStatus != ITEM_RESERVED) && (lendStatus != ITEM_RESERVED_INACTIVE)) {
                        totalItemAmount++;
                        if (lendStatus == ITEM_RECEIVED) {
                            receivedItemAmount++;
                        }
                        if ((lendStatus == ITEM_RETURNED) || (lendStatus == ITEM_OVERDUE_RETURNED)) {
                            returnedItemAmount++;
                        }
                    }
                }

                // If all item status are received (not counting RESERVED)
                // Order Status should be ORDER_RECEIVED
                if (totalItemAmount == receivedItemAmount) {
                    orderDAO.updateOrder(orderID, ORDER_RECEIVED, CONNECTION_USE_BATCH);
                    conn.commit();
                }
                // If all item status are returned (not counting RESERVED)
                // Order Status should be ORDER_RETURNED
                if (totalItemAmount == returnedItemAmount) {
                    orderDAO.updateOrder(orderID, ORDER_RETURNED, CONNECTION_USE_BATCH);
                    conn.commit();
                }
                // Get Librarian who gave books
                DirectOrderDAO directOrderDAO = new DirectOrderDAO();
                DirectOrderDTO directOrder = directOrderDAO.getDirectOrderFromOrderID(orderID);
                UserDAO userDAO = new UserDAO();
                String librarianID = directOrder.getLibrarianID();
                UserDTO librarian = null;
                if ((librarianID != null) || (!librarianID.isEmpty())) {
                    librarian = userDAO.getUserByID(librarianID);
                }
                // Get Order
                OrderDTO order = orderDAO.getOrderFromID(orderID);
                orderInformation = new Pair<OrderDTO, UserDTO>(order, librarian);
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("CheckDirectOrderStatusServlet _ SQL: " + ex.getMessage());
            try {
                conn.rollback();
            } catch (SQLException exRollback) {
                LOGGER.error(exRollback.getMessage());
                log("CheckDirectOrderStatusServlet _ SQL: " + exRollback.getMessage());
            }
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("CheckDirectOrderStatusServlet _ Naming: " + ex.getMessage());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            log("CheckDirectOrderStatusServlet _ Exception: " + ex.getMessage());
        } finally {
            String json = new Gson().toJson(orderInformation);
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
