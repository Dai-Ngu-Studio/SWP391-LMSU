package com.lmsu.controller.author;

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

@WebServlet(name = "ShowAuthorBookServlet", value = "/ShowAuthorBookServlet")
public class ShowAuthorBookServlet extends HttpServlet {

    private final String ERROR_PAGE = "author.jsp";
    private final String RESULT_PAGE = "book.jsp";
    static final Logger LOGGER = Logger.getLogger(ShowAuthorBookServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String authorID = request.getParameter("authorPk");
        String url = RESULT_PAGE;
        try {
            BookDAO dao = new BookDAO();
            dao.viewBookByAuthor(authorID);
            List<BookDTO> result = dao.getBookList();
            if (result != null) {
                request.setAttribute("BOOK_LIST", result);
                url = RESULT_PAGE;
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("ShowAuthorBookServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("ShowAuthorBookServlet _ Naming: " + ex.getMessage());
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
