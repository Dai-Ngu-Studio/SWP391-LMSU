package com.lmsu.controller.member.comment;

import com.lmsu.books.BookDAO;
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
import java.util.List;

@WebServlet(name = "AddCommentServlet", value = "/AddCommentServlet")
public class AddCommentServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(AddCommentServlet.class);
    private final String VIEW_BOOK_DETAILS_CONTROLLER = "ViewBookDetailsServlet";

    private final boolean COMMENT_NOT_EDITED = false;
    private final boolean COMMENT_NOT_DELETED = false;

    private final String PARAM_BOOKID = "bookPk";
    private final String PARAM_TXTCOMMENT = "txtComment";
    private final String PARAM_TXTRATING = "bookRating";

    private final String ATTR_LOGIN_USER = "LOGIN_USER";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bookID = request.getParameter(PARAM_BOOKID);
        String txtComment = request.getParameter(PARAM_TXTCOMMENT);
        String txtRating = request.getParameter(PARAM_TXTRATING);

        String url = VIEW_BOOK_DETAILS_CONTROLLER + "?" + PARAM_BOOKID + "=" + bookID;

        try {
            // 1. Check if session existed
            HttpSession session = request.getSession();
            // 2. Check if user is logged in
            UserDTO userDTO = (UserDTO) session.getAttribute(ATTR_LOGIN_USER);
            if (userDTO != null) {
                String userID = userDTO.getId();
                float rating = Float.parseFloat(txtRating);
                BookDAO bookDAO = new BookDAO();
                CommentDAO commentDAO = new CommentDAO();
                // 3. Check if user has already commented on this book (regardless of deleteStatus)
                CommentDTO userComment = commentDAO.getCommentOfBookFromUserID(userID, bookID);
                // 4.1. User had already commented but deleted; update textComment, deleteStatus, isEdited, rating
                // instead of adding new comment (because memberID, bookID are both PK for Comments)
                if (userComment != null) {
                    commentDAO.editBookComment(userID, bookID, txtComment, rating,
                            "", COMMENT_NOT_EDITED, COMMENT_NOT_DELETED);
                } else {
                    // 4.2. User hasn't commented before, add new comment
                    boolean result = commentDAO.addCommentToBook(userID, bookID, txtComment, rating);
                }
                int numberOfRatingsGiven = 0;
                int avgRating = 0;
                commentDAO.viewBookComments(bookID);
                List<CommentDTO> comments = commentDAO.getCommentList();
                if (comments != null) {
                    for (CommentDTO comment : comments) {
                        if (!comment.isDeleteStatus()) {
                            float commentRating = comment.getRating();
                            avgRating += (commentRating > 0) ? commentRating : 0;
                            numberOfRatingsGiven += (commentRating > 0) ? 1 : 0;
                        }
                    }
                    if (numberOfRatingsGiven > 0) {
                        avgRating = avgRating / numberOfRatingsGiven;
                        bookDAO.updateAvgRating(bookID, avgRating);
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
