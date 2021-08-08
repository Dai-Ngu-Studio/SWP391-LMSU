package com.lmsu.controller.subject;

import com.lmsu.books.BookDAO;
import com.lmsu.books.BookDTO;
import com.lmsu.subjects.SubjectDAO;
import com.lmsu.subjects.SubjectDTO;
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
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "ShowSubjectServlet", value = "/ShowSubjectServlet")
public class ShowSubjectServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(ShowSubjectServlet.class);
    private static final String ERROR_PAGE = "error.jsp";
    private static final String SUBJECT_MANAGEMENT_PAGE = "subjectmanagement.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String url = SUBJECT_MANAGEMENT_PAGE;

        try {
            List<SubjectDTO> searchResultReceived = (List<SubjectDTO>) request.getAttribute("SEARCH_RESULT");
            if (searchResultReceived != null) {
                request.setAttribute("SUBJECT_LIST", searchResultReceived);
            } else {
                SubjectDAO dao = new SubjectDAO();
                dao.viewSubjectList();
                List<SubjectDTO> list = dao.getSubjectList();
                request.setAttribute("SUBJECT_LIST", list);

                HashMap<String, List<BookDTO>> subjectMap = new HashMap<>();
                HashMap<String, Integer> countSubjectMap = new HashMap<>();
                BookDAO bookDAO = new BookDAO();
                bookDAO.viewBookList();
                List<BookDTO> bookMapList = bookDAO.getBookList();

                if (bookMapList != null) {
                    for (BookDTO tmp : bookMapList
                    ) {
                        if (subjectMap.containsKey(tmp.getSubjectID())) {
                            subjectMap.get(tmp.getSubjectID()).add(tmp);
                        } else {
                            List<BookDTO> newList = new ArrayList<>();
                            newList.add(tmp);
                            subjectMap.put(tmp.getSubjectID(), newList);
                        }
                        countSubjectMap.put(tmp.getSubjectID(), bookDAO.countBookBySubjectID(tmp.getSubjectID()));
                    }
                    request.setAttribute("SUBJECT_MAP", subjectMap);
                    request.setAttribute("COUNT_SUBJECT", countSubjectMap);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            log("ShowSubjectServlet _ SQL: " + e.getMessage());
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            log("ShowSubjectServlet _ Naming: " + e.getMessage());
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
