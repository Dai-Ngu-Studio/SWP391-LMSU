package com.lmsu.controller;

import com.lmsu.bean.member.OrderItemObj;
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

    private static final String PROFILE_PAGE = "usersettings.jsp";
    static final Logger LOGGER = Logger.getLogger(ShowProfileServlet.class);

    // temporary
    private final List<Integer> NO_LEND_STATUS_SPECIFIED = null;

    private final String ATTR_LOGIN_USER = "LOGIN_USER";
    private final String ATTR_ORDER_ITEMS = "ORDER_ITEMS";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String url = PROFILE_PAGE;
        try {
            if (session != null) {
                UserDTO userDTO = (UserDTO) session.getAttribute(ATTR_LOGIN_USER);
                if (userDTO != null) {
                    OrderItemDAO orderItemDAO = new OrderItemDAO();
                    orderItemDAO.getOrderItemsFromMember(userDTO.getId(), NO_LEND_STATUS_SPECIFIED);

                    List<OrderItemDTO> orderItemList = orderItemDAO.getOrderItemList();
                    List<OrderItemObj> orderItemObjList = new ArrayList<>();

                    BookDAO bookDAO = new BookDAO();

                    if (orderItemList != null) {
                        for (OrderItemDTO orderitemDTO : orderItemList) {
                            BookDTO book_dto = bookDAO.getBookById(orderitemDTO.getBookID());

                            OrderItemObj orderitemObj = new OrderItemObj();
                            orderitemObj.setId(orderitemDTO.getId());
                            orderitemObj.setOrderID(orderitemDTO.getOrderID());
                            orderitemObj.setMemberID(orderitemDTO.getMemberID());
                            orderitemObj.setBookID(orderitemDTO.getBookID());
                            orderitemObj.setTitle(book_dto.getTitle());
                            orderitemObj.setLendStatus(orderitemDTO.getLendStatus());
                            orderitemObj.setReturnDeadline(orderitemDTO.getReturnDeadline());
                            orderitemObj.setLendDate(orderitemDTO.getLendDate());
                            orderitemObj.setReturnDate(orderitemDTO.getReturnDate());
                            orderItemObjList.add(orderitemObj);
                        }
                        request.setAttribute(ATTR_ORDER_ITEMS, orderItemObjList);
                    }
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
