package com.lmsu.controller.member.returndelivery;

import com.lmsu.bean.member.CartObj;
import com.lmsu.bean.member.ReturnCartObj;
import com.lmsu.controller.member.cart.OrderFormServlet;
import com.lmsu.users.UserDTO;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ReturnFormServlet", value = "/ReturnFormServlet")
public class ReturnFormServlet extends HttpServlet {
    static final Logger LOGGER = Logger.getLogger(ReturnFormServlet.class);
    private final String RETURN_FORM_PAGE = "returnform.jsp";

    private final String ATTR_RETURN_CART = "RETURN_CART";
    private final String INDEX_CONTROLLER = "IndexServlet";

    private final String ATTR_CHECKOUT_RESERVE = "CHECKOUT_RESERVE";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = INDEX_CONTROLLER;

        try {
            HttpSession session = request.getSession();
            if (session != null) {
                ReturnCartObj returnCartObj = (ReturnCartObj) session.getAttribute(ATTR_RETURN_CART);
                if (returnCartObj != null) {
                    session.setAttribute("RETURN_BOOK", true);
                    url = RETURN_FORM_PAGE;
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
