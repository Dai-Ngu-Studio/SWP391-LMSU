package com.lmsu.controller.member.returndelivery;

import com.lmsu.bean.member.ReturnCartObj;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ReturnFormServlet", value = "/ReturnFormServlet")
public class ReturnFormServlet extends HttpServlet {
    static final Logger LOGGER = Logger.getLogger(ReturnFormServlet.class);
    private final String RETURN_FORM_PAGE = "returnform.jsp";

    private final String ATTR_RETURN_CART = "RETURN_CART";
    private final String INDEX_CONTROLLER = "IndexServlet";

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
