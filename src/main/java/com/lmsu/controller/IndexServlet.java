package com.lmsu.controller;

import com.lmsu.authors.AuthorDAO;
import com.lmsu.authors.AuthorDTO;
import com.lmsu.books.BookDAO;
import com.lmsu.books.BookDTO;
import com.lmsu.subjects.SubjectDAO;
import com.lmsu.subjects.SubjectDTO;
import com.lmsu.users.UserDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "IndexServlet", value = "/IndexServlet")
public class IndexServlet extends HttpServlet {

    private static final String ERROR_PAGE = "error.html";
    private static final String INDEX_PAGE = "index.jsp";
    static final Logger LOGGER = Logger.getLogger(IndexServlet.class);

    private final String ATTR_LOGIN_USER = "LOGIN_USER";
    private final String ATTR_MOST_FAVORITE_BOOKS_LIST = "MOST_FAVORITE_BOOKS_LIST";
    private final String ATTR_NEW_ARRIVAL_BOOKS_LIST = "NEW_ARRIVAL_BOOKS_LIST";
    private final String ATTR_POPULAR_AUTHORS_LIST = "POPULAR_AUTHORS_LIST";
    private final String ATTR_INDEX_SUBJECT_LIST = "INDEX_SUBJECT_LIST";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String url = ERROR_PAGE;

        try {
            BookDAO bookDAO = new BookDAO();
            AuthorDAO authorDAO = new AuthorDAO();
            SubjectDAO subjectDAO = new SubjectDAO();

            //get Popular Books
            List<BookDTO> mostFavoriteBookList = bookDAO.getMostFavoriteBooks();
            request.setAttribute(ATTR_MOST_FAVORITE_BOOKS_LIST, mostFavoriteBookList);

            //get New Arrival Books
            List<BookDTO> newArrivalBookList = bookDAO.getNewArrivalBooks();
            request.setAttribute(ATTR_NEW_ARRIVAL_BOOKS_LIST, newArrivalBookList);

            //get Popular Authors
            List<AuthorDTO> popularAuthorsList = authorDAO.getPopularAuthorsFromMostFavoriteBooks();
            request.setAttribute(ATTR_POPULAR_AUTHORS_LIST, popularAuthorsList);

            List<SubjectDTO> subjects = null;
            boolean hasSubjects = false;
            //get subjects
            HttpSession session = request.getSession();
            if (session != null) {
                UserDTO user = (UserDTO) session.getAttribute(ATTR_LOGIN_USER);
                if (user != null) {
                    String txtSemester = user.getSemester();
                    int semester = Integer.parseInt(txtSemester);
                    subjectDAO.searchSubjectBySemester(semester);
                    subjects = subjectDAO.getSubjectList();
                    if (subjects != null) {
                        hasSubjects = true;
                        request.setAttribute(ATTR_INDEX_SUBJECT_LIST, subjects);
                    }
                }
            }
            if (!hasSubjects) {
                subjectDAO.viewSubjectList();
                subjects = subjectDAO.getSubjectList();
                if (subjects != null) {
                    if (subjects.size() >= 8) {
                        subjects = subjects.subList(0, 7);
                    }
                    request.setAttribute(ATTR_INDEX_SUBJECT_LIST, subjects);
                }
            }

            url = INDEX_PAGE;
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("IndexServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("IndexServlet _ Naming: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            LOGGER.error(ex.getMessage());
            log("IndexServlet _ NumberFormat: " + ex.getMessage());
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