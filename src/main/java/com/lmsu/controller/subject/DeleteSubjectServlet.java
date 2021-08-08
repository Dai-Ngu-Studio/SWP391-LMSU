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

@WebServlet(name = "DeleteSubjectServlet", value = "/DeleteSubjectServlet")
public class DeleteSubjectServlet extends HttpServlet {
    private final String SEARCH_PAGE = "subjectmanagement.jsp";
    private final String SEARCH_CONTROLLER = "SearchSubjectNameServlet";
    private final String SHOW_SUBJECT_CONTROLLER = "ShowSubjectServlet";
    static final Logger LOGGER = Logger.getLogger(DeleteSubjectServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String url = SEARCH_PAGE;

        String id = request.getParameter("pk");
        String searchVal = request.getParameter("txtSearchValue");

        try{
            SubjectDAO dao = new SubjectDAO();
            boolean result = dao.deleteSubject(id);
            if (result){
                if (searchVal==null || searchVal.trim().isEmpty()){
                    url=SHOW_SUBJECT_CONTROLLER;
                } else{
                    url=SEARCH_CONTROLLER;
                }
            }

        } catch (SQLException ex){
            LOGGER.error(ex.getMessage());
            log("DeleteSubjectServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex){
            LOGGER.error(ex.getMessage());
            log("DeleteSubjectServlet _ Naming: " + ex.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request,response);
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
