package com.lmsu.controller.member.returndelivery;

import com.lmsu.authorbookmaps.AuthorBookMapDAO;
import com.lmsu.authorbookmaps.AuthorBookMapDTO;
import com.lmsu.authors.AuthorDTO;
import com.lmsu.bean.book.BookObj;
import com.lmsu.bean.member.CartObj;
import com.lmsu.bean.member.ReturnCartObj;
import com.lmsu.bean.orderdata.OrderItemObj;
import com.lmsu.books.BookDAO;
import com.lmsu.books.BookDTO;
import com.lmsu.controller.member.cart.AddBookToCartServlet;
import com.lmsu.orderdata.orderitems.OrderItemDAO;
import com.lmsu.orderdata.orderitems.OrderItemDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "AddBookToReturnCartServlet", value = "/AddBookToReturnCartServlet")
public class AddBookToReturnCartServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(AddBookToReturnCartServlet.class);
    private static final String VIEW_USER_SETTING_CONTROLLER = "ShowProfileServlet";

    private final String PARAM_ITEMID = "orderItemPk";
    private final String PARAM_BOOKID = "bookPk";

    private final String ATTR_RETURN_CART = "RETURN_CART";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = VIEW_USER_SETTING_CONTROLLER;
        String bookID = request.getParameter(PARAM_BOOKID);
        String orderItemsID = request.getParameter(PARAM_ITEMID);

        try {
            // 1. Check if session existed (default create one if not exist)
            HttpSession session = request.getSession();
            // 2. Check if session has cart
            ReturnCartObj returnCartObj = (ReturnCartObj) session.getAttribute(ATTR_RETURN_CART);
            if (returnCartObj == null) {
                returnCartObj = new ReturnCartObj();
            }
            // 3. Get the book which member want to add to cart
            BookDAO bookDAO = new BookDAO();
            OrderItemDAO orderItemDAO = new OrderItemDAO();
            // Get BookDTO
            BookDTO bookDTO = bookDAO.getBookById(bookID);
            //get OrderItemDTO
            OrderItemDTO orderItemDTO = orderItemDAO.getOrderItemByID(Integer.valueOf(orderItemsID));
            if (bookDTO != null && orderItemDTO != null) {
                // Create OrderItems Bean
                OrderItemObj orderItemObj = new OrderItemObj();
                orderItemObj.setId(Integer.valueOf(orderItemsID));
                orderItemObj.setOrderID(orderItemObj.getOrderID());
                orderItemObj.setReturnOrderID(orderItemObj.getReturnOrderID());
                orderItemObj.setMemberID(orderItemObj.getMemberID());
                orderItemObj.setBookID(bookID);
                orderItemObj.setTitle(bookDTO.getTitle());
                orderItemObj.setLendStatus(orderItemObj.getLendStatus());
                orderItemObj.setReturnDeadline(orderItemObj.getReturnDeadline());
                orderItemObj.setLendDate(orderItemObj.getLendDate());
                orderItemObj.setReturnDate(orderItemObj.getReturnDate());
                // 4. Add book to return cart
                returnCartObj.addBookToReturnCart(orderItemObj);
                // 5. Save cart on server
                session.setAttribute(ATTR_RETURN_CART, returnCartObj);
                // 6. Member continues checking book
                url = VIEW_USER_SETTING_CONTROLLER + "?" + PARAM_ITEMID + "=" + orderItemsID;
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("AddBookToReturnCartServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("AddBookToReturnCartServlet _ Naming: " + ex.getMessage());
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
