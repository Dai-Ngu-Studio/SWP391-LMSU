package com.lmsu.controller.member;

import com.lmsu.authors.AuthorDAO;
import com.lmsu.authors.AuthorDTO;
import com.lmsu.bean.member.AuthorObj;
import com.lmsu.bean.member.BookObj;
import com.lmsu.books.BookDAO;
import com.lmsu.books.BookDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ViewBookDetailsServlet", value = "/ViewBookDetailsServlet")
public class ViewBookDetailsServlet extends HttpServlet {

    private static final String BOOK_CATALOG_CONTROLLER = "ShowBookCatalogServlet";
    private static final String BOOK_DETAILS_PAGE = "bookDetails.jsp";
    static final Logger LOGGER = Logger.getLogger(ViewBookDetailsServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String url = BOOK_CATALOG_CONTROLLER;

        String bookID = request.getParameter("bookPk");

        try {
            BookDAO bookDAO = new BookDAO();
            // Get BookDTO
            BookDTO bookDTO = bookDAO.getBookById(bookID);
            if (bookDTO != null) {
                AuthorDAO authorDAO = new AuthorDAO();
                // Get AuthorDTO
                AuthorDTO authorDTO = authorDAO.getAuthorByID(bookDTO.getAuthorID());
                // Create Beans
                AuthorObj authorObj = new AuthorObj(authorDTO.getAuthorID(),
                        authorDTO.getAuthorName(), authorDTO.getAuthorBio());
                // Subject name not yet implemented
                BookObj bookObj = new BookObj(bookDTO.getBookID(), bookDTO.getTitle(), authorDTO.getAuthorName(),
                        "TEMP", bookDTO.getPublisher(), bookDTO.getPublicationDate(),
                        bookDTO.getDescription(), bookDTO.getAvgRating(),
                        bookDTO.getIsbnTen(), bookDTO.getIsbnThirteen(), bookDTO.getCoverPath());
                request.setAttribute("BOOK_OBJECT", bookObj);
                url = BOOK_DETAILS_PAGE;
            } //end if bookDTO existed
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            log("ViewBookDetailsServlet _ SQL: " + e.getMessage());
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            log("ViewBookDetailsServlet _ Naming: " + e.getMessage());
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
