package com.lmsu.controller.usersettings;

import com.lmsu.bean.orderdata.OrderItemObj;
import com.lmsu.books.BookDAO;
import com.lmsu.books.BookDTO;
import com.lmsu.orderdata.deliveryorders.DeliveryOrderDAO;
import com.lmsu.orderdata.deliveryorders.DeliveryOrderDTO;
import com.lmsu.orderdata.directorders.DirectOrderDAO;
import com.lmsu.orderdata.directorders.DirectOrderDTO;
import com.lmsu.orderdata.orderitems.OrderItemDAO;
import com.lmsu.orderdata.orderitems.OrderItemDTO;
import com.lmsu.orderdata.orders.OrderDAO;
import com.lmsu.orderdata.orders.OrderDTO;
import com.lmsu.renewalrequests.RenewalRequestDAO;
import com.lmsu.renewalrequests.RenewalRequestDTO;
import com.lmsu.users.UserDTO;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet(name = "ShowProfileServlet", value = "/ShowProfileServlet")
public class ShowProfileServlet extends HttpServlet {

    private static final String PROFILE_PAGE = "usersettings.jsp";
    static final Logger LOGGER = Logger.getLogger(ShowProfileServlet.class);

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

    private final String ATTR_LOGIN_USER = "LOGIN_USER";
    private final String ATTR_MEMBER_ORDER_ITEMS = "MEMBER_ORDER_ITEMS";
    private final String ATTR_MEMBER_RESERVE_ITEMS = "MEMBER_RESERVE_ITEMS";
    private final String ATTR_RENEWAL_MAP_LIST = "RENEWAL_MAP_LIST";
    private final String ATTR_RENEWAL_MAP_STATUS = "RENEWAL_MAP_STATUS";
    private final String ATTR_QUANTITY_MAP_LIST = "QUANTITY_MAP_LIST";
    private final String ATTR_MEMBER_ORDER_LIST = "MEMBER_ORDER_LIST";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String url = PROFILE_PAGE;
        try {
            if (session != null) {
                UserDTO userDTO = (UserDTO) session.getAttribute(ATTR_LOGIN_USER);
                if (userDTO != null) {
                    BookDAO bookDAO = new BookDAO();
                    OrderDAO orderDAO = new OrderDAO();
                    OrderItemDAO orderItemDAO = new OrderItemDAO();
                    RenewalRequestDAO renewalRequestDAO = new RenewalRequestDAO();
                    DeliveryOrderDAO deliveryOrderDAO = new DeliveryOrderDAO();
                    DirectOrderDAO directOrderDAO = new DirectOrderDAO();
                    //----------------------------------------------------
                    // Order Item Statuses (not including cancelled, rejected or reserved)
                    List<Integer> activeItemStatuses = new ArrayList<Integer>(
                            Arrays.asList(
                                    ITEM_PENDING, ITEM_APPROVED, ITEM_RECEIVED,
                                    ITEM_RETURN_SCHEDULED, ITEM_RETURNED, ITEM_OVERDUE,
                                    ITEM_OVERDUE_RETURN_SCHEDULED, ITEM_OVERDUE_RETURNED, ITEM_LOST
                            ));
                    //----------------------------------------------------
                    // Get Order Items of Member
                    orderDAO.getOrdersFromMember(ORDER_BOTH_METHOD, userDTO.getId(), NO_ORDER_STATUS_SPECIFIED);
                    List<OrderDTO> orders = orderDAO.getOrderList();
                    List<OrderItemObj> validOrderItems = new ArrayList<>();
                    List<OrderItemObj> reservedItems = new ArrayList<>();
                    Map<Integer, Integer> mapStatus = new HashMap<>();
                    Map<Pair<DirectOrderDTO, DeliveryOrderDTO>, Pair<OrderDTO, List<OrderItemObj>>> detailedOrders =
                            new HashMap<Pair<DirectOrderDTO, DeliveryOrderDTO>, Pair<OrderDTO, List<OrderItemObj>>>();
                    if (orders != null) {
                        for (OrderDTO order : orders) {
                            List<OrderItemObj> itemsOfOrder = new ArrayList<>();
                            orderItemDAO.clearOrderItemList();
                            orderItemDAO.getOrderItemsFromOrderID(order.getId());
                            List<OrderItemDTO> orderItems = orderItemDAO.getOrderItemList();
                            if (orderItems != null) {
                                for (OrderItemDTO orderItem : orderItems) {
                                    BookDTO bookDTO = bookDAO.getBookById(orderItem.getBookID());
                                    RenewalRequestDTO renewalRequestDTO = renewalRequestDAO.getRenewalByItemID(orderItem.getId());
                                    OrderItemObj orderitemObj = new OrderItemObj();
                                    orderitemObj.setId(orderItem.getId());
                                    orderitemObj.setOrderID(orderItem.getOrderID());
                                    orderitemObj.setBookID(orderItem.getBookID());
                                    orderitemObj.setTitle(bookDTO.getTitle());
                                    orderitemObj.setLendStatus(orderItem.getLendStatus());
                                    orderitemObj.setReturnDeadline(orderItem.getReturnDeadline());
                                    orderitemObj.setLendDate(orderItem.getLendDate());
                                    orderitemObj.setReturnDate(orderItem.getReturnDate());
                                    // check if item is not cancelled, rejected
                                    for (int activeItemStatus : activeItemStatuses) {
                                        if (orderItem.getLendStatus() == activeItemStatus) {
                                            validOrderItems.add(orderitemObj);
                                            break;
                                        }
                                    } // end traverse statuses
                                    // check if item is reserved
                                    if (orderItem.getLendStatus() == ITEM_RESERVED) {
                                        reservedItems.add(orderitemObj);
                                    }
                                    itemsOfOrder.add(orderitemObj);
                                    int orderID = order.getId();
                                    DirectOrderDTO directOrderDTO = new DirectOrderDTO();
                                    DeliveryOrderDTO deliveryOrderDTO = new DeliveryOrderDTO();
                                    if (!order.isLendMethod()) {
                                        directOrderDTO = directOrderDAO.getDirectOrderFromOrderID(orderID);
                                    } else {
                                        deliveryOrderDTO = deliveryOrderDAO.getDeliveryOrderFromOrderID(orderID);
                                    }
                                    Pair<DirectOrderDTO, DeliveryOrderDTO> orderType = new Pair<>(directOrderDTO, deliveryOrderDTO);
                                    Pair<OrderDTO, List<OrderItemObj>> orderInformation = new Pair<>(order, itemsOfOrder);
                                    detailedOrders.put(orderType, orderInformation);

                                    //check if item already send renewal
                                    if (renewalRequestDTO != null) {
                                        if (renewalRequestDTO.getApprovalStatus() == 0) {
                                            mapStatus.put(orderItem.getId(), 0);
                                        }
                                    }
                                } // end traverse items
                            }
                        } // end traverse orders
                    }
                    request.setAttribute(ATTR_MEMBER_ORDER_LIST, detailedOrders);
                    request.setAttribute(ATTR_MEMBER_ORDER_ITEMS, validOrderItems);
                    request.setAttribute(ATTR_RENEWAL_MAP_STATUS, mapStatus);
                    //----------------------------------------------------
                    // Renewal
                    RenewalRequestDAO renewalDAO = new RenewalRequestDAO();
                    LinkedHashMap<Integer, Integer> renewalMap = new LinkedHashMap<>();
                    for (OrderItemObj orderitemObj : validOrderItems) {
                        renewalMap.put(orderitemObj.getId(),
                                renewalDAO.countRenewalRequestByItemID(orderitemObj.getId()));
                    }
                    request.setAttribute(ATTR_RENEWAL_MAP_LIST, renewalMap);
                    //----------------------------------------------------
                    // Reserve
                    request.setAttribute(ATTR_MEMBER_RESERVE_ITEMS, reservedItems);
                    LinkedHashMap<Integer, Integer> quantityMap = new LinkedHashMap<>();
                    for (OrderItemObj orderitemObj : reservedItems) {
                        quantityMap.put(
                                orderitemObj.getId(),
                                bookDAO.getQuantityByBookID(orderitemObj.getBookID())
                        );
                    }
                    request.setAttribute(ATTR_QUANTITY_MAP_LIST, quantityMap);
                }
            }
            url = PROFILE_PAGE;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            log("ShowProfileServlet _ SQL: " + e.getMessage());
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            log("ShowProfileServlet _ Naming: " + e.getMessage());
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
