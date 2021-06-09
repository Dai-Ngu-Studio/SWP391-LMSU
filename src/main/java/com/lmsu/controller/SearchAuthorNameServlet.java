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

@WebServlet(name = "SearchAuthorNameServlet", value = "/SearchAuthorNameServlet")
public class SearchAuthorNameServlet extends HttpServlet {
    private final String ERROR_PAGE = "authormanagement.jsp";
    private final String RESULT_PAGE = "ShowAuthorServlet";
    static final Logger LOGGER = Logger.getLogger(SearchAuthorNameServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String search = request.getParameter("txtSearchValue");
        System.out.println(search);
        String url = ERROR_PAGE;
        try {
            if (search.trim().length() > 0) {
                AuthorDAO dao = new AuthorDAO();
                dao.searchAuthorByName(search);
                List<AuthorDTO> list = dao.getAuthorList();
                if (!list.isEmpty()) {
                    request.setAttribute("SEARCH_RESULT", list);
                    url = RESULT_PAGE;
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("SearchTitleServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("SearchTitleServlet _ Naming: " + ex.getMessage());
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
