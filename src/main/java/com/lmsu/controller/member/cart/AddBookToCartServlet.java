package com.lmsu.controller.member.cart;

import com.lmsu.authors.AuthorDAO;
import com.lmsu.authors.AuthorDTO;
import com.lmsu.bean.member.AuthorObj;
import com.lmsu.bean.member.BookObj;
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

@WebServlet(name = "AddBookToCartServlet", value = "/AddBookToCartServlet")
public class AddBookToCartServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(AddBookToCartServlet.class);
    private static final String VIEW_BOOK_DETAILS_CONTROLLER = "ViewBookDetailsServlet";
    private static final String SHOW_BOOK_CATALOG_CONTROLLER = "ShowBookCatalogServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = SHOW_BOOK_CATALOG_CONTROLLER;
        String bookID = request.getParameter("bookPk");

        try {
            // 1. Check if session existed (default create one if not exist)
            HttpSession session = request.getSession();
            // 2. Check if session has cart
            CartObj cartObj = (CartObj) session.getAttribute("MEMBER_CART");
            if (cartObj == null) {
                cartObj = new CartObj();
            }
            // 3. Start: Get the book which member want to add to cart
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
                        bookDTO.getDescription(), bookDTO.getQuantity(), bookDTO.getAvgRating(),
                        bookDTO.getIsbnTen(), bookDTO.getIsbnThirteen(), bookDTO.getCoverPath());
                // 3. End: Get the book which member want to add to cart
                // 4. Add book to member's cart
                cartObj.addBookToCart(bookObj);
                // 5. Save book on server
                session.setAttribute("MEMBER_CART", cartObj);
                // 6. Member continues checking book
                // (Temporary redirect to cart for testing)
//                url = VIEW_BOOK_DETAILS_CONTROLLER + "?bookPk=" + bookID;
                url = "viewCart.jsp";
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
