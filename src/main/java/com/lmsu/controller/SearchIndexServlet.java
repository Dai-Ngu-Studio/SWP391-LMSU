package com.lmsu.controller;

import com.lmsu.authors.AuthorDAO;
import com.lmsu.authors.AuthorDTO;
import com.lmsu.books.BookDAO;
import com.lmsu.books.BookDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "SearchIndexServlet", value = "/SearchIndexServlet")
public class SearchIndexServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(SearchIndexServlet.class);
    private final String ERROR_PAGE = "IndexServlet";
    private final String RESULT_PAGE = "ShowBookCatalogServlet";
    private final String RESULT_AUTHOR_PAGE = "ShowAuthorCatalogServlet";

    private final String PARAM_SEARCH_VALUE = "txtBookCatalogSearchValue";
    private final String PARAM_CRITERION = "itemFilterOptions";

    private final String CRITERION_BOOK = "Books";
    private final String CRITERION_AUTHOR = "Authors";
    private final String CRITERION_SUBJECT = "Subjects";

    private final String ATTR_SEARCH_RESULT = "SEARCH_RESULT";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchVal = request.getParameter(PARAM_SEARCH_VALUE);
        String criterion = request.getParameter(PARAM_CRITERION);
        String url = ERROR_PAGE;
        try {
            if (searchVal.trim().length() > 0) {
                BookDAO dao = new BookDAO();
                if (criterion.equals(CRITERION_BOOK)) {
                    dao.searchBookByTitle(searchVal);
                    List<BookDTO> result = dao.getBookList();
                    if (result != null) {
                        request.setAttribute(ATTR_SEARCH_RESULT, result);
                        url = RESULT_PAGE;
                    }
                } else if (criterion.equals(CRITERION_AUTHOR)) {
                    AuthorDAO author_dao = new AuthorDAO();
                    author_dao.searchAuthorByName(searchVal);
                    List<AuthorDTO> author_result = author_dao.getAuthorList();
                    if (author_result != null) {
                        request.setAttribute(ATTR_SEARCH_RESULT, author_result);
                        url = RESULT_AUTHOR_PAGE;
                    }
                } else if (criterion.equals(CRITERION_SUBJECT)) {
                    dao.searchBooksBySubject(searchVal);
                    List<BookDTO> result = dao.getBookList();
                    if (result != null) {
                        request.setAttribute(ATTR_SEARCH_RESULT, result);
                        url = RESULT_PAGE;
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
