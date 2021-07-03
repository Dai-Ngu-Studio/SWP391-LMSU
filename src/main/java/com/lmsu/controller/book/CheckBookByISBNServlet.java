package com.lmsu.controller.book;

import com.lmsu.books.BookDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/CheckBookByISBNServlet")
public class CheckBookByISBNServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String isbnTen = request.getParameter("txtISBNTen");
        String isbnThirteen = request.getParameter("txtISBNThirteen");

        try (PrintWriter out = response.getWriter()) {
            BookDAO dao = new BookDAO();
            // 0 mean isbn existed and book not deleted
            // 1 mean isbn existed but book deleted
            // 2 mean isbn not existed
            out.print(dao.checkISBN(isbnTen, isbnThirteen));
        } catch (Exception exception) {
            log(exception.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
