package com.lmsu.controller;

import com.lmsu.authors.AuthorDAO;
import com.lmsu.authors.AuthorDTO;
import com.lmsu.books.BookDAO;
import com.lmsu.books.BookDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "IndexServlet", value = "/IndexServlet")
public class IndexServlet extends HttpServlet {

    private static final String ERROR_PAGE = "error.html";
    private static final String INDEX_PAGE = "index.jsp";
    static final Logger LOGGER = Logger.getLogger(IndexServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String url = ERROR_PAGE;

        try {
            BookDAO bookDAO = new BookDAO();
            AuthorDAO authorDAO = new AuthorDAO();

            //get Popular Books
            bookDAO.getPopularBooks();
            List<BookDTO> popularBookList = bookDAO.getBookList();
            request.setAttribute("POPULAR_BOOKS", popularBookList);

            bookDAO.clearList();

            //get New Arrival Books
            bookDAO.getNewArrival();
            List<BookDTO> newArrivalBookList = bookDAO.getBookList();
            request.setAttribute("NEW_ARRIVAL_BOOKS", newArrivalBookList);

//            //get Popular Authors
//            authorDAO.getPopularAuthor();
//            List<AuthorDTO> popularAuthorList = authorDAO.getAuthorList();
//            request.setAttribute("POPULAR_AUTHORS", popularAuthorList);

            url = INDEX_PAGE;
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("IndexServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("IndexServlet _ Naming: " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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