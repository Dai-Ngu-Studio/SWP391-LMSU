package com.lmsu.controller.member.comment;

import com.lmsu.comments.CommentDAO;
import com.lmsu.comments.CommentDTO;
import com.lmsu.users.UserDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AddCommentServlet", value = "/AddCommentServlet")
public class AddCommentServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(AddCommentServlet.class);
    private static final String VIEW_BOOK_DETAILS_CONTROLLER = "ViewBookDetailsServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bookID = request.getParameter("bookPk");
        String textComment = request.getParameter("txtComment");
        String txtRating = request.getParameter("bookRating");

        String url = VIEW_BOOK_DETAILS_CONTROLLER + "?bookPk=" + bookID;

        try {
            // 1. Check if session existed
            HttpSession session = request.getSession();
            // 2. Check if user is logged in
            UserDTO userDTO = (UserDTO) session.getAttribute("LOGIN_USER");
            if (userDTO != null) {
                String userID = userDTO.getId();
                float rating = Float.parseFloat(txtRating);
                CommentDAO commentDAO = new CommentDAO();
                // 3. Check if user has already commented on this book (regardless of deleteStatus)
                CommentDTO userComment = commentDAO.getCommentOfBookFromUserID(userID, bookID);
                // 4.1. User had already commented but deleted; update textComment, deleteStatus, isEdited, rating
                // instead of adding new comment (because memberID, bookID are both PK for Comments)
                if (userComment != null) {
                    commentDAO.editBookComment(userID, bookID, textComment, rating,
                            "", false,false);
                } else {
                    // 4.2. User hasn't commented before, add new comment
                    boolean result = commentDAO.addCommentToBook(userID, bookID, textComment, rating);
                    if (result) {
                        url = VIEW_BOOK_DETAILS_CONTROLLER + "?bookPk=" + bookID;
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            log("AddCommentServlet _ SQL: " + e.getMessage());
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            log("AddCommentServlet _ Naming: " + e.getMessage());
        } catch (NumberFormatException e) {
            LOGGER.error(e.getMessage());
            log("AddCommentServlet _ NumberFormat: " + e.getMessage());
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
