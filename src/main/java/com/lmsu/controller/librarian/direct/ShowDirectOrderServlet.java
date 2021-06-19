package com.lmsu.controller.librarian.direct;

import com.lmsu.bean.orderdata.OrderItemObj;
import com.lmsu.bean.orderdata.OrderObj;
import com.lmsu.books.BookDAO;
import com.lmsu.books.BookDTO;
import com.lmsu.orderdata.orderitems.OrderItemDAO;
import com.lmsu.orderdata.orderitems.OrderItemDTO;
import com.lmsu.orderdata.orders.OrderDAO;
import com.lmsu.orderdata.orders.OrderDTO;
import com.lmsu.users.UserDAO;
import com.lmsu.users.UserDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet(name = "ShowDirectOrderServlet", value = "/ShowDirectOrderServlet")
public class ShowDirectOrderServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(ShowDirectOrderServlet.class);
    private final String BOOK_RENTAL_MANAGEMENT_PAGE = "bookrentalmanagement.jsp";

    private final boolean DIRECT_METHOD = false;
    private final int ORDER_CANCELLED = -1;
    private final int ORDER_PENDING = 0;
    private final int ORDER_APPROVED = 1;
    private final int ORDER_RECEIVED = 2;
    private final int ORDER_RETURNED = 3;
    private final int ORDER_OVERDUE = 4;
    private final int ORDER_REJECTED = 5;

    private final String ATTR_ORDER_LIST = "ORDER_LIST";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = BOOK_RENTAL_MANAGEMENT_PAGE;
        try {
            OrderDAO orderDAO = new OrderDAO();
            OrderItemDAO orderItemDAO = new OrderItemDAO();
            UserDAO userDAO = new UserDAO();
            BookDAO bookDAO = new BookDAO();

            //--------------------------------------------------
            // default show pending and overdue (need to change to depend on parameters and not hard-coded)
            orderDAO
                    .viewOrders(
                            DIRECT_METHOD,
                            new ArrayList<Integer>(
                                    Arrays.asList(
                                            ORDER_PENDING, ORDER_OVERDUE
                                    )));
            //--------------------------------------------------
            List<OrderDTO> orders = orderDAO.getOrderList();
            Map<OrderObj, List<OrderItemObj>> detailedOrders = new LinkedHashMap<OrderObj, List<OrderItemObj>>();
            for (OrderDTO orderDTO : orders) {
                UserDTO userDTO = userDAO.getUserByID(orderDTO.getMemberID());
                OrderObj orderObj = new OrderObj();
                orderObj.setId(orderDTO.getId());
                orderObj.setMemberID(orderDTO.getMemberID());
                orderObj.setMemberName(userDTO.getName());
                orderObj.setOrderDate(orderDTO.getOrderDate());
                orderObj.setLendMethod((orderDTO.isLendMethod()));
                orderObj.setActiveStatus(orderDTO.getActiveStatus());

                orderItemDAO.getOrderItemsFromOrderID(orderDTO.getId());
                List<OrderItemDTO> orderItems = orderItemDAO.getOrderItemList();
                List<OrderItemObj> orderItemObjs = new ArrayList<>();
                for (OrderItemDTO orderItemDTO : orderItems) {
                    BookDTO bookDTO = bookDAO.getBookById(orderItemDTO.getBookID());
                    OrderItemObj orderItemObj = new OrderItemObj();
                    orderItemObj.setId(orderItemDTO.getId());
                    orderItemObj.setOrderID(orderItemDTO.getOrderID());
                    orderItemObj.setMemberID(orderItemDTO.getMemberID());
                    orderItemObj.setBookID(orderItemDTO.getBookID());
                    orderItemObj.setTitle(bookDTO.getTitle());
                    orderItemObj.setLendStatus(orderItemDTO.getLendStatus());
                    orderItemObj.setReturnDeadline(orderItemDTO.getReturnDeadline());
                    orderItemObj.setLendDate(orderItemDTO.getLendDate());
                    orderItemObj.setReturnDate(orderItemDTO.getLendDate());

                    orderItemObjs.add(orderItemObj);
                }
                detailedOrders.put(orderObj, orderItemObjs);
            }
            request.setAttribute(ATTR_ORDER_LIST, detailedOrders);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("ShowDirectOrderServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("ShowDirectOrderServlet _ Naming: " + ex.getMessage());
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
