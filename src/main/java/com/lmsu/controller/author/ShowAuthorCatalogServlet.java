package com.lmsu.controller.author;

import com.lmsu.authors.AuthorDAO;
import com.lmsu.authors.AuthorDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ShowAuthorCatalogServlet", value = "/ShowAuthorCatalogServlet")
public class ShowAuthorCatalogServlet extends HttpServlet {

    private final String AUTHOR_CATALOG_LIST = "author.jsp";
    static final Logger LOGGER = Logger.getLogger(ShowAuthorCatalogServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String url = AUTHOR_CATALOG_LIST;
        try {
            List<AuthorDTO> searchResultReceived = (List<AuthorDTO>) request.getAttribute("SEARCH_RESULT");
            if (searchResultReceived != null) {
                request.setAttribute("AUTHOR_LIST", searchResultReceived);
            } else {
                AuthorDAO dao = new AuthorDAO();
                dao.viewAuthorList();
                List<AuthorDTO> result = dao.getAuthorList();

                request.setAttribute("AUTHOR_LIST", result);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            log("ShowAuthorCatalogServlet _ SQL: " + e.getMessage());
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            log("ShowAuthorCatalogServlet _ Naming: " + e.getMessage());
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
