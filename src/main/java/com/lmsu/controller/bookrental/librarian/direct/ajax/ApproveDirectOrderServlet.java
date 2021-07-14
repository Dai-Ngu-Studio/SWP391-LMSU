package com.lmsu.controller.bookrental.librarian.direct.ajax;

import com.google.gson.Gson;
import com.lmsu.orderdata.orderitems.OrderItemDAO;
import com.lmsu.orderdata.orderitems.OrderItemDTO;
import com.lmsu.orderdata.orders.OrderDAO;
import com.lmsu.orderdata.orders.OrderDTO;
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

@WebServlet(name = "ApproveDirectOrderServlet", value = "/ApproveDirectOrderServlet")
public class ApproveDirectOrderServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(ApproveDirectOrderServlet.class);

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
                // 2. Check if librarian existed
                UserDTO librarian = (UserDTO) session.getAttribute(ATTR_LOGIN_USER);
                if (librarian != null) {
                    // 3. Create connection for rollback
                    conn = DBHelpers.makeConnection();
                    if (conn != null) {
                        conn.setAutoCommit(false);
                        int orderID = Integer.parseInt(txtOrderID);
                        OrderDAO orderDAO = new OrderDAO(conn);
                        OrderItemDAO orderItemDAO = new OrderItemDAO(conn);
                        // Check if order hadn't been cancelled or rejected
                        OrderDTO order = orderDAO.getOrderFromID(orderID);
                        if ((order.getActiveStatus() != ORDER_CANCELLED) && (order.getActiveStatus() != ORDER_REJECTED)) {
                            boolean updateOrderResult = orderDAO
                                    .updateOrder(orderID, ORDER_APPROVED, CONNECTION_USE_BATCH);
                            if (updateOrderResult) {
                                boolean updateOrderItemResult = orderItemDAO
                                        .updateOrderItemStatusOfOrder(
                                                orderID, ITEM_APPROVED, CONNECTION_USE_BATCH);
                                if (updateOrderItemResult) {
                                    conn.commit();
                                }
                            }
                        }
                        order = orderDAO.getOrderFromID(orderID);
                        orderItemDAO.clearOrderItemList();
                        orderItemDAO.getOrderItemsFromOrderID(orderID);
                        List<OrderItemDTO> orderItems = orderItemDAO.getOrderItemList();
                        orderInformation = new Pair<>(order, orderItems);
                        if (conn != null) {
                            conn.setAutoCommit(true);
                            conn.close();
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("ApproveDirectOrderServlet _ SQL: " + ex.getMessage());
            try {
                conn.rollback();
            } catch (SQLException exRollback) {
                LOGGER.error(exRollback.getMessage());
                log("ApproveDirectOrderServlet _ SQL: " + exRollback.getMessage());
            }
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("ApproveDirectOrderServlet _ Naming: " + ex.getMessage());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            log("ApproveDirectOrderServlet _ Exception: " + ex.getMessage());
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
