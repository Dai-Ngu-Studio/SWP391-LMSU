package com.lmsu.controller.librarian.direct;

import com.lmsu.bean.orderdata.OrderItemObj;
import com.lmsu.bean.orderdata.OrderObj;
import com.lmsu.orderdata.orderitems.OrderItemDAO;
import com.lmsu.orderdata.orders.OrderDAO;
import com.lmsu.orderdata.orders.OrderDTO;
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
            //--------------------------------------------------
            // default show pending and overdue
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
            for (OrderDTO order : orders) {

            }
            // temporarily use orderDTO
            // need to create bean for order to display name of borrower, ...
            // temporarily use list
            // need to use map to show items of order
            if (orders != null) {
                request.setAttribute(ATTR_ORDER_LIST, orders);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("ShowDirectOrderServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("ShowDirectOrderServlet _ Naming: " + ex.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request,response);
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
