package com.lmsu.controller.member.cart;

import com.lmsu.bean.member.CartObj;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ShowCartServlet", value = "/ShowCartServlet")
public class ShowCartServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(ShowCartServlet.class);
    private final static String VIEW_CART_PAGE = "viewCart.jsp";

    // No uses for this servlet yet

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = VIEW_CART_PAGE;

        try {
//            // 1. Check if session existed (default create one if not exist)
//            HttpSession session = request.getSession();
//            // 2. Check if session has cart
//            CartObj cartObj = (CartObj) session.getAttribute("MEMBER_CART");
//            if (cartObj == null) {
//                cartObj = new CartObj();
//            }
//            // 3. Save cart on server
//            session.setAttribute("MEMBER_CART", cartObj);
//            // 4. Redirect to cart
//            url = VIEW_CART_PAGE;
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }
}
