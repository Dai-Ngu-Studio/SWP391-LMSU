package com.lmsu.controller.member.cart;

import com.lmsu.bean.member.CartObj;
import com.lmsu.orderdata.orderitems.OrderItemDAO;
import com.lmsu.orderdata.orderitems.OrderItemDTO;
import com.lmsu.orderdata.orders.OrderDAO;
import com.lmsu.orderdata.orders.OrderDTO;
import com.lmsu.users.UserDTO;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "ShowCartServlet", value = "/ShowCartServlet")
public class ShowCartServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(ShowCartServlet.class);
    private final String VIEW_CART_PAGE = "viewCart.jsp";

    private final List<Integer> NO_ORDER_STATUS_SPECIFIED = null;

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

    private final int ORDER_BOTH_METHOD = -1;

    private final int ITEM_PENDING = 0;
    private final int ITEM_APPROVED = 1;
    private final int ITEM_RECEIVED = 2;
    private final int ITEM_RETURN_SCHEDULED = 3;
    private final int ITEM_OVERDUE = 5;
    private final int ITEM_OVERDUE_RETURN_SCHEDULED = 6;

    private final String ATTR_MEMBER_TOTAL_ACTIVE_BORROWS = "MEMBER_TOTAL_ACTIVE_BORROWS";
    private final String ATTR_LOGIN_USER = "LOGIN_USER";
    private final String ATTR_MEMBER_CART = "MEMBER_CART";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = VIEW_CART_PAGE;

        try {
            // 1. Check if session existed (default create one if not exist)
            HttpSession session = request.getSession();
            // 2. Check if session has cart
            CartObj cartObj = (CartObj) session.getAttribute(ATTR_MEMBER_CART);
            if (cartObj == null) {
                cartObj = new CartObj();
                session.setAttribute(ATTR_MEMBER_CART, cartObj);
            }
            UserDTO userDTO = (UserDTO) session.getAttribute(ATTR_LOGIN_USER);
            if (userDTO != null) {
                OrderDAO orderDAO = new OrderDAO();
                OrderItemDAO orderItemDAO = new OrderItemDAO();
                //----------------------------------------------------
                // Active Order Item Statuses
                List<Integer> activeItemStatuses = new ArrayList<Integer>(
                        Arrays.asList(
                                ITEM_PENDING, ITEM_APPROVED, ITEM_RECEIVED,
                                ITEM_RETURN_SCHEDULED, ITEM_OVERDUE,
                                ITEM_OVERDUE_RETURN_SCHEDULED
                        ));
                //----------------------------------------------------
                // Get orders from member
                orderDAO.getOrdersFromMember(ORDER_BOTH_METHOD, userDTO.getId(), NO_ORDER_STATUS_SPECIFIED);
                List<OrderDTO> orders = orderDAO.getOrderList();
                //----------------------------------------------------
                // Get the number of active borrowed books from member
                int memberTotalActiveBorrows = 0;
                if (orders != null) {
                    for (OrderDTO order : orders) {
                        orderItemDAO.clearOrderItemList();
                        orderItemDAO.getOrderItemsFromOrderID(order.getId());
                        List<OrderItemDTO> orderItems = orderItemDAO.getOrderItemList();
                        if (orderItems != null) {
                            for (OrderItemDTO orderItem : orderItems) {
                                for (int activeItemStatus : activeItemStatuses) {
                                    if (orderItem.getLendStatus() == activeItemStatus) {
                                        // found an active item
                                        memberTotalActiveBorrows++;
                                        break; //stop checking active status of current item
                                    }
                                } // end traverse active statuses
                            } // end traverse items
                        }
                    } // end traverse orders
                }
                session.setAttribute(ATTR_MEMBER_TOTAL_ACTIVE_BORROWS, memberTotalActiveBorrows);
            }
            // Redirect to cart
            url = VIEW_CART_PAGE;
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("ShowCartServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("ShowCartServlet _ Naming: " + ex.getMessage());
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
