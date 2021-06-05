package com.lmsu.controller.member;

import com.lmsu.books.BookDAO;
import com.lmsu.books.BookDTO;
import com.lmsu.controller.LoginServlet;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ShowBookCatalogServlet", value = "/ShowBookCatalogServlet")
public class ShowBookCatalogServlet extends HttpServlet {

    private final String BOOK_CATALOG_PAGE = "book.jsp";
    static final Logger LOGGER = Logger.getLogger(ShowBookCatalogServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String url = BOOK_CATALOG_PAGE;
        try {
            List<BookDTO> searchResultReceived = (List<BookDTO>) request.getAttribute("SEARCH_RESULT");
            if (searchResultReceived != null) {
                request.setAttribute("BOOK_LIST", searchResultReceived);
            } else {
                BookDAO dao = new BookDAO();
                dao.viewBookList();
                List<BookDTO> result = dao.getBookList();

                request.setAttribute("BOOK_LIST", result);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            log("ShowBookCatalogServlet _ SQL: " + e.getMessage());
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            log("ShowBookCatalogServlet _ Naming: " + e.getMessage());
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
