package com.lmsu.controller.author;

import com.lmsu.authors.AuthorDAO;
import com.lmsu.controller.book.UpdateBookServlet;
import com.lmsu.utils.ImageHelpers;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UpdateAuthorServlet", value = "/UpdateAuthorServlet")
public class UpdateAuthorServlet extends HttpServlet {

    private final String SEARCH_CONTROLLER = "SearchAuthorNameServlet";
    private final String SHOW_AUTHOR_CONTROLLER = "ShowAuthorServlet";
    //private final String SEARCH_CONTROLLER = "SearchTitleServlet";
    static final Logger LOGGER = Logger.getLogger(UpdateBookServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = SHOW_AUTHOR_CONTROLLER;

        String authorID = request.getParameter("pk");
        String authorName = request.getParameter("txtUpdateAuthorName");
        String authorBio = request.getParameter("txtUpdateAuthorBio");

        String searchVal = request.getParameter("txtSearchValue");
        String coverFile = request.getParameter("txtCoverFile");

        try {
            AuthorDAO dao = new AuthorDAO();
            String uploadPath = ImageHelpers.getPathImgFolder(getServletContext().getRealPath(""));
            String fileName = "";
            for (Part part : request.getParts()) {
                fileName = part.getSubmittedFileName();
                if (!(fileName == null || fileName.trim().isEmpty())) {
                    fileName = "author-" + authorID + "." + FilenameUtils.getExtension(fileName);
                    part.write(uploadPath + fileName);
                    coverFile = fileName;
                    break;
                }
            }
            boolean result = dao.updateAuthor(authorID, authorName, authorBio, coverFile);
            if (result) {
                if (searchVal == null || searchVal.trim().isEmpty()) {
                    url = SHOW_AUTHOR_CONTROLLER;
                } else {
                    url = SEARCH_CONTROLLER;
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("UpdateAuthorServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("UpdateAuthorServlet _ Naming: " + ex.getMessage());
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
