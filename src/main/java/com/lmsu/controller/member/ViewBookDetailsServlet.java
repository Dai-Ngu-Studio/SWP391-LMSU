package com.lmsu.controller.member;

import com.lmsu.authors.AuthorDAO;
import com.lmsu.authors.AuthorDTO;
import com.lmsu.bean.member.AuthorObj;
import com.lmsu.bean.member.BookObj;
import com.lmsu.bean.member.CommentObj;
import com.lmsu.bean.member.OrderItemObj;
import com.lmsu.books.BookDAO;
import com.lmsu.books.BookDTO;
import com.lmsu.comments.CommentDAO;
import com.lmsu.comments.CommentDTO;
import com.lmsu.orderdata.orderitems.OrderItemDAO;
import com.lmsu.orderdata.orderitems.OrderItemDTO;
import com.lmsu.users.UserDAO;
import com.lmsu.users.UserDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "ViewBookDetailsServlet", value = "/ViewBookDetailsServlet")
public class ViewBookDetailsServlet extends HttpServlet {

    private static final String BOOK_CATALOG_CONTROLLER = "ShowBookCatalogServlet";
    private static final String BOOK_DETAILS_PAGE = "bookdetails.jsp";
    static final Logger LOGGER = Logger.getLogger(ViewBookDetailsServlet.class);

    private final static int ITEM_PENDING = 0;
    private final static int ITEM_APPROVED = 1;
    private final static int ITEM_RECEIVED = 2;
    private final static int ITEM_RETURN_SCHEDULED = 3;
    private final static int ITEM_OVERDUE = 5;
    private final static int ITEM_OVERDUE_RETURN_SCHEDULED = 6;

    private final static boolean ATTR_BOOK_BORROWED = true;
    private final static String ATTR_MEMBER_TOTAL_ACTIVE_BORROWS = "MEMBER_TOTAL_ACTIVE_BORROWS";
    private final static String ATTR_MEMBER_BOOK_BORROW_STATUS = "MEMBER_BOOK_BORROW_STATUS";
    private final static String ATTR_COMMENT_LIST = "COMMENT_LIST";
    private final static String ATTR_COMMENT_AMOUNT = "COMMENT_AMOUNT";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = BOOK_CATALOG_CONTROLLER;

        String bookID = request.getParameter("bookPk");
        HttpSession session = request.getSession();

        try {
            BookDAO bookDAO = new BookDAO();
            // Get BookDTO
            BookDTO bookDTO = bookDAO.getBookById(bookID);
            if (bookDTO != null) {
                AuthorDAO authorDAO = new AuthorDAO();
                // Get AuthorDTO
                AuthorDTO authorDTO = authorDAO.getAuthorByID(bookDTO.getAuthorID());
                // Create Author Bean, Book Bean
                AuthorObj authorObj = new AuthorObj(authorDTO.getAuthorID(),
                        authorDTO.getAuthorName(), authorDTO.getAuthorBio());
                // Subject name not yet implemented
                BookObj bookObj = new BookObj(bookDTO.getBookID(), bookDTO.getTitle(), authorDTO.getAuthorName(),
                        "TEMP", bookDTO.getPublisher(), bookDTO.getPublicationDate(),
                        bookDTO.getDescription(), bookDTO.getQuantity(), bookDTO.getAvgRating(),
                        bookDTO.getIsbnTen(), bookDTO.getIsbnThirteen(), bookDTO.getCoverPath());
                request.setAttribute("BOOK_OBJECT", bookObj);
                //----------------------------------------------------
                //Get order items of member
                if (session != null) {
                    UserDTO userDTO = (UserDTO) session.getAttribute("LOGIN_USER");
                    if (userDTO != null) {
                        OrderItemDAO orderItemDAO = new OrderItemDAO();
                        // Get the number of active borrowed books from member
                        //----------------------------------------------------
                        orderItemDAO
                                .getOrderItemsFromMember(
                                        userDTO.getId(),
                                        new ArrayList<Integer>(
                                                Arrays.asList(
                                                        ITEM_PENDING, ITEM_APPROVED, ITEM_RECEIVED,
                                                        ITEM_RETURN_SCHEDULED, ITEM_OVERDUE,
                                                        ITEM_OVERDUE_RETURN_SCHEDULED
                                                )));
                        //----------------------------------------------------
                        List<OrderItemDTO> memberTotalActiveBorrows = orderItemDAO.getOrderItemList();
                        session.setAttribute(ATTR_MEMBER_TOTAL_ACTIVE_BORROWS, memberTotalActiveBorrows);
                        //----------------------------------------------------
                        // Check if member had borrowed this book
                        if (orderItemDAO.getOrderItemFromBookID(bookID, userDTO.getId()) != null) {
                            request.setAttribute(ATTR_MEMBER_BOOK_BORROW_STATUS, ATTR_BOOK_BORROWED);
                        }
                    }
                }
                //----------------------------------------------------
                // Get comments
                CommentDAO commentDAO = new CommentDAO();
                UserDAO userDAO = new UserDAO();
                commentDAO.viewBookComments(bookID);
                List<CommentDTO> commentList = commentDAO.getCommentList();
                List<CommentObj> commentObjList = new ArrayList<CommentObj>();
                int numberOfComment = 0;
                if (commentList != null) {
                    for (CommentDTO commentDTO : commentList) {
                        UserDTO userDTO_member = userDAO.getUserByID(commentDTO.getMemberID());
                        UserDTO userDTO_editor = userDAO.getUserByID(commentDTO.getEditorID());
                        String editorName = "";
                        if (userDTO_editor != null) {
                            editorName = userDTO_editor.getName();
                        }
                        CommentObj commentObj =
                                new CommentObj(commentDTO.getMemberID(), userDTO_member.getName(),
                                        userDTO_member.getProfilePicturePath(),
                                        commentDTO.getBookID(), commentDTO.getTextComment(),
                                        commentDTO.getRating(), commentDTO.getEditorID(),
                                        editorName,
                                        commentDTO.isEdited());
                        commentObjList.add(commentObj);
                        numberOfComment++;
                    }
                    request.setAttribute(ATTR_COMMENT_LIST, commentObjList);
                }
                request.setAttribute(ATTR_COMMENT_AMOUNT, numberOfComment);
                url = BOOK_DETAILS_PAGE;
            } //end if bookDTO existed
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            log("ViewBookDetailsServlet _ SQL: " + e.getMessage());
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            log("ViewBookDetailsServlet _ Naming: " + e.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        processRequest(request, response);
    }
}
