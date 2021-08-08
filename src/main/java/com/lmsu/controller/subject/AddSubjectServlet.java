package com.lmsu.controller.subject;

import com.lmsu.subjects.SubjectDAO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AddSubjectServlet", value = "/AddSubjectServlet")
public class AddSubjectServlet extends HttpServlet {
    static final Logger LOGGER = Logger.getLogger(AddSubjectServlet.class);
    private final String SEARCH_CONTROLLER = "SearchSubjectNameServlet";
    private final String SHOW_SUBJECT_CONTROLLER = "ShowSubjectServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = SHOW_SUBJECT_CONTROLLER;

        String subjectName = request.getParameter("txtSubjectName");
        String semester_no = request.getParameter("txtSubjectSemester");

        String searchVal = request.getParameter("txtSearchValue");

        try {
            SubjectDAO dao = new SubjectDAO();
            int subjectID = 0;
            do {
                subjectID++;
            } while (dao.checkSubjectExisted(String.valueOf(subjectID)));
            String subjectIDtxt = String.valueOf(subjectID);

            boolean result = dao.addSubject(subjectIDtxt, subjectName, Integer.parseInt(semester_no), false);
            if (result) {
                if (searchVal == null || searchVal.trim().isEmpty()) {
                    url = SHOW_SUBJECT_CONTROLLER;
                } else {
                    url = SEARCH_CONTROLLER;
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("AddSubjectServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("AddSubjectServlet _ Naming: " + ex.getMessage());
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
