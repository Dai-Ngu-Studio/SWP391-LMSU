package com.lmsu.controller;

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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SearchTitleServlet", value = "/SearchTitleServlet")
public class SearchTitleServlet extends HttpServlet {

    private final String ERROR_PAGE = "bookmanagement.jsp";
    private final String RESULT_PAGE = "ShowBookServlet";
    static final Logger LOGGER = Logger.getLogger(SearchTitleServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String search = request.getParameter("txtSearchValue");
        System.out.println(search);
        String url = ERROR_PAGE;
        try {
            if (search.trim().length() > 0) {
                BookDAO dao = new BookDAO();
                dao.searchBookByTitle(search);
                List<BookDTO> list = dao.getBookList();
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
