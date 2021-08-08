package com.lmsu.controller.member.cart;

import com.lmsu.bean.member.CartObj;
import com.lmsu.users.UserDTO;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "OrderFormServlet", value = "/OrderFormServlet")
public class OrderFormServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(OrderFormServlet.class);
    private final String ORDER_FORM_PAGE = "orderform.jsp";
    private final String ORDER_REVIEW_PAGE = "revieworder.jsp";

    private final String ATTR_MEMBER_CART = "MEMBER_CART";
    private final String ATTR_LOGIN_USER = "LOGIN_USER";

    private final String ATTR_CHECKOUT_RESERVE = "CHECKOUT_RESERVE";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = ORDER_FORM_PAGE;

        try {
            HttpSession session = request.getSession();
            if (session != null) {
                CartObj cartObj = (CartObj) session.getAttribute(ATTR_MEMBER_CART);
                if (cartObj != null) {
                    UserDTO userDTO = (UserDTO) session.getAttribute(ATTR_LOGIN_USER);
                    // cart quantity = 0 means only reserved items are in cart/
                    // no need to check for empty cart here, already checked at viewCart
                    int cartQuantity = cartObj.getCartQuantity();
                    if (cartQuantity == 0) {
                        request.setAttribute(ATTR_CHECKOUT_RESERVE, true);
                        url = ORDER_REVIEW_PAGE;
                    }
                }
            }
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        processRequest(request, response);
    }
}
