package com.lmsu.controller;

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
            BookDAO dao = new BookDAO();

            //get Popular Books
            List<BookDTO> mostFavoriteBookList = dao.getMostFavoriteBooksAndPopularAuthor();
            request.setAttribute("MOST_FAVORITE_BOOKS_LIST", mostFavoriteBookList);

            //get New Arrival Books
            List<BookDTO> newArrivalBookList = dao.getNewArrivalBooks();
            request.setAttribute("NEW_ARRIVAL_BOOKS_LIST", newArrivalBookList);

            //get Popular Authors
            request.setAttribute("POPULAR_AUTHORS_LIST", mostFavoriteBookList);

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