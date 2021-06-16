package com.lmsu.controller.member.cart;

import com.lmsu.bean.member.BookObj;
import com.lmsu.bean.member.CartObj;
import com.lmsu.controller.AddBookServlet;
import com.lmsu.orderdata.orderitems.OrderItemDTO;
import com.lmsu.orderdata.orders.OrderDAO;
import com.lmsu.orderdata.orders.OrderDTO;
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
import java.util.Map;

@WebServlet(name = "CheckoutServlet", value = "/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(CheckoutServlet.class);
    private final String SHOW_BOOK_CATALOG_CONTROLLER = "ShowBookCatalogServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = SHOW_BOOK_CATALOG_CONTROLLER;

        try {
            // 1. Check if session existed
            HttpSession session = request.getSession(false);
            if (session != null) {
                // 2. Check if cart existed
                CartObj cartObj = (CartObj) session.getAttribute("MEMBER_CART");
                if (cartObj != null) {
                    // 3. Check if items existed
                    if (cartObj.getItems() != null) {
                        Map<String, BookObj> cartItems = cartObj.getItems();

                        // 4. Get user, current date
                        UserDTO userDTO = (UserDTO) session.getAttribute("LOGIN_USER");
                        OrderDAO orderDAO = new OrderDAO();
                        Date currentDate = DateHelpers.getCurrentDate();

                        // 5. Create new order
                        orderDAO.addOrder(userDTO.getId(), currentDate, true);

                        // 6. Traverse items in cart
                        for (String bookID : cartItems.keySet()) {
                            cartItems.get(bookID);
                            OrderItemDTO orderItemDTO = new OrderItemDTO();
                            orderItemDTO.setOrderID(

                        }
                    }// end if items existed
                    else {

                    }// end if items not existed
                }// end if cart existed
                else {

                }// end if cart not existed
            }// end if session existed
            else {

            }// end if session not existed
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("CheckoutServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("CheckoutServlet _ Naming: " + ex.getMessage());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            log("CheckoutServlet _ Exception: " + ex.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request,response);
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
