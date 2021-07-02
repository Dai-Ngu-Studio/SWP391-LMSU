package com.lmsu.controller.librarian.direct.ajax;

import com.google.gson.Gson;
import com.lmsu.orderdata.orders.OrderDAO;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ApproveOrderServlet", value = "/ApproveOrderServlet")
public class ApproveOrderServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(ApproveOrderServlet.class);

    private final String PARAM_TXT_ORDERID = "txtOrderID";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orderID = request.getParameter(PARAM_TXT_ORDERID);
        String result = "hey";
        try {
            result = "tried.";
        } finally {
            String json = new Gson().toJson(result);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
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
