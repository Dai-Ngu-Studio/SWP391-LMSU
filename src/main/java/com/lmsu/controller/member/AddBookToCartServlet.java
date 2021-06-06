package com.lmsu.controller.member;

import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AddBookToCartServlet", value = "/AddBookToCartServlet")
public class AddBookToCartServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(AddBookToCartServlet.class);
    private static final String VIEW_BOOK_DETAILS_CONTROLLER = "ViewBookDetailsServlet";
    private static final String SHOW_BOOK_CATALOG_CONTROLLER = "ShowBookCatalogServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = SHOW_BOOK_CATALOG_CONTROLLER;
        String txtBookID = request.getParameter("bookPk");

        try {

        }
//        catch (SQLException ex) {
//            LOGGER.error(ex.getMessage());
//            log("AddBookToCartServlet _ SQL: " + ex.getMessage());
//        } catch (NamingException ex) {
//            LOGGER.error(ex.getMessage());
//            log("AddBookToCartServlet _ Naming: " + ex.getMessage());
//        }
        finally {
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
