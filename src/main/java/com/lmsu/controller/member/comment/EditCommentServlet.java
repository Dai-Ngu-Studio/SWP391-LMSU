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

@WebServlet(name = "EditCommentServlet", value = "/EditCommentServlet")
public class EditCommentServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(EditCommentServlet.class);
    private static final String VIEW_BOOK_DETAILS_CONTROLLER = "ViewBookDetailsServlet";

    private final boolean COMMENT_EDITED = true;
    private final boolean COMMENT_NOT_DELETED = false;

    private final String PARAM_BOOKID = "bookPk";
    private final String PARAM_TXT_EDITCOMMENT = "txtEditComment";
    private final String PARAM_COMMENT_MEMBER_ID = "commentMemberID";

    private final String ATTR_LOGIN_USER = "LOGIN_USER";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bookID = request.getParameter(PARAM_BOOKID);
        String textEditComment = request.getParameter(PARAM_TXT_EDITCOMMENT);
        String memberID = request.getParameter(PARAM_COMMENT_MEMBER_ID);

        String url = VIEW_BOOK_DETAILS_CONTROLLER + "?" + PARAM_BOOKID + "=" + bookID;

        try {
            // 1. Check if session existed
            HttpSession session = request.getSession();
            // 2. Check if user is logged in
            UserDTO userDTO = (UserDTO) session.getAttribute("LOGIN_USER");
            if (userDTO != null) {
                String userID = userDTO.getId();
                BookDAO bookDAO = new BookDAO();
                CommentDAO commentDAO = new CommentDAO();
                CommentDTO commentDTO = commentDAO.getCommentOfBookFromUserID(memberID, bookID);
                // isEdited: true
                boolean result = commentDAO.editBookComment(memberID, bookID, textEditComment,
                        commentDTO.getRating(), userID, COMMENT_EDITED, COMMENT_NOT_DELETED);
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
            log("EditCommentServlet _ SQL: " + e.getMessage());
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            log("EditCommentServlet _ Naming: " + e.getMessage());
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
