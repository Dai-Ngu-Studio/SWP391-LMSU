package com.lmsu.controller.member.cart;

import com.lmsu.bean.book.BookObj;
import com.lmsu.bean.member.CartObj;
import com.lmsu.books.BookDAO;
import com.lmsu.orderdata.directorders.DirectOrderDAO;
import com.lmsu.orderdata.orderitems.OrderItemDAO;
import com.lmsu.orderdata.orderitems.OrderItemDTO;
import com.lmsu.orderdata.orders.OrderDAO;
import com.lmsu.users.UserDTO;
import com.lmsu.utils.DBHelpers;
import com.lmsu.utils.DateHelpers;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "CheckoutDirectServlet", value = "/CheckoutDirectServlet")
public class CheckoutDirectServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(CheckoutDirectServlet.class);
    private final String SHOW_BOOK_CATALOG_CONTROLLER = "ShowBookCatalogServlet"; //W.I.P. temporary (to be changed)
    private final String INDEX_CONTROLLER = "IndexServlet"; //W.I.P. temporary (to be changed)

    private final boolean DIRECT_METHOD = false;
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

    private final String ATTR_MEMBER_CART = "MEMBER_CART";
    private final String ATTR_LOGIN_USER = "LOGIN_USER";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = SHOW_BOOK_CATALOG_CONTROLLER; //W.I.P. temporary (to be changed)
        String txtPickupDate = request.getParameter("txtPickupDate");
        String txtPickupTime = request.getParameter("txtPickupTime");
        Connection conn = null;

        try {
            // 1. Check if session existed
            HttpSession session = request.getSession(false);
            if (session != null) {
                // 2. Check if cart existed
                CartObj cartObj = (CartObj) session.getAttribute(ATTR_MEMBER_CART);
                if (cartObj != null) {
                    // 3. Check if items existed
                    if (cartObj.getItems() != null) {
                        Map<String, BookObj> cartItems = cartObj.getItems();
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
                                int orderID = orderDAO.addOrder(userDTO.getId(), DIRECT_METHOD);
                                Date returnDeadline = DateHelpers.getDeadlineDate(DateHelpers.getCurrentDate(), 14);
                                if (orderID > 0) {
                                    // 7. Traverse items in cart and add to list
                                    List<OrderItemDTO> orderItems = new ArrayList<OrderItemDTO>();
                                    for (String bookID : cartItems.keySet()) {
                                        BookDAO bookDAO = new BookDAO();
                                        OrderItemDTO orderItemDTO = new OrderItemDTO();
                                        orderItemDTO.setOrderID(orderID);
                                        orderItemDTO.setMemberID(userDTO.getId());
                                        orderItemDTO.setBookID(bookID);
                                        if(!bookDAO.getBookByIDAndQuantity(bookID)) {
                                            orderItemDTO.setLendStatus(ITEM_PENDING);
                                            orderItemDTO.setReturnDeadline(returnDeadline);
                                        } else {
                                            orderItemDTO.setLendStatus(ITEM_RESERVED);
                                            orderItemDTO.setReturnDeadline(null);
                                        }
                                        orderItemDTO.setReturnDate(null);
                                        orderItems.add(orderItemDTO);
                                    }// end traverse items in cart
                                    // 8. Add order items
                                    OrderItemDAO orderItemDAO = new OrderItemDAO(conn);
                                    boolean itemsAddResult = orderItemDAO.addOrderItems(orderItems);
                                    if (itemsAddResult) {
                                        // 9. Create Delivery Order
                                        DirectOrderDAO directOrderDAO = new DirectOrderDAO(conn);
                                        boolean directOrderAddResult = directOrderDAO
                                                .addDirectOrder(orderID, schedule);
                                        if (directOrderAddResult) {
                                            conn.commit();
                                            session.removeAttribute(ATTR_MEMBER_CART);
                                            url = INDEX_CONTROLLER; //W.I.P. temporary (to be changed)
                                        }// end if direct order created successfully
                                    }// end if order items added successfully
                                }// end if order created successfully
                                if (conn != null) {
                                    conn.setAutoCommit(true);
                                    conn.close();
                                } // end if connected existed and close connection
                            }// end if connection existed
                        }// end if user existed
                    }// end if items existed
                }// end if cart existed
            }// end if session existed
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("CheckoutDirectServlet _ SQL: " + ex.getMessage());
            try {
                conn.rollback();
            } catch (SQLException exRollback) {
                LOGGER.error(exRollback.getMessage());
                log("CheckoutDirectServlet _ SQL: " + exRollback.getMessage());
            }
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("CheckoutDirectServlet _ Naming: " + ex.getMessage());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            log("CheckoutDirectServlet _ Exception: " + ex.getMessage());
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
