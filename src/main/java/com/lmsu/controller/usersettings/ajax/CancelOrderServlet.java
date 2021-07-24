package com.lmsu.controller.usersettings.ajax;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.lmsu.orderdata.deliveryorders.DeliveryOrderDAO;
import com.lmsu.orderdata.deliveryorders.DeliveryOrderDTO;
import com.lmsu.orderdata.orderitems.OrderItemDAO;
import com.lmsu.orderdata.orderitems.OrderItemDTO;
import com.lmsu.orderdata.orders.OrderDAO;
import com.lmsu.orderdata.orders.OrderDTO;
import com.lmsu.services.GhnApis;
import com.lmsu.users.UserDTO;
import com.lmsu.utils.DBHelpers;
import javafx.util.Pair;
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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "CancelOrderServlet", value = "/CancelOrderServlet")
public class CancelOrderServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(CancelOrderServlet.class);

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
    private final String ATTR_LOGIN_USER = "LOGIN_USER";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String txtOrderID = request.getParameter(PARAM_TXT_ORDERID);
        Connection conn = null;
        Pair<OrderDTO, List<OrderItemDTO>> orderInformation = null;

        try {
            // 1. Check if session existed
            HttpSession session = request.getSession(false);
            if (session != null) {
                // 2. Check if user existed
                UserDTO member = (UserDTO) session.getAttribute(ATTR_LOGIN_USER);
                if (member != null) {
                    // 3. Create connection for rollback
                    conn = DBHelpers.makeConnection();
                    if (conn != null) {
                        conn.setAutoCommit(false);
                        int orderID = Integer.parseInt(txtOrderID);
                        OrderDAO orderDAO = new OrderDAO(conn);
                        OrderItemDAO orderItemDAO = new OrderItemDAO(conn);
                        // Check if order hadn't been cancelled or rejected
                        OrderDTO order = orderDAO.getOrderFromID(orderID);
                        orderItemDAO.getOrderItemsFromOrderID(orderID);
                        List<OrderItemDTO> orderItems = orderItemDAO.getOrderItemList();
                        if ((order.getActiveStatus() != ORDER_APPROVED) && (order.getActiveStatus() != ORDER_REJECTED)) {
                            boolean updateOrderResult = orderDAO
                                    .updateOrder(orderID, ORDER_CANCELLED, CONNECTION_USE_BATCH);
                            if (updateOrderResult) {
                                boolean updateOrderItemResult = orderItemDAO
                                        .updateOrderItemStatusOfOrder(
                                                orderID, ITEM_CANCELLED, CONNECTION_USE_BATCH);
                                if (updateOrderItemResult) {
                                    conn.commit();
                                    LOGGER.log(Level.INFO, "Member " + member.getName() + " [" + member.getId() +
                                            "] has cancelled order " + txtOrderID);
                                } // end if order item status updated
                            } // end if order status updated
                        } // end if check status direction
                        order = orderDAO.getOrderFromID(orderID);
                        orderItemDAO.clearOrderItemList();
                        orderItemDAO.getOrderItemsFromOrderID(orderID);
                        orderItems = orderItemDAO.getOrderItemList();
                        orderInformation = new Pair<>(order, orderItems);
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("CancelOrderServlet _ SQL: " + ex.getMessage());
            try {
                conn.rollback();
            } catch (SQLException exRollback) {
                LOGGER.error(exRollback.getMessage());
                log("CancelOrderServlet _ SQL: " + exRollback.getMessage());
            }
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("CancelOrderServlet _ Naming: " + ex.getMessage());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            log("CancelOrderServlet _ Exception: " + ex.getMessage());
            try {
                conn.rollback();
            } catch (SQLException exRollback) {
                LOGGER.error(exRollback.getMessage());
                log("CancelOrderServlet _ SQL: " + exRollback.getMessage());
            }
        } finally {
            try {
                if ((conn != null) && (!conn.isClosed())) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException ex) {
                LOGGER.error(ex.getMessage());
                log("CancelOrderServlet _ SQL: " + ex.getMessage());
            }
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
