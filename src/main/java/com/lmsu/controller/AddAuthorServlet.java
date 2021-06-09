package com.lmsu.controller;

import com.lmsu.authors.AuthorDAO;
import com.lmsu.books.BookDAO;
import com.lmsu.utils.ImageHelpers;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(name = "AddAuthorServlet", value = "/AddAuthorServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
               maxFileSize = 1024 * 1024 * 5,
               maxRequestSize = 1024 * 1024 * 5 * 5)
public class AddAuthorServlet extends HttpServlet {

        private final String SEARCH_AUTHOR_CONTROLLER = "SearchAuthorNameServlet";
        private final String SHOW_AUTHOR_CONTROLLER = "ShowAuthorServlet";
        //    private final String SEARCH_CONTROLLER = "SearchTitleServlet";
        static final Logger LOGGER = Logger.getLogger(AddAuthorServlet.class);

        protected void processRequest (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            //String book_id, String title, String author_id, String subject_id,
            //                           String publisher, String publication_date, String description, BigDecimal price, int quantity,
            //                           boolean delete_status, Date last_lent_date, float avg_rating, String isbn_ten,
            //                           String isbn_thirteen

            String searchVal = request.getParameter("txtSearchValue");

            String authorName = request.getParameter("txtAuthorName");
            String authorBio = request.getParameter("txtBio");

            String url = SHOW_AUTHOR_CONTROLLER;

            try {
                AuthorDAO dao = new AuthorDAO();
                int authorID = 0;
                do {
                    authorID++;
                } while (dao.checkAuthorId(String.valueOf(authorID)));

                String authorIDTxt = String.valueOf(authorID);

                //boolean deleteStatus = false;

                System.out.println(1);
                //Start to add img to server process
                String uploadPath = ImageHelpers.getPathImgFolder(getServletContext().getRealPath(""));
                System.out.println(authorIDTxt);
                String fileName = "";
                for (Part part : request.getParts()) {
                    fileName = part.getSubmittedFileName();
                    if (!(fileName == null || fileName.trim().isEmpty())) {
                        System.out.println(fileName);
                        fileName = "author-" + authorIDTxt + "." + FilenameUtils.getExtension(fileName);
                        part.write(uploadPath + fileName);
                        break;
                    }
                }
                System.out.println(authorIDTxt + " Author ID");

                boolean result = dao.addAuthor(authorIDTxt,authorName,authorBio,fileName);
                if (result) {
                    if (searchVal == null || searchVal.trim().isEmpty()) {
                        url = SHOW_AUTHOR_CONTROLLER;
                    } else {
                        url = SEARCH_AUTHOR_CONTROLLER;
                    }
                }
//            if (!result) {
//                url = "DispatchServlet" +
//                        "?btAction=SearchBook" +
//                        "&txtSearchValue=" + searchVal;
//            }

            } catch (SQLException ex) {
                LOGGER.error(ex.getMessage());
                log("AddBookServlet _ SQL: " + ex.getMessage());
            } catch (NamingException ex) {
                LOGGER.error(ex.getMessage());
                log("AddBookServlet _ Naming: " + ex.getMessage());
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage());
                log("AddBookServlet _ Exception: " + ex.getMessage());
            } finally {
                request.getRequestDispatcher(url).forward(request, response);
            }
        }

        @Override
        protected void doGet (HttpServletRequest request, HttpServletResponse response) throws
        ServletException, IOException {
            processRequest(request, response);
        }

        @Override
        protected void doPost (HttpServletRequest request, HttpServletResponse response) throws
        ServletException, IOException {
            processRequest(request, response);
        }

    }

