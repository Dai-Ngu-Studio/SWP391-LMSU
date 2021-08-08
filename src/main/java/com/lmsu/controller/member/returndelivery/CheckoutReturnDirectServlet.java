package com.lmsu.controller.member.returndelivery;

import com.lmsu.bean.member.ReturnCartObj;
import com.lmsu.orderdata.directorders.DirectOrderDAO;
import com.lmsu.orderdata.orderitems.OrderItemDAO;
import com.lmsu.orderdata.orderitems.OrderItemDTO;
import com.lmsu.orderdata.orders.OrderDAO;
import com.lmsu.users.UserDTO;
import com.lmsu.utils.DBHelpers;
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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "CheckoutReturnDirectServlet", value = "/CheckoutReturnDirectServlet")
public class CheckoutReturnDirectServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(CheckoutReturnDirectServlet.class);
    private final String SHOW_USER_SETTING_CONTROLLER = "ShowProfileServlet"; //W.I.P. temporary (to be changed)
    private final String INDEX_CONTROLLER = "IndexServlet"; //W.I.P. temporary (to be changed)

    private final boolean DIRECT_METHOD = false;
    private final boolean DIRECT_RETURN = true;
    private final boolean CONNECTION_NO_BATCH = false;

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

    private final int PENALTY_NONE = 0;
    private final int PENALTY_UNPAID = 1;
    private final int PENALTY_PAID = 2;

    private final String ATTR_RETURN_CART = "RETURN_CART";
    private final String ATTR_LOGIN_USER = "LOGIN_USER";

    private final String ATTR_CHECKOUT_PICKUPDATE = "CHECKOUT_PICKUP_DATE";
    private final String ATTR_CHECKOUT_PICKUPTIME = "CHECKOUT_PICKUP_TIME";
    private final String ATTR_RETURN_SUCCESS = "RETURN_SUCCESS";
    private final String ATTR_RETURN_FAILED = "RETURN_FAILED";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = INDEX_CONTROLLER; //W.I.P. temporary (to be changed)
        Connection conn = null;

        try {
            // 1. Check if session existed
            HttpSession session = request.getSession(false);
            if (session != null) {
                String txtPickupDate = (String) session.getAttribute(ATTR_CHECKOUT_PICKUPDATE);
                String txtPickupTime = (String) session.getAttribute(ATTR_CHECKOUT_PICKUPTIME);
                if (!txtPickupDate.isEmpty() && !txtPickupTime.isEmpty()) {
                    // 2. Check if cart existed
                    ReturnCartObj returnCartObj = (ReturnCartObj) session.getAttribute(ATTR_RETURN_CART);
                    if (returnCartObj != null) {
                        // 3. Check if items existed
                        if (returnCartObj.getReturnItems() != null) {
                            Map<Integer, OrderItemDTO> cartReturnItems = returnCartObj.getReturnItems();
                            UserDTO userDTO = (UserDTO) session.getAttribute(ATTR_LOGIN_USER);
                            if (userDTO != null) {
                                // 4. Create connection for rollback
                                conn = DBHelpers.makeConnection();
                                if (conn != null) {
                                    conn.setAutoCommit(false);
                                    // 5. Create Timestamp
                                    LocalDate pickupDate = LocalDate.parse(txtPickupDate);
                                    LocalTime pickupTime = LocalTime.parse(txtPickupTime);
                                    LocalDateTime pickupDateTime = LocalDateTime.of(pickupDate, pickupTime);
                                    Timestamp schedule = Timestamp.valueOf(pickupDateTime);
                                    // 6. Create new order
                                    OrderDAO orderDAO = new OrderDAO(conn);
                                    int orderID = orderDAO.addOrder(userDTO.getId(), DIRECT_METHOD, ORDER_RETURN_SCHEDULED);
                                    if (orderID > 0) {
                                        // 7. Traverse items in cart and add to list
                                        OrderItemDAO orderItem_DAO = new OrderItemDAO(conn);
                                        List<OrderItemDTO> orderItems = new ArrayList<OrderItemDTO>();

                                        for (Integer orderItemsID : cartReturnItems.keySet()) {
                                            if (orderItem_DAO.getOrderItemByID(orderItemsID).getLendStatus() == ITEM_RECEIVED) {
                                                orderItem_DAO.updateOrderItemReturnOrderIDAndStatus(orderItemsID, orderID, ITEM_RETURN_SCHEDULED);
                                            } else if (orderItem_DAO.getOrderItemByID(orderItemsID).getLendStatus() == ITEM_OVERDUE) {
                                                orderItem_DAO.updateOrderItemReturnOrderIDAndStatus(orderItemsID, orderID, ITEM_OVERDUE_RETURN_SCHEDULED);
                                            }
                                        }// end traverse items in cart
                                        // 8. Add order items
                                        // 9. Create Direct Order
                                        DirectOrderDAO directOrderDAO = new DirectOrderDAO(conn);
                                        boolean directOrderAddResult = directOrderDAO
                                                .addDirectOrder(orderID, schedule, DIRECT_RETURN);
                                        if (directOrderAddResult) {
                                            conn.commit();
                                            session.removeAttribute(ATTR_RETURN_CART);
                                            session.removeAttribute(ATTR_CHECKOUT_PICKUPDATE);
                                            session.removeAttribute(ATTR_CHECKOUT_PICKUPTIME);
                                            request.setAttribute(ATTR_RETURN_SUCCESS, true);
                                            url = SHOW_USER_SETTING_CONTROLLER; //W.I.P. temporary (to be changed)
                                        } // end if delivery order created successfully
                                        else {
                                            request.setAttribute(ATTR_RETURN_FAILED, true);
                                        }
                                    }// end if order created successfully
                                    else {
                                        request.setAttribute(ATTR_RETURN_FAILED, true);
                                    }
                                    if (conn != null) {
                                        conn.setAutoCommit(true);
                                        conn.close();
                                    } // end if connected existed and close connection
                                }// end if connection existed
                            }// end if user existed
                        }// end if items existed
                    }// end if cart existed
                } // end check if parameters are empty
            }// end if session existed
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("CheckoutReturnDirectServlet _ SQL: " + ex.getMessage());
            try {
                conn.rollback();
                request.setAttribute(ATTR_RETURN_FAILED, true);
            } catch (SQLException exRollback) {
                LOGGER.error(exRollback.getMessage());
                log("CheckoutReturnDirectServlet _ SQL: " + exRollback.getMessage());
            }
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("CheckoutReturnDirectServlet _ Naming: " + ex.getMessage());
            try {
                conn.rollback();
                request.setAttribute(ATTR_RETURN_FAILED, true);
            } catch (SQLException exRollback) {
                LOGGER.error(exRollback.getMessage());
                log("CheckoutReturnDirectServlet _ SQL: " + exRollback.getMessage());
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            log("CheckoutReturnDirectServlet _ Exception: " + ex.getMessage());
            try {
                conn.rollback();
                request.setAttribute(ATTR_RETURN_FAILED, true);
            } catch (SQLException exRollback) {
                LOGGER.error(exRollback.getMessage());
                log("CheckoutReturnDirectServlet _ SQL: " + exRollback.getMessage());
            }
        } finally {
            try {
                if ((conn != null) && (!conn.isClosed())) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException ex) {
                LOGGER.error(ex.getMessage());
                log("CheckoutReturnDirectServlet _ SQL: " + ex.getMessage());
            }
            request.getRequestDispatcher(url).forward(request, response);
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
