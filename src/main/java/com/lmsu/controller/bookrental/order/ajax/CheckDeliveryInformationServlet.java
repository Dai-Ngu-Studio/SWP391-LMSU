package com.lmsu.controller.bookrental.order.ajax;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lmsu.orderdata.deliveryorders.DeliveryOrderDAO;
import com.lmsu.orderdata.deliveryorders.DeliveryOrderDTO;
import com.lmsu.orderdata.orders.OrderDAO;
import com.lmsu.orderdata.orders.OrderDTO;
import com.lmsu.users.UserDAO;
import com.lmsu.users.UserDTO;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "CheckDeliveryInformationServlet", value = "/CheckDeliveryInformationServlet")
public class CheckDeliveryInformationServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(CheckDeliveryInformationServlet.class);

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
        Pair<OrderDTO, DeliveryOrderDTO> deliveryOrderInformation = null;
        try {
            int orderID = Integer.parseInt(txtOrderID);
            OrderDAO orderDAO = new OrderDAO();
            OrderDTO order = orderDAO.getOrderFromID(orderID);
            UserDAO userDAO = new UserDAO();
            if (order != null) {
                DeliveryOrderDAO deliveryOrderDAO = new DeliveryOrderDAO();
                DeliveryOrderDTO deliveryOrder = deliveryOrderDAO.getDeliveryOrderFromOrderID(orderID);

                if (deliveryOrder != null) {
                    String managerID = deliveryOrder.getManagerID();
                    if (managerID != null) {
                        UserDTO manager = userDAO.getUserByID(deliveryOrder.getManagerID());
                        if (manager != null) {
                            deliveryOrder.setManager(manager);
                        }
                    }
                    deliveryOrderInformation = new Pair<OrderDTO, DeliveryOrderDTO>(order, deliveryOrder);
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("CheckDeliveryInformationServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("CheckDeliveryInformationServlet _ Naming: " + ex.getMessage());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            log("CheckDeliveryInformationServlet _ Exception: " + ex.getMessage());
        } finally {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            String json = gson.toJson(deliveryOrderInformation);
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
