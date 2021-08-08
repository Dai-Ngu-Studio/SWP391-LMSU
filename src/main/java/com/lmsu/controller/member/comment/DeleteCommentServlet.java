package com.lmsu.controller.member.comment;

import com.lmsu.books.BookDAO;
import com.lmsu.comments.CommentDAO;
import com.lmsu.comments.CommentDTO;
import com.lmsu.users.UserDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "DeleteCommentServlet", value = "/DeleteCommentServlet")
public class DeleteCommentServlet extends HttpServlet {
    static final Logger LOGGER = Logger.getLogger(DeleteCommentServlet.class);
    private static final String VIEW_BOOK_DETAILS_CONTROLLER = "ViewBookDetailsServlet";

    private final boolean COMMENT_EDITED = true;
    private final boolean COMMENT_DELETED = true;

    private final String PARAM_BOOKID = "bookPk";
    private final String PARAM_COMMENT_MEMBER_ID = "commentMemberID";

    private final String ATTR_LOGIN_USER = "LOGIN_USER";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bookID = request.getParameter(PARAM_BOOKID);
        String memberID = request.getParameter(PARAM_COMMENT_MEMBER_ID);

        String url = VIEW_BOOK_DETAILS_CONTROLLER + "?" + PARAM_BOOKID + "=" + bookID;

        try {
            // 1. Check if session existed
            HttpSession session = request.getSession();
            // 2. Check if user is logged in
            UserDTO userDTO = (UserDTO) session.getAttribute(ATTR_LOGIN_USER);
            if (userDTO != null) {
                String userID = userDTO.getId();
                BookDAO bookDAO = new BookDAO();
                CommentDAO commentDAO = new CommentDAO();
                CommentDTO commentDTO = commentDAO.getCommentOfBookFromUserID(memberID, bookID);
                // Set deleteStatus true, userID for editorID
                boolean result = commentDAO.editBookComment(memberID, bookID, commentDTO.getTextComment(),
                        commentDTO.getRating(), userID, COMMENT_EDITED, COMMENT_DELETED);
                int numberOfRatingsGiven = 0;
                int avgRating = 0;
                commentDAO.viewBookComments(bookID);
                List<CommentDTO> comments = commentDAO.getCommentList();
                if (comments != null) {
                    for (CommentDTO comment : comments) {
                        if (!comment.isDeleteStatus()){
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
            log("DeleteCommentServlet _ SQL: " + e.getMessage());
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            log("DeleteCommentServlet _ Naming: " + e.getMessage());
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
