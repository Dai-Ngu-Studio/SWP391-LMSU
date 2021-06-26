package com.lmsu.controller.member.cart;

import com.lmsu.bean.book.BookObj;
import com.lmsu.bean.member.CartObj;
import com.lmsu.books.BookDAO;
import com.lmsu.books.BookDTO;
import com.lmsu.orderdata.deliveryorders.DeliveryOrderDAO;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@WebServlet(name = "CheckoutDeliveryServlet", value = "/CheckoutDeliveryServlet")
public class CheckoutDeliveryServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(CheckoutDeliveryServlet.class);
    private final String SHOW_BOOK_CATALOG_CONTROLLER = "ShowBookCatalogServlet"; //W.I.P. temporary (to be changed)
    private final String INDEX_CONTROLLER = "IndexServlet"; //W.I.P. temporary (to be changed)

    private final boolean DELIVERY_METHOD = true;
    private final boolean DELIVERY_NOT_RETURN = false;

    private final int ORDER_CANCELLED = -1;
    private final int ORDER_PENDING = 0;
    private final int ORDER_APPROVED = 1;
    private final int ORDER_RECEIVED = 2;
    private final int ORDER_RETURNED = 3;
    private final int ORDER_OVERDUE = 4;
    private final int ORDER_REJECTED = 5;
    private final int ORDER_RESERVE_ONLY = 6;

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

    private final String ATTR_MEMBER_CART = "MEMBER_CART";
    private final String ATTR_LOGIN_USER = "LOGIN_USER";
    private final String ATTR_CHECKOUT_SUCCESS = "CHECKOUT_SUCCESS";

    private final String ATTR_CHECKOUT_RECEIVERNAME = "CHECKOUT_RECEIVERNAME";
    private final String ATTR_CHECKOUT_PHONENUMBER = "CHECKOUT_PHONENUMBER";
    private final String ATTR_CHECKOUT_ADDRESSONE = "CHECKOUT_ADDRESSONE";
    private final String ATTR_CHECKOUT_ADDRESSTWO = "CHECKOUT_ADDRESSTWO";
    private final String ATTR_CHECKOUT_CITY = "CHECKOUT_CITY";
    private final String ATTR_CHECKOUT_DISTRICT = "CHECKOUT_DISTRICT";
    private final String ATTR_CHECKOUT_WARD = "CHECKOUT_WARD";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = INDEX_CONTROLLER; //W.I.P. temporary (to be changed)
        Connection conn = null;

        try {
            // 1. Check if session existed
            HttpSession session = request.getSession(false);
            if (session != null) {
                String receiverName = (String) session.getAttribute(ATTR_CHECKOUT_RECEIVERNAME);
                String phoneNumber = (String) session.getAttribute(ATTR_CHECKOUT_PHONENUMBER);
                String deliveryAddressOne = (String) session.getAttribute(ATTR_CHECKOUT_ADDRESSONE);
                String deliveryAddressTwo = (String) session.getAttribute(ATTR_CHECKOUT_ADDRESSTWO);
                String city = (String) session.getAttribute(ATTR_CHECKOUT_CITY);
                String district = (String) session.getAttribute(ATTR_CHECKOUT_DISTRICT);
                String ward = (String) session.getAttribute(ATTR_CHECKOUT_WARD);
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
                                // 5. Get current date, get deadline
                                // 6. Create new order
                                OrderDAO orderDAO = new OrderDAO(conn);
                                BookDAO bookDAO = new BookDAO();
                                int orderID = orderDAO.addOrder(userDTO.getId(), DELIVERY_METHOD, ORDER_PENDING);
                                if (orderID > 0) {
                                    // 7. Traverse items in cart and add to list
                                    List<OrderItemDTO> orderItems = new ArrayList<OrderItemDTO>();
                                    Date returnDeadline = DateHelpers.getDeadlineDate(DateHelpers.getCurrentDate(), 14);
                                    for (String bookID : cartItems.keySet()) {
                                        OrderItemDTO orderItemDTO = new OrderItemDTO();
                                        orderItemDTO.setOrderID(orderID);
                                        orderItemDTO.setMemberID(userDTO.getId());
                                        orderItemDTO.setBookID(bookID);
                                        //do lát present nên tui sửa tạm orderItemDTO.setLendStatus(0) thành
                                        //orderItemDTO.setLendStatus(ITEM_RECEIVED) (T. Phuc)
                                        if (bookDAO.getBookById(bookID).getQuantity() > 0) {
                                            orderItemDTO.setLendStatus(ITEM_RECEIVED);
                                            orderItemDTO.setReturnDeadline(returnDeadline);
                                        } else {
                                            orderItemDTO.setLendStatus(ITEM_RESERVED);
                                            orderItemDTO.setReturnDeadline(null);
                                        }
                                        // default ▼
                                        //orderItemDTO.setLendStatus(ITEM_PENDING);
                                        orderItemDTO.setReturnDate(null);
                                        orderItems.add(orderItemDTO);
                                    }// end traverse items in cart
                                    // 8. Add order items
                                    OrderItemDAO orderItemDAO = new OrderItemDAO(conn);
                                    boolean itemsAddResult = orderItemDAO.addOrderItems(orderItems);
                                    if (itemsAddResult) {
                                        // 9. Create Delivery Order
                                        DeliveryOrderDAO deliveryOrderDAO = new DeliveryOrderDAO(conn);
                                        boolean deliveryOrderAddResult = deliveryOrderDAO
                                                .addDeliveryOrder(orderID, phoneNumber,
                                                        deliveryAddressOne, deliveryAddressTwo, city, district, ward, DELIVERY_NOT_RETURN);
                                        if (deliveryOrderAddResult) {
                                            conn.commit();
                                            session.removeAttribute(ATTR_MEMBER_CART);
                                            // 10.a
                                            // Decrease quantity of books
                                            // 10.b
                                            // Check if books have been reserved in the past
                                            // if yes, mark the old reserve status as inactive (ITEM_RESERVED_INACTIVE)
                                            // avoid newest order by checking orderID
                                            for (String bookID : cartItems.keySet()) {
                                                BookDTO bookDTO = bookDAO.getBookById(bookID);
                                                if (bookDTO.getQuantity() > 0) {
                                                    bookDAO.updateQuantity(bookID, bookDTO.getQuantity() - 1);
                                                }
                                                orderItemDAO.clearOrderItemList();
                                                orderItemDAO.getMemberItemsFromBookID(
                                                        bookID,
                                                        userDTO.getId(),
                                                        new ArrayList<Integer>(Arrays.asList(ITEM_RESERVED)));
                                                List<OrderItemDTO> itemsOfCurrentBookByMember =
                                                        orderItemDAO.getOrderItemList();
                                                if (itemsOfCurrentBookByMember != null) {
                                                    for (OrderItemDTO currentItem : itemsOfCurrentBookByMember) {
                                                        if (currentItem.getOrderID() != orderID) {
                                                            if (currentItem.getLendStatus() == ITEM_RESERVED) {
                                                                orderItemDAO.updateOrderItemStatus(
                                                                        currentItem.getId(),
                                                                        ITEM_RESERVED_INACTIVE
                                                                );
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            request.setAttribute(ATTR_CHECKOUT_SUCCESS, true);
                                            url = SHOW_BOOK_CATALOG_CONTROLLER; //W.I.P. temporary (to be changed)
                                        }// end if delivery order created successfully
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
            log("CheckoutDeliveryServlet _ SQL: " + ex.getMessage());
            try {
                conn.rollback();
            } catch (SQLException exRollback) {
                LOGGER.error(exRollback.getMessage());
                log("CheckoutDeliveryServlet _ SQL: " + exRollback.getMessage());
            }
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("CheckoutDeliveryServlet _ Naming: " + ex.getMessage());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            log("CheckoutDeliveryServlet _ Exception: " + ex.getMessage());
        } finally {
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
