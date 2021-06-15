package com.lmsu.controller;

import com.lmsu.orderdata.orderitems.OrderItemDAO;
import com.lmsu.orderdata.orderitems.OrderItemDTO;
import com.lmsu.users.UserDAO;
import com.lmsu.users.UserDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ShowProfileServlet", value = "/ShowProfileServlet")
public class ShowProfileServlet extends HttpServlet {

    private static final String RESULT_PAGE = "usersettings.jsp";
    static final Logger LOGGER = Logger.getLogger(ShowProfileServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //HttpSession session = request.getSession();

        String url = RESULT_PAGE;
        try {
            OrderItemDAO dao = new OrderItemDAO();
            dao.viewOrderItems();
            List<OrderItemDTO> list = dao.getOrderItemList();
            request.setAttribute("ORDER_ITEMS", list);

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
