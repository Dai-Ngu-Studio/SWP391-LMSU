package com.lmsu.controller.book;

import com.lmsu.books.BookDAO;
import com.lmsu.books.BookDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ShowBookServlet", value = "/ShowBookServlet")
public class ShowBookServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(ShowBookServlet.class);
    private final String BOOK_MANAGEMENT_PAGE = "bookmanagement.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String url = BOOK_MANAGEMENT_PAGE;
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
            log("ShowBookServlet _ SQL: " + e.getMessage());
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            log("ShowBookServlet _ Naming: " + e.getMessage());
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
