package com.lmsu.controller.author;

import com.lmsu.authors.AuthorDAO;
import com.lmsu.authors.AuthorDTO;
import com.lmsu.books.BookDAO;
import com.lmsu.books.BookDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

@WebServlet(name = "ShowAuthorServlet", value = "/ShowAuthorServlet")
public class ShowAuthorServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(ShowAuthorServlet.class);
    private final String AUTHOR_MANAGEMENT_PAGE = "authormanagement.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String url = AUTHOR_MANAGEMENT_PAGE;
        try {
            List<AuthorDTO> searchResultReceived = (List<AuthorDTO>) request.getAttribute("SEARCH_RESULT");
            if (searchResultReceived != null) {
                request.setAttribute("AUTHOR_LIST", searchResultReceived);
            } else {
                AuthorDAO dao = new AuthorDAO();
                BookDAO bookDAO = new BookDAO();
                AuthorDTO authorDTO = new AuthorDTO();

                bookDAO.viewBookList();
                List<BookDTO> book_result = bookDAO.getBookList();

                dao.viewAuthorList();
                List<AuthorDTO> result = dao.getAuthorList();
                LinkedHashMap<String, Integer> bookMap = new LinkedHashMap<>();

                for (BookDTO bookDTO: book_result
                     ) {
                    bookMap.put(bookDTO.getAuthorID(), bookDAO.countBookByAuthorID(bookDTO.getAuthorID()));
                }
                request.setAttribute("AUTHOR_LIST", result);
                request.setAttribute("COUNT_BOOK", bookMap);
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
        protected void doGet (HttpServletRequest request, HttpServletResponse response) throws
        ServletException, IOException {
            processRequest(request, response);
        }

        @Override
        protected void doPost (HttpServletRequest request, HttpServletResponse response) throws
        ServletException, IOException {
            processRequest(request, response);
        }
    }

