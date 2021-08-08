package com.lmsu.controller.book;

import com.lmsu.authorbookmaps.AuthorBookMapDAO;
import com.lmsu.authorbookmaps.AuthorBookMapDTO;
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
import java.util.ArrayList;
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
                checkBookValidity(searchResultReceived);
                request.setAttribute("BOOK_LIST", searchResultReceived);
            } else {
                BookDAO dao = new BookDAO();
                dao.viewBookList();
                List<BookDTO> result = dao.getBookList();
                checkBookValidity(result);
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

    private void checkBookValidity(List<BookDTO> books) throws SQLException, NamingException {
        AuthorBookMapDAO authorBookMapDAO = new AuthorBookMapDAO();
        List<String> booksToHide = new ArrayList<>();
        for (BookDTO bookDTO : books) {
            authorBookMapDAO.clearAuthorBookMaps();
            authorBookMapDAO.getAuthorsOfBook(bookDTO.getBookID());
            List<AuthorBookMapDTO> authorBookMaps = authorBookMapDAO.getAuthorBookMaps();
            if ((authorBookMaps == null) || (authorBookMaps.isEmpty())) {
                booksToHide.add(bookDTO.getBookID());
            }
        }
        for (String bookID : booksToHide) {
            books.removeIf(book -> (book.getBookID().equalsIgnoreCase(bookID)));
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
