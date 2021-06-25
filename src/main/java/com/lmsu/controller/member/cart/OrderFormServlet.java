package com.lmsu.controller.member.cart;

import com.lmsu.bean.book.BookObj;
import com.lmsu.bean.member.CartObj;
import com.lmsu.users.UserDTO;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Map;

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
                    int cartQuantity = cartObj.getCartQuantity();
                    int reserveQuantity = 0;
                    Map<String, BookObj> cartItems = cartObj.getItems();
                    for (String bookID : cartItems.keySet()) {
                        if (cartItems.get(bookID).getQuantity() == 0) {
                            reserveQuantity++;
                        }
                    }
                    if (cartQuantity == reserveQuantity) {
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
