package com.lmsu.controller;

import com.lmsu.books.BookDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/GetBookByISBNServlet")
public class GetBookByISBNServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String isbnTen = request.getParameter("txtISBNTen");
        String isbnThirteen = request.getParameter("txtISBNThirteen");

        try (PrintWriter out = response.getWriter()) {
            BookDAO dao = new BookDAO();
            out.print(dao.checkISBNten(isbnTen) && dao.checkISBNthirteen(isbnThirteen));
        } catch (Exception exception) {
            log(exception.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
