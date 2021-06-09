package com.lmsu.controller.member;

import com.lmsu.comments.CommentDAO;
import com.lmsu.users.UserDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "EditCommentServlet", value = "/EditCommentServlet")
public class EditCommentServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(AddCommentServlet.class);
    private static final String VIEW_BOOK_DETAILS_CONTROLLER = "ViewBookDetailsServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bookID = request.getParameter("bookPk");
        String textComment = request.getParameter("txtComment");

        String url = VIEW_BOOK_DETAILS_CONTROLLER + "?bookPk=" + bookID;

        try {
            // 1. Check if session existed
            HttpSession session = request.getSession();
            // 2. Check if user is logged in
            UserDTO userDTO = (UserDTO) session.getAttribute("LOGIN_USER");
            if (userDTO != null) {
                String userID = userDTO.getId();
                CommentDAO commentDAO = new CommentDAO();
                // W.I.P.
                if (true) {
                    url = VIEW_BOOK_DETAILS_CONTROLLER + "?bookPk=" + bookID;
                }
            }
        }
//        catch (SQLException e) {
//            LOGGER.error(e.getMessage());
//            log("EditCommentServlet _ SQL: " + e.getMessage());
//        } catch (NamingException e) {
//            LOGGER.error(e.getMessage());
//            log("EditCommentServlet _ Naming: " + e.getMessage());
//        }
        finally {
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
