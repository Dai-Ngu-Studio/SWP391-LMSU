package com.lmsu.controller.book;

import com.lmsu.authorbookmaps.AuthorBookMapDAO;
import com.lmsu.authorbookmaps.AuthorBookMapDTO;
import com.lmsu.authors.AuthorDTO;
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
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            AuthorBookMapDAO authorBookMapDAO = new AuthorBookMapDAO();
            authorBookMapDAO.viewAuthorBookMapList();
            List<AuthorBookMapDTO> list = authorBookMapDAO.getAuthorBookMaps();
            //First String is bookID
            Map<String, ArrayList<AuthorDTO>> bookAuthorMap = new HashMap<>();
            if (list != null) {
                for (AuthorBookMapDTO dto : list) {
                    String bookID = null;
                    BookDTO book = dto.getBookDTO();
                    if (book != null) {
                        bookID = book.getBookID();
                        if (bookAuthorMap.containsKey(bookID) == true) {
                            bookAuthorMap.get(bookID).add(dto.getAuthorDTO());
                        } else {
                            ArrayList<AuthorDTO> newAuthorList = new ArrayList<>();
                            newAuthorList.add(dto.getAuthorDTO());
                            bookAuthorMap.put(bookID, newAuthorList);
                        }
                    }
                }
                request.setAttribute("BOOK_AUTHOR_MAP", bookAuthorMap);
            }
            List<BookDTO> searchResultReceived = (List<BookDTO>) request.getAttribute("SEARCH_RESULT");
            if (searchResultReceived != null) {
                request.setAttribute("BOOK_LIST", searchResultReceived);
            } else {
                BookDAO bookDAO = new BookDAO();
                bookDAO.viewBookList();
                List<BookDTO> result = bookDAO.getBookList();
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
