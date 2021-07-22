package com.lmsu.controller;

import com.lmsu.authors.AuthorDAO;
import com.lmsu.authors.AuthorDTO;
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

@WebServlet(name = "SearchBookCatalogServlet", value = "/SearchBookCatalogServlet")
public class SearchBookCatalogServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(SearchBookCatalogServlet.class);
    private final String ERROR_PAGE = "IndexServlet";
    private final String RESULT_PAGE = "ShowBookCatalogServlet";
    private final String RESULT_AUTHOR_PAGE = "ShowAuthorCatalogServlet";


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchVal = request.getParameter("txtBookCatalogSearchValue");
        String criterion = request.getParameter("itemFilterOptions");
        String url = ERROR_PAGE;
        try {
            if (searchVal.trim().length() > 0) {
                BookDAO dao = new BookDAO();
                if (criterion.equals("Books")) {
                    dao.searchBookByTitle(searchVal);
                    List<BookDTO> result = dao.getBookList();
                    if (result != null) {
                        request.setAttribute("SEARCH_RESULT", result);
                        url = RESULT_PAGE;
                    }
                } else if (criterion.equals("Authors")) {
                    AuthorDAO author_dao = new AuthorDAO();
                    author_dao.searchAuthorByName(searchVal);
                    List<AuthorDTO> author_result = author_dao.getAuthorList();
                    if (author_result != null) {
                        request.setAttribute("SEARCH_RESULT", author_result);
                        url = RESULT_AUTHOR_PAGE;
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("SearchBookCatalogServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("SearchBookCatalogServlet _ Naming: " + ex.getMessage());
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
