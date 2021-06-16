package com.lmsu.controller;

import com.lmsu.bean.member.OrderItemsObj;
import com.lmsu.books.BookDAO;
import com.lmsu.books.BookDTO;
import com.lmsu.orderdata.orderitems.OrderItemDAO;
import com.lmsu.orderdata.orderitems.OrderItemDTO;
import com.lmsu.users.UserDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ShowProfileServlet", value = "/ShowProfileServlet")
public class ShowProfileServlet extends HttpServlet {

    private static final String RESULT_PAGE = "usersettings.jsp";
    static final Logger LOGGER = Logger.getLogger(ShowProfileServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String url = RESULT_PAGE;
        try {

            UserDTO user_dto = (UserDTO) session.getAttribute("LOGIN_USER");
            OrderItemDAO orderItemDAO = new OrderItemDAO();
            orderItemDAO.viewOrderItems(user_dto.getId());
            BookDAO book_dao = new BookDAO();

            List<OrderItemDTO> orderItemList = orderItemDAO.getOrderItemList();
            List<OrderItemsObj> orderItemsObjList = new ArrayList<>();
            //OrderItemDTO orderitems_dto = new OrderItemDTO();

            if(session != null){
                if(orderItemList != null){
                    for(OrderItemDTO orderitemDTO: orderItemList){
                       BookDTO book_dto = book_dao.getBookById(orderitemDTO.getBookID());

                        OrderItemsObj orderitemsObj = new OrderItemsObj(orderitemDTO.getId(), orderitemDTO.getOrderID(),
                               orderitemDTO.getMemberID(), orderitemDTO.getBookID(), book_dto.getTitle(),
                               orderitemDTO.getLendStatus(), orderitemDTO.getReturnDeadline(), orderitemDTO.getLendDate(),
                               orderitemDTO.getReturnDate());
                       orderItemsObjList.add(orderitemsObj);
                    }
                    request.setAttribute("ORDER_ITEMS", orderItemsObjList);
                }
            }
            url = RESULT_PAGE;
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
