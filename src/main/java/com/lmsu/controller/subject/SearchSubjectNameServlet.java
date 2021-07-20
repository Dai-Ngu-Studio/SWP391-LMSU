package com.lmsu.controller.subject;

import com.lmsu.subjects.SubjectDAO;
import com.lmsu.subjects.SubjectDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "SearchSubjectNameServlet", value = "/SearchSubjectNameServlet")
public class SearchSubjectNameServlet extends HttpServlet {
    private static final String ERROR_PAGE = "subjectmanagement.jsp";
    private static final String SHOW_SUBJECT_CONTROLLER = "ShowSubjectServlet";
    static final Logger LOGGER = Logger.getLogger(SearchSubjectNameServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String searchValue = request.getParameter("txtSearchValue");
        String url = ERROR_PAGE;

        try {
            if (searchValue.trim().length() > 0) {
                SubjectDAO dao = new SubjectDAO();
                dao.searchSubjectByName(searchValue);
                List<SubjectDTO> list = dao.getSubjectList();
                if (list != null && list.isEmpty() == false) {
                    request.setAttribute("SEARCH_RESULT", list);
                    url = SHOW_SUBJECT_CONTROLLER;
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            log("SearchSubjectNameServlet _ SQL: " + e.getMessage());
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            log("SearchSubjectNameServlet _ Naming: " + e.getMessage());
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
