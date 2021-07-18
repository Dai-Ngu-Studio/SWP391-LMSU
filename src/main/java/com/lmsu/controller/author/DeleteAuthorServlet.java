package com.lmsu.controller.author;

import com.lmsu.authorbookmaps.AuthorBookMapDAO;
import com.lmsu.authors.AuthorDAO;
import com.lmsu.controller.book.DeleteBookServlet;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DeleteAuthorServlet", value = "/DeleteAuthorServlet")
public class DeleteAuthorServlet extends HttpServlet {

    private final String SEARCH_PAGE = "authormanagement.jsp";
    private final String SEARCH_CONTROLLER = "SearchAuthorNameServlet";
    private final String SHOW_AUTHOR_CONTROLLER="ShowAuthorServlet";
    static final Logger LOGGER = Logger.getLogger(DeleteAuthorServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String url = SEARCH_PAGE;

        String id = request.getParameter("pk");
        String searchVal = request.getParameter("txtSearchValue");

        try{
            AuthorDAO dao = new AuthorDAO();
            boolean result = dao.deleteAuthor(id);
            AuthorBookMapDAO authorBookMapDAO = new AuthorBookMapDAO();
            authorBookMapDAO.deleteByAuthorID(id);
            if (result){
                if (searchVal==null || searchVal.trim().isEmpty()){
                    url=SHOW_AUTHOR_CONTROLLER;
                } else{
                    url=SEARCH_CONTROLLER;
                }
            }

        } catch (SQLException ex){
            LOGGER.error(ex.getMessage());
            log("DeleteAuthorServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex){
            LOGGER.error(ex.getMessage());
            log("DeleteAuthorServlet _ Naming: " + ex.getMessage());
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
