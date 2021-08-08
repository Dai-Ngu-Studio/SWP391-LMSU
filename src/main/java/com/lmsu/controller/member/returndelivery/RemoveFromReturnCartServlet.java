package com.lmsu.controller.member.returndelivery;

import com.lmsu.bean.member.ReturnCartObj;
import com.lmsu.controller.member.cart.RemoveFromCartServlet;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RemoveFromReturnCartServlet", value = "/RemoveFromReturnCartServlet")
public class RemoveFromReturnCartServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(RemoveFromCartServlet.class);
    private static final String VIEW_RETURN_CART_PAGE = "viewReturnCart.jsp";
    private static final String VIEW_BOOK_DETAILS_CONTROLLER = "ViewBookDetailsServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = VIEW_BOOK_DETAILS_CONTROLLER;
        String orderItemPk = request.getParameter("orderItemPk");

        try {
            // 1. Check if session existed
            HttpSession session = request.getSession(false);
            if (session != null) {
                // 2. Check if session has cart
                ReturnCartObj returnCartObj = (ReturnCartObj) session.getAttribute("RETURN_CART");
                if (returnCartObj != null) {
                    // 3. Remove book from cart
                    returnCartObj.removeBookFromReturnCart(Integer.valueOf(orderItemPk));
                    // 4. Update cart on server
                    session.setAttribute("RETURN_CART", returnCartObj);
                    url = VIEW_RETURN_CART_PAGE;
                } // end if cart existed
            }
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
