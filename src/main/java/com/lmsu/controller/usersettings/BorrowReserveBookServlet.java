package com.lmsu.controller.usersettings;

import com.lmsu.bean.orderdata.OrderItemObj;
import com.lmsu.books.BookDAO;
import com.lmsu.books.BookDTO;
import com.lmsu.controller.member.ViewBookDetailsServlet;
import com.lmsu.orderdata.orderitems.OrderItemDAO;
import com.lmsu.orderdata.orderitems.OrderItemDTO;
import com.lmsu.users.UserDTO;
import com.lmsu.utils.DateHelpers;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "BorrowReserveBookServlet", value = "/BorrowReserveBookServlet")
public class BorrowReserveBookServlet extends HttpServlet {

    private static final String SHOW_PROFILE_CONTROLLER = "ShowProfileServlet";
    static final Logger LOGGER = Logger.getLogger(BorrowReserveBookServlet.class);

    private final int ITEM_PENDING = 0;

    private final int ITEM_RECEIVED = 2;
    private final int ITEM_RESERVED = 10;

    private final boolean ATTR_BOOK_BORROWED = true;
    private final String ATTR_MEMBER_TOTAL_ACTIVE_BORROWS = "MEMBER_TOTAL_ACTIVE_BORROWS";
    private final String ATTR_MEMBER_BOOK_BORROW_STATUS = "MEMBER_BOOK_BORROW_STATUS";
    private final String ATTR_COMMENT_LIST = "COMMENT_LIST";
    private final String ATTR_COMMENT_AMOUNT = "COMMENT_AMOUNT";
    private final String ATTR_LOGIN_USER = "LOGIN_USER";
    private final String ATTR_BOOK_OBJECT = "BOOK_OBJECT";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserDTO userDTO = (UserDTO) session.getAttribute(ATTR_LOGIN_USER);
        String url = SHOW_PROFILE_CONTROLLER;
        String bookID = request.getParameter("bookPk");

        try {
            if(userDTO != null){
                OrderItemDAO orderItemDAO = new OrderItemDAO();
                OrderItemDTO orderItemDTO = orderItemDAO.getMemberItemFromBookID(bookID, userDTO.getId());
                Date returnDeadline = DateHelpers.getDeadlineDate(DateHelpers.getCurrentDate(), 14);
                boolean result = orderItemDAO.borrowReserveBook(orderItemDTO.getId(), ITEM_RECEIVED, returnDeadline);

            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            log("BorrowReserveBookServlet _ SQL: " + e.getMessage());
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            log("BorrowReserveBookServlet _ Naming: " + e.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
