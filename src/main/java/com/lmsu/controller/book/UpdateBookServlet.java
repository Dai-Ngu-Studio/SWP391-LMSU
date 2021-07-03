package com.lmsu.controller.book;

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
import java.sql.SQLException;

@WebServlet(name = "UpdateBookServlet", value = "/UpdateBookServlet")
public class UpdateBookServlet extends HttpServlet {
    private final String SEARCH_CONTROLLER = "SearchTitleServlet";
    private final String SHOW_BOOK_CONTROLLER = "ShowBookServlet";
    //private final String SEARCH_CONTROLLER = "SearchTitleServlet";
    static final Logger LOGGER = Logger.getLogger(UpdateBookServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = SHOW_BOOK_CONTROLLER;

        String bookID = request.getParameter("pk");
        String title = request.getParameter("txtUpdateTitle");
        String authorID = request.getParameter("txtUpdateAuthorID");

        String publishDate = request.getParameter("txtUpdatePubliDate");
        String publisher = request.getParameter("txtUpdatePublisher");
        String description = request.getParameter("txtUpdateDescription");

        String subjectID = request.getParameter("txtUpdateSubjectID");
        String quantity = request.getParameter("txtUpdateQuantity");
        String price = request.getParameter("txtUpdatePrice");

        String ISBN_ten = request.getParameter("txtUpdateISBNTen");
        String ISBN_thirteen = request.getParameter("txtUpdateISBNThirteen");

        String searchVal = request.getParameter("txtSearchValue");
        String coverFile = request.getParameter("txtCoverFile");
        boolean check = false;
        try {
            BigDecimal priceDecimal = new BigDecimal(price);
            int quantityNum = Integer.parseInt(quantity);
            BookDAO dao = new BookDAO();
            String uploadPath = ImageHelpers.getPathImgFolder(getServletContext().getRealPath(""));
            String fileName = "";
            for (Part part : request.getParts()) {
                fileName = part.getSubmittedFileName();
                if (!(fileName == null || fileName.trim().isEmpty())) {
                    fileName = "book-" + bookID + "." + FilenameUtils.getExtension(fileName);
                    part.write(uploadPath + fileName);
                    coverFile = fileName;
                    break;
                }
            }
            boolean result = dao.updateBook(bookID, title, subjectID, publisher, publishDate, description, priceDecimal, quantityNum, ISBN_ten, ISBN_thirteen, coverFile);
            if (result) {
                if (searchVal == null || searchVal.trim().isEmpty()) {
                    url = SHOW_BOOK_CONTROLLER;
                } else {
                    url = SEARCH_CONTROLLER;
                }
            }
//            if (result) {
//                url = "DispatchServlet" +
//                        "?btAction=SearchBook" +
//                        "&txtSearchValue=" + SearchValue;
//            }
        } catch (SQLException ex) {

            LOGGER.error(ex.getMessage());
            log("UpdateBookServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("UpdateBookServlet _ Naming: " + ex.getMessage());
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
