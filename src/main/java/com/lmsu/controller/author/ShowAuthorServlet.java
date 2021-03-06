package com.lmsu.controller.author;

import com.lmsu.authorbookmaps.AuthorBookMapDAO;
import com.lmsu.authorbookmaps.AuthorBookMapDTO;
import com.lmsu.authors.AuthorDAO;
import com.lmsu.authors.AuthorDTO;
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
                dao.viewAuthorList();
                List<AuthorDTO> result = dao.getAuthorList();
                request.setAttribute("AUTHOR_LIST", result);

                HashMap<String, List<AuthorBookMapDTO>> authorMap = new HashMap<>();
                AuthorBookMapDAO authorBookMapDAO = new AuthorBookMapDAO();
                authorBookMapDAO.viewAuthorBookMapList();
                List<AuthorBookMapDTO> authorBookMapList = authorBookMapDAO.getAuthorBookMaps();

                if (authorBookMapList != null) {
                    for (AuthorBookMapDTO tmp : authorBookMapList
                    ) {
                        if (authorMap.containsKey(tmp.getAuthorDTO().getAuthorID())) {
                            authorMap.get(tmp.getAuthorDTO().getAuthorID()).add(tmp);
                        } else {
                            List<AuthorBookMapDTO> newList = new ArrayList<>();
                            newList.add(tmp);
                            authorMap.put(tmp.getAuthorDTO().getAuthorID(), newList);
                        }
                    }
                    request.setAttribute("AUTHOR_MAP", authorMap);
                }

            }
            AuthorBookMapDAO authorBookMapDAO = new AuthorBookMapDAO();
            ArrayList<String> listOfCannotDeleteAuthor = authorBookMapDAO.getCannotDeleteAuthors();
            request.setAttribute("DO_NOT_DELETE_AUTHOR_LIST", listOfCannotDeleteAuthor);

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            log("ShowAuthorServlet _ SQL: " + e.getMessage());
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            log("ShowAuthorServlet _ Naming: " + e.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        processRequest(request, response);
    }
}

