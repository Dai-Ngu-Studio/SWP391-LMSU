package com.lmsu.controller.subject;

import com.lmsu.authors.AuthorDAO;
import com.lmsu.controller.book.UpdateBookServlet;
import com.lmsu.subjects.SubjectDAO;
import com.lmsu.utils.ImageHelpers;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UpdateSubjectServlet", value = "/UpdateSubjectServlet")
public class UpdateSubjectServlet extends HttpServlet {

    private final String SEARCH_CONTROLLER = "SearchSubjectNameServlet";
    private final String SHOW_SUBJECT_CONTROLLER = "ShowSubjectServlet";

    static final Logger LOGGER = Logger.getLogger(UpdateSubjectServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = SHOW_SUBJECT_CONTROLLER;

        String subjectID = request.getParameter("pk");
        String subjectName = request.getParameter("txtUpdateSubjectName");
        String semester_no = request.getParameter("txtUpdateSemester");

        String searchVal = request.getParameter("txtSearchValue");

        try {
            SubjectDAO dao = new SubjectDAO();
            boolean result = dao.updateSubject(subjectID, subjectName, Integer.parseInt(semester_no));
            if (result) {
                if (searchVal == null || searchVal.trim().isEmpty()) {
                    url = SHOW_SUBJECT_CONTROLLER;
                } else {
                    url = SEARCH_CONTROLLER;
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("UpdateSubjectServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("UpdateSubjectServlet _ Naming: " + ex.getMessage());
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
