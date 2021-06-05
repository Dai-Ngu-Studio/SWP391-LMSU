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

@WebServlet(name = "SearchForIndexServlet", value = "/SearchForIndexServlet")
public class SearchForIndexServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(SearchForIndexServlet.class);
    private final String ERROR_PAGE = "index.jsp";
    private final String SEARCH_PAGE = "index.jsp";


    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filterBox = request.getParameter("cboBox");
        String search = request.getParameter("txtSearchSth");

        String url = ERROR_PAGE;
        try {
            if (search.trim().length() > 0) {
//                if(filterBox.equals("Authors")){
//                    BookDAO dao = new BookDAO();
//                    dao.SearchBookByEverything(search);
//                    List<BookDTO> list = dao.getBookList();
//                    if (!list.isEmpty()) {
//                        request.setAttribute("SEARCH_RESULT_INDEX", list);
//                        url = SEARCH_PAGE;
//                    }
//                }
                if(filterBox.equals("Books")){
                    BookDAO dao = new BookDAO();
                    dao.searchBookByTitle(search);
                    List<BookDTO> list = dao.getBookList();
                    if (!list.isEmpty()) {
                        request.setAttribute("SEARCH_RESULT_INDEX", list);
                        url = SEARCH_PAGE;
                    }
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
