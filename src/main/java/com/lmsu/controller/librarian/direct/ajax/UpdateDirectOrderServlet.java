package com.lmsu.controller.librarian.direct.ajax;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.lmsu.books.BookDAO;
import com.lmsu.books.BookDTO;
import com.lmsu.orderdata.directorders.DirectOrderDAO;
import com.lmsu.orderdata.orderitems.OrderItemDAO;
import com.lmsu.orderdata.orderitems.OrderItemDTO;
import com.lmsu.orderdata.orders.OrderDAO;
import com.lmsu.orderdata.orders.OrderDTO;
import com.lmsu.users.UserDTO;
import com.lmsu.utils.DBHelpers;
import com.lmsu.utils.DateHelpers;
import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.util.Pair;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "UpdateDirectOrderServlet", value = "/UpdateDirectOrderServlet")
public class UpdateDirectOrderServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(UpdateDirectOrderServlet.class);

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

    private final boolean DATE_RECEIVE = false;
    private final boolean DATE_RETURN = true;

    private final Map<Integer, Boolean> DATE_OPTIONS = new HashMap<Integer, Boolean>() {{
        put(ITEM_RECEIVED, DATE_RECEIVE);
        put(ITEM_RETURNED, DATE_RETURN);
        put(ITEM_OVERDUE_RETURNED, DATE_RETURN);
    }};

    private final String PARAM_TXT_ORDERID = "txtOrderID";
    private final String PARAM_TXT_ITEMSTATUSLIST = "jsonItemStats";

    private final String KEY_ORDERITEMID = "orderItemID";
    private final String KEY_LENDSTATUS = "lendStatus";
    private final String KEY_ORDERID = "orderID";
    private final String KEY_LENDDATE = "lendDate";
    private final String KEY_RETURNDATE = "returnDate";

    private final String ATTR_LOGIN_USER = "LOGIN_USER";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String txtOrderID = request.getParameter(PARAM_TXT_ORDERID);
        String txtOrderItemList = request.getParameter(PARAM_TXT_ITEMSTATUSLIST);
        List<OrderItemDTO> orderItems = new ArrayList<>();
        // Map<OrderItemID, LendStatus>
        Connection conn = null;
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
                        Object jsonOrderItemList = new Gson().fromJson(txtOrderItemList, Object.class);
                        OrderItemDAO orderItemDAO = new OrderItemDAO(conn);
                        BookDAO bookDAO = new BookDAO();
                        // Updating items
                        for (Object orderItem : ((ArrayList) jsonOrderItemList)) {
                            String txtOrderItemID = (String) ((LinkedTreeMap) orderItem).get(KEY_ORDERITEMID);
                            String txtLendStatus = (String) ((LinkedTreeMap) orderItem).get(KEY_LENDSTATUS);
                            String txtOrderIDofItem = (String) ((LinkedTreeMap) orderItem).get(KEY_ORDERID);
                            String txtLendDate = (String) ((LinkedTreeMap) orderItem).get(KEY_LENDDATE);
                            String txtReturnDate = (String) ((LinkedTreeMap) orderItem).get(KEY_RETURNDATE);
                            int orderItemID = Integer.parseInt(txtOrderItemID);
                            int lendStatus = Integer.parseInt(txtLendStatus);
                            // Status update
                            boolean updateStatusResult = orderItemDAO
                                    .updateOrderItemStatus(orderItemID, lendStatus, CONNECTION_USE_BATCH);
                            if (updateStatusResult) {
                                // Date update
                                Date currentDate = DateHelpers.getCurrentDate();
                                if ((txtLendDate.equals("")) && (lendStatus == ITEM_RECEIVED)) {
                                    boolean updateDateResult = orderItemDAO
                                            .updateOrderItemDate(orderItemID, currentDate,
                                                    DATE_RECEIVE, CONNECTION_USE_BATCH);
                                    if (updateDateResult) {
                                        // Update Librarian ID of Direct Order
                                        if (lendStatus == ITEM_RECEIVED) {
                                            DirectOrderDAO directOrderDAO = new DirectOrderDAO(conn);
                                            directOrderDAO.updateLibrarianOfOrder(orderID, librarian.getId(),
                                                    CONNECTION_USE_BATCH);
                                        }
                                    }
                                }
                                if ((txtReturnDate.equals(""))
                                        && ((lendStatus == ITEM_RETURNED) || (lendStatus == ITEM_OVERDUE_RETURNED))) {
                                    boolean updateDateResult = orderItemDAO
                                            .updateOrderItemDate(orderItemID, currentDate,
                                                    DATE_RETURN, CONNECTION_USE_BATCH);
                                }
                                conn.commit();
                                // Update book quantity after returning item
                                if ((lendStatus == ITEM_RETURNED)
                                        || (lendStatus == ITEM_OVERDUE_RETURNED)) {
                                    OrderItemDTO orderItemDTO = orderItemDAO.getOrderItemByID(orderItemID);
                                    String bookID = orderItemDTO.getBookID();
                                    BookDTO book = bookDAO.getBookById(bookID);
                                    bookDAO.updateQuantity(bookID, book.getQuantity() + 1);
                                }
                                OrderItemDTO orderItemDTO = orderItemDAO.getOrderItemByID(orderItemID);
                                orderItems.add(orderItemDTO);
                            }
                        } // end traversing order items
                        if (conn != null) {
                            conn.setAutoCommit(true);
                            conn.close();
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("UpdateDirectOrderServlet _ SQL: " + ex.getMessage());
            try {
                conn.rollback();
            } catch (SQLException exRollback) {
                LOGGER.error(exRollback.getMessage());
                log("UpdateDirectOrderServlet _ SQL: " + exRollback.getMessage());
            }
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("UpdateDirectOrderServlet _ Naming: " + ex.getMessage());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            log("UpdateDirectOrderServlet _ Exception: " + ex.getMessage());
        } finally {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            String json = gson.toJson(orderItems);
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
