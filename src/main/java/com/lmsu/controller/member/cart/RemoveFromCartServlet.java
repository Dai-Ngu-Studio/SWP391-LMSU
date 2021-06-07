package com.lmsu.controller.member.cart;

import com.lmsu.bean.member.CartObj;
import com.lmsu.controller.member.ViewBookDetailsServlet;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RemoveFromCartServlet", value = "/RemoveFromCartServlet")
public class RemoveFromCartServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(RemoveFromCartServlet.class);
    private static final String VIEW_CART_PAGE = "viewCart.jsp";
    private static final String VIEW_BOOK_DETAILS_CONTROLLER = "ViewBookDetailsServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = VIEW_CART_PAGE;
        String bookID = request.getParameter("bookPk");
        String isBrowsingBooks = request.getParameter("isBrowsingBooks");

        try {
            // 1. Check if session existed
            HttpSession session = request.getSession(false);
            if (session != null) {
                // 2. Check if session has cart
                CartObj cartObj = (CartObj) session.getAttribute("MEMBER_CART");
                if (cartObj != null) {
                    // 3. Remove book from cart
                    cartObj.removeBookFromCart(bookID);
                    // 4. Update cart on server
                    session.setAttribute("MEMBER_CART", cartObj);
                    // 5. Check what user was doing before removing
                    if (isBrowsingBooks.equals("true")) {
                        url = VIEW_BOOK_DETAILS_CONTROLLER + "?bookPk=" + bookID;
                    } else {
                        url = VIEW_CART_PAGE;
                    }
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
