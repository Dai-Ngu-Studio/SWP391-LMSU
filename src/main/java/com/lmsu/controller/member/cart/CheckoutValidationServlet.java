package com.lmsu.controller.member.cart;

import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "CheckoutValidationServlet", value = "/CheckoutValidationServlet")
public class CheckoutValidationServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(CheckoutValidationServlet.class);
    private final static String VIEW_CART_PAGE = "viewCart.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = VIEW_CART_PAGE;
        String receiverName = request.getParameter("txtReceiverName");
        String phoneNumber = request.getParameter("txtPhoneNumber");
        String addressOne = request.getParameter("txtAddressOne");
        String addressTwo = request.getParameter("txtAddressTwo");
        Map<String, String> errors = new HashMap<>();

        try {
            if (receiverName != null) {
                if (receiverName.trim().length() < 2
                        || receiverName.trim().length() > 60) {
                    errors.put("errorReceiverName", "inputReceiverName");
                } else if (!receiverName.matches("^[\\p{L}\\s]{2,60}$")) {
                    errors.put("errorReceiverName", "inputReceiverName");
                }
            }

            if (phoneNumber != null) {
                if (phoneNumber.trim().length() < 10
                        || phoneNumber.trim().length() > 12
                        || phoneNumber.trim().matches("^[^0-9]$")) {
                    errors.put("errorPhoneNumber", "inputPhoneNumber");
                }
            }

            if (addressOne != null) {
                if (addressOne.trim().length() < 5
                        || addressOne.trim().length() > 50) {
                    errors.put("errorAddressOne", "inputAddressOne");
                }
            }

            if (addressTwo != null) {
                if (addressTwo.trim().length() > 50) {
                    errors.put("errorAddressTwo", "inputAddressTwo");
                }
            }
        } finally {
            String json = new Gson().toJson(errors);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
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
