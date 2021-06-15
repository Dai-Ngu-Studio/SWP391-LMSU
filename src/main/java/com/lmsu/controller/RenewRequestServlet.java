package com.lmsu.controller;

import com.lmsu.authors.AuthorDAO;
import com.lmsu.authors.AuthorDTO;
import com.lmsu.bean.member.AuthorObj;
import com.lmsu.bean.member.BookObj;
import com.lmsu.bean.member.CartObj;
import com.lmsu.books.BookDAO;
import com.lmsu.books.BookDTO;
import com.lmsu.controller.member.cart.AddBookToCartServlet;
import com.lmsu.renewalrequests.RenewalRequestDAO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "RenewRequestServlet", value = "/RenewRequestServlet")
public class RenewRequestServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(RenewRequestServlet.class);
    private static final String USER_SETTING_PAGE = "usersettings.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = USER_SETTING_PAGE;
        String bookID = request.getParameter("bookPk");
        String orderItemsID = request.getParameter("orderItemsPk");
        String reason = request.getParameter("txtReason");
        String extendDate = request.getParameter("txtExtendDate");

        try {
            RenewalRequestDAO dao = new RenewalRequestDAO();
            int renewalID = 0;


//        } catch (SQLException ex) {
//            LOGGER.error(ex.getMessage());
//            log("RenewRequestServlet _ SQL: " + ex.getMessage());
//        } catch (NamingException ex) {
//            LOGGER.error(ex.getMessage());
//            log("RenewRequestServlet _ Naming: " + ex.getMessage());
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
