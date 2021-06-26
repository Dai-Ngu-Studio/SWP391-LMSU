package com.lmsu.controller.member.cart;

import com.lmsu.authorbookmaps.AuthorBookMapDAO;
import com.lmsu.authorbookmaps.AuthorBookMapDTO;
import com.lmsu.authors.AuthorDAO;
import com.lmsu.authors.AuthorDTO;
import com.lmsu.bean.author.AuthorObj;
import com.lmsu.bean.book.BookObj;
import com.lmsu.bean.member.CartObj;
import com.lmsu.books.BookDAO;
import com.lmsu.books.BookDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "AddBookToCartServlet", value = "/AddBookToCartServlet")
public class AddBookToCartServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(AddBookToCartServlet.class);
    private static final String VIEW_BOOK_DETAILS_CONTROLLER = "ViewBookDetailsServlet";

    private final String PARAM_BOOKID = "bookPk";

    private final String ATTR_BOOK_OBJECT = "BOOK_OBJECT";
    private final String ATTR_MEMBER_CART = "MEMBER_CART";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = VIEW_BOOK_DETAILS_CONTROLLER;
        String bookID = request.getParameter(PARAM_BOOKID);

        try {
            // 1. Check if session existed (default create one if not exist)
            HttpSession session = request.getSession();
            // 2. Check if session has cart
            CartObj cartObj = (CartObj) session.getAttribute(ATTR_MEMBER_CART);
            if (cartObj == null) {
                cartObj = new CartObj();
            }
            // 3. Get the book which member want to add to cart
            BookDAO bookDAO = new BookDAO();
            // Get BookDTO
            BookDTO bookDTO = bookDAO.getBookById(bookID);
            if (bookDTO != null) {
                // Get authors of this book
                AuthorBookMapDAO authorBookMapDAO = new AuthorBookMapDAO();
                authorBookMapDAO.getAuthorsOfBook(bookID);
                List<AuthorBookMapDTO> authorBookMaps = authorBookMapDAO.getAuthorBookMaps();
                // Create Book Bean
                BookObj bookObj = new BookObj();
                bookObj.setId(bookID);
                bookObj.setTitle(bookDTO.getTitle());
                bookObj.setSubjectID(bookDTO.getSubjectID());
                bookObj.setSubjectName("TEMPORARY VALUE"); //need SubjectDAO, DTO
                bookObj.setPublisher(bookDTO.getPublisher());
                bookObj.setPublishDate(bookDTO.getPublicationDate());
                bookObj.setDescription(bookDTO.getDescription());
                bookObj.setQuantity(bookDTO.getQuantity());
                bookObj.setAvgRating(bookDTO.getAvgRating());
                bookObj.setIsbnTen(bookDTO.getIsbnTen());
                bookObj.setIsbnThirteen(bookDTO.getIsbnThirteen());
                bookObj.setCoverPath(bookDTO.getCoverPath());
                bookObj.setAuthors(new HashMap<String, String>());
                // Map authors to Book Bean
                for (AuthorBookMapDTO authorBookMap : authorBookMaps) {
                    AuthorDTO authorDTO = authorBookMap.getAuthorDTO();
                    bookObj.getAuthors().put(authorDTO.getAuthorID(), authorDTO.getAuthorName());
                }
                // 4. Add book to member's cart
                cartObj.addBookToCart(bookObj);
                // 5. Save cart on server
                session.setAttribute(ATTR_MEMBER_CART, cartObj);
                // 6. Member continues checking book
                url = VIEW_BOOK_DETAILS_CONTROLLER + "?" + PARAM_BOOKID + "=" + bookID;
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("AddBookToCartServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("AddBookToCartServlet _ Naming: " + ex.getMessage());
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
