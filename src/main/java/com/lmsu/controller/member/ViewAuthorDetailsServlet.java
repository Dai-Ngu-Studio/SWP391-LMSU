package com.lmsu.controller.member;

import com.lmsu.authors.AuthorDAO;
import com.lmsu.authors.AuthorDTO;
import com.lmsu.bean.author.AuthorObj;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ViewAuthorDetailsServlet", value = "/ViewAuthorDetailsServlet")
public class ViewAuthorDetailsServlet extends HttpServlet {

    private static final String AUTHOR_CATALOG_CONTROLLER = "ShowAuthorCatalogServlet";
    private static final String AUTHOR_DETAILS_PAGE = "authorDetails.jsp";
    static final Logger LOGGER = Logger.getLogger(ViewAuthorDetailsServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String url = AUTHOR_CATALOG_CONTROLLER;

        String authorID = request.getParameter("authorPk");

        try {
            AuthorDAO authorDAO = new AuthorDAO();
            AuthorDTO authorDTO = authorDAO.getAuthorByID(authorID);

            if (authorDTO != null) {

                AuthorObj authorObj = new AuthorObj(authorDTO.getAuthorID(),
                        authorDTO.getAuthorName(), authorDTO.getAuthorBio());
                request.setAttribute("AUTHOR_OBJECT", authorObj);
                url = AUTHOR_DETAILS_PAGE;
            } //end if bookDTO existed
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            log("ViewAuthorDetailsServlet _ SQL: " + e.getMessage());
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            log("ViewAuthorDetailsServlet _ Naming: " + e.getMessage());
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
