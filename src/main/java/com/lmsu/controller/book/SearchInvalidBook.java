package com.lmsu.controller.book;

import com.lmsu.books.BookDAO;
import com.lmsu.books.BookDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "SearchInvalidBook", value = "/SearchInvalidBook")
public class SearchInvalidBook extends HttpServlet {

    private final String ERROR_PAGE = "internalservererror.html";
    private final String RESULT_PAGE = "ShowBookServlet";
    static final Logger LOGGER = Logger.getLogger(SearchTitleServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = ERROR_PAGE;
        try {
            BookDAO dao = new BookDAO();
            dao.searchInvalidBook();
            List<BookDTO> list = dao.getBookList();
            request.setAttribute("SEARCH_RESULT", list);
            request.setAttribute("SHOWING_INVALID", true);
            url = RESULT_PAGE;
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("SearchTitleServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("SearchTitleServlet _ Naming: " + ex.getMessage());
        } finally {
            System.out.println(url);
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
