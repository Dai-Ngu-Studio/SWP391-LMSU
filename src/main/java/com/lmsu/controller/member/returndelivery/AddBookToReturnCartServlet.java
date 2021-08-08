package com.lmsu.controller.member.returndelivery;

import com.lmsu.bean.member.ReturnCartObj;
import com.lmsu.books.BookDAO;
import com.lmsu.books.BookDTO;
import com.lmsu.orderdata.orderitems.OrderItemDAO;
import com.lmsu.orderdata.orderitems.OrderItemDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

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
        String orderItemID = request.getParameter(PARAM_ITEMID);

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
            BookDTO book = bookDAO.getBookById(bookID);
            //get OrderItemDTO
            OrderItemDTO orderItem = orderItemDAO.getOrderItemByID(Integer.parseInt(orderItemID));
            if (book != null && orderItem != null) {
                // Create OrderItems Bean
                // 4. Add book to return cart
                returnCartObj.addBookToReturnCart(orderItem);
                // 5. Save cart on server
                session.setAttribute(ATTR_RETURN_CART, returnCartObj);
                // 6. Member continues checking book
                url = VIEW_USER_SETTING_CONTROLLER + "?" + PARAM_ITEMID + "=" + orderItemID;
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
