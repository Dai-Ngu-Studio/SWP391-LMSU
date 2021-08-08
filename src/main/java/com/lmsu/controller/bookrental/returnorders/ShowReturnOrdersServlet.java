package com.lmsu.controller.bookrental.returnorders;

import com.lmsu.books.BookDAO;
import com.lmsu.orderdata.deliveryorders.DeliveryOrderDAO;
import com.lmsu.orderdata.deliveryorders.DeliveryOrderDTO;
import com.lmsu.orderdata.directorders.DirectOrderDAO;
import com.lmsu.orderdata.directorders.DirectOrderDTO;
import com.lmsu.orderdata.orderitems.OrderItemDAO;
import com.lmsu.orderdata.orderitems.OrderItemDTO;
import com.lmsu.orderdata.orders.OrderDAO;
import com.lmsu.orderdata.orders.OrderDTO;
import com.lmsu.users.UserDAO;
import com.lmsu.users.UserDTO;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet(name = "ShowReturnOrdersServlet", value = "/ShowReturnOrdersServlet")
public class ShowReturnOrdersServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(ShowReturnOrdersServlet.class);
    private final String RETURN_MANAGEMENT_PAGE = "returnmanagement.jsp";

    private final int BOTH_METHOD = 5;
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

    private final String ATTR_ORDER_LIST = "ORDER_LIST";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = RETURN_MANAGEMENT_PAGE;
        try {
            OrderDAO orderDAO = new OrderDAO();
            OrderItemDAO orderItemDAO = new OrderItemDAO();
            DirectOrderDAO directOrderDAO = new DirectOrderDAO();
            DeliveryOrderDAO deliveryOrderDAO = new DeliveryOrderDAO();
            UserDAO userDAO = new UserDAO();
            BookDAO bookDAO = new BookDAO();

            //--------------------------------------------------
            orderDAO
                    .viewOrders(
                            BOTH_METHOD,
                            new ArrayList<Integer>(
                                    Arrays.asList(
                                            ORDER_RETURN_SCHEDULED, ORDER_RETURN_RETURNED
                                    )));
            //--------------------------------------------------
            List<OrderDTO> orders = orderDAO.getOrderList();
            Map<Pair<DirectOrderDTO, DeliveryOrderDTO>, Pair<OrderDTO, List<OrderItemDTO>>> detailedOrders = new HashMap<Pair<DirectOrderDTO, DeliveryOrderDTO>, Pair<OrderDTO, List<OrderItemDTO>>>();
            if (orders != null) {
                for (OrderDTO order : orders) {
                    order.setMember(userDAO.getUserByID(order.getMemberID()));

                    orderItemDAO.clearOrderItemList();
                    orderItemDAO.getOrderItemsFromOrderID(order.getId());
                    List<OrderItemDTO> orderItems = orderItemDAO.getOrderItemList();
                    if (orderItems != null) {
                        for (OrderItemDTO orderItemDTO : orderItems) {
                            orderItemDTO.setBook(bookDAO.getBookById(orderItemDTO.getBookID()));
                        }
                        int orderID = order.getId();
                        DirectOrderDTO directOrder = new DirectOrderDTO();
                        DeliveryOrderDTO deliveryOrder = new DeliveryOrderDTO();
                        directOrder.setReturnOrder(false);
                        if (order.isLendMethod() == false) {
                            directOrder = directOrderDAO.getDirectOrderFromOrderID(orderID);
                            String librarianID = (directOrder != null) ? directOrder.getLibrarianID() : null;
                            if (librarianID != null) {
                                UserDTO librarian = userDAO.getUserByID(librarianID);
                                directOrder.setLibrarian(librarian);
                            }
                        } else {
                            deliveryOrder = deliveryOrderDAO.getDeliveryOrderFromOrderID(orderID);
                            String managerID = (deliveryOrder != null) ? deliveryOrder.getManagerID() : null;
                            if (managerID != null) {
                                UserDTO manager = userDAO.getUserByID(managerID);
                                deliveryOrder.setManager(manager);
                            }
                        }
                        Pair<DirectOrderDTO, DeliveryOrderDTO> orderType = new Pair<>(directOrder, deliveryOrder);
                        Pair<OrderDTO, List<OrderItemDTO>> orderInformation = new Pair<>(order, orderItems);
                        detailedOrders.put(orderType, orderInformation);
                    }
                }
            }
            request.setAttribute(ATTR_ORDER_LIST, detailedOrders);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("ShowOrdersServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("ShowOrdersServlet _ Naming: " + ex.getMessage());
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
