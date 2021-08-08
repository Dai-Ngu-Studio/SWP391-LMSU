package com.lmsu.controller.book;

import com.lmsu.authorbookmaps.AuthorBookMapDAO;
import com.lmsu.authorbookmaps.AuthorBookMapDTO;
import com.lmsu.authors.AuthorDTO;
import com.lmsu.bean.book.BookObj;
import com.lmsu.bean.comment.CommentObj;
import com.lmsu.bean.member.CartObj;
import com.lmsu.books.BookDAO;
import com.lmsu.books.BookDTO;
import com.lmsu.comments.CommentDAO;
import com.lmsu.comments.CommentDTO;
import com.lmsu.orderdata.orderitems.OrderItemDAO;
import com.lmsu.orderdata.orderitems.OrderItemDTO;
import com.lmsu.orderdata.orders.OrderDAO;
import com.lmsu.orderdata.orders.OrderDTO;
import com.lmsu.users.UserDAO;
import com.lmsu.users.UserDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "ViewBookDetailsServlet", value = "/ViewBookDetailsServlet")
public class ViewBookDetailsServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(ViewBookDetailsServlet.class);
    private final String BOOK_CATALOG_CONTROLLER = "ShowBookCatalogServlet";
    private final String BOOK_DETAILS_PAGE = "bookdetails.jsp";
    private final List<Integer> NO_ORDER_STATUS_SPECIFIED = null;

    private final int ORDER_CANCELLED = -1;
    private final int ORDER_PENDING = 0;
    private final int ORDER_APPROVED = 1;
    private final int ORDER_RECEIVED = 2;
    private final int ORDER_RETURNED = 3;
    private final int ORDER_OVERDUE = 4;
    private final int ORDER_REJECTED = 5;
    private final int ORDER_RESERVE_ONLY = 6;
    private final int ORDER_RETURN_SCHEDULED = 7;
    private final int ORDER_RETURN_RETURNED = 8;

    private final int ITEM_CANCELLED = -1;
    private final int ITEM_PENDING = 0;
    private final int ITEM_APPROVED = 1;
    private final int ITEM_RECEIVED = 2;
    private final int ITEM_RETURN_SCHEDULED = 3;
    private final int ITEM_RETURNED = 4;
    private final int ITEM_OVERDUE = 5;
    private final int ITEM_OVERDUE_RETURN_SCHEDULED = 6;
    private final int ITEM_OVERDUE_RETURNED = 7;
    private final int ITEM_REJECTED = 8;
    private final int ITEM_LOST = 9;
    private final int ITEM_RESERVED = 10;
    private final int ITEM_RESERVED_PAST = 11;

    private final int ORDER_BOTH_METHOD = -1;
    private final int STATUS_BORROWED = 0;
    private final int STATUS_RESERVED = 1;

    private final String PARAM_BOOKID = "bookPk";

    private final String ATTR_MEMBER_TOTAL_ACTIVE_BORROWS = "MEMBER_TOTAL_ACTIVE_BORROWS";
    private final String ATTR_MEMBER_BOOK_BORROW_STATUS = "MEMBER_BOOK_BORROW_STATUS";
    private final String ATTR_COMMENT_LIST = "COMMENT_LIST";
    private final String ATTR_COMMENT_AMOUNT = "COMMENT_AMOUNT";
    private final String ATTR_LOGIN_USER = "LOGIN_USER";
    private final String ATTR_BOOK_OBJECT = "BOOK_OBJECT";
    private final String ATTR_MEMBER_CART = "MEMBER_CART";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = BOOK_CATALOG_CONTROLLER;

        String bookID = request.getParameter(PARAM_BOOKID);

        try {
            // 1. Check if session existed (default create one if not exist)
            HttpSession session = request.getSession();
            // 2. Check if session has cart
            CartObj cartObj = (CartObj) session.getAttribute(ATTR_MEMBER_CART);
            if (cartObj == null) {
                cartObj = new CartObj();
                session.setAttribute(ATTR_MEMBER_CART, cartObj);
            }
            BookDAO bookDAO = new BookDAO();
            // Get BookDTO
            BookDTO bookDTO = bookDAO.getBookById(bookID);
            if (bookDTO != null) {
                // Get authors of this book
                AuthorBookMapDAO authorBookMapDAO = new AuthorBookMapDAO();
                authorBookMapDAO.getAuthorsOfBook(bookID);
                List<AuthorBookMapDTO> authorBookMaps = authorBookMapDAO.getAuthorBookMaps();
                // Create Book Bean
                BookObj bookObj = new BookObj();
                bookObj.setId(bookID);
                bookObj.setTitle(bookDTO.getTitle());
                bookObj.setSubjectID(bookDTO.getSubjectID());
                bookObj.setSubjectName("TEMPORARY VALUE"); //need SubjectDAO, DTO
                bookObj.setPublisher(bookDTO.getPublisher());
                bookObj.setPublishDate(bookDTO.getPublicationDate());
                bookObj.setDescription(bookDTO.getDescription());
                bookObj.setQuantity(bookDTO.getQuantity());
                bookObj.setAvgRating(bookDTO.getAvgRating());
                bookObj.setIsbnTen(bookDTO.getIsbnTen());
                bookObj.setIsbnThirteen(bookDTO.getIsbnThirteen());
                bookObj.setCoverPath(bookDTO.getCoverPath());
                bookObj.setAuthors(new HashMap<String, String>());
                // Map authors to Book Bean
                for (AuthorBookMapDTO authorBookMap : authorBookMaps) {
                    AuthorDTO authorDTO = authorBookMap.getAuthorDTO();
                    bookObj.getAuthors().put(authorDTO.getAuthorID(), authorDTO.getAuthorName());
                }
                request.setAttribute(ATTR_BOOK_OBJECT, bookObj);
                //----------------------------------------------------
                //Get order items of member
                if (session != null) {
                    UserDTO userDTO = (UserDTO) session.getAttribute(ATTR_LOGIN_USER);
                    if (userDTO != null) {
                        OrderDAO orderDAO = new OrderDAO();
                        OrderItemDAO orderItemDAO = new OrderItemDAO();
                        //----------------------------------------------------
                        // Active Order Item Statuses
                        List<Integer> activeItemStatuses = new ArrayList<Integer>(
                                Arrays.asList(
                                        ITEM_PENDING, ITEM_APPROVED, ITEM_RECEIVED,
                                        ITEM_RETURN_SCHEDULED, ITEM_OVERDUE,
                                        ITEM_OVERDUE_RETURN_SCHEDULED
                                ));
                        //----------------------------------------------------
                        // Get orders from member
                        orderDAO.getOrdersFromMember(ORDER_BOTH_METHOD, userDTO.getId(), NO_ORDER_STATUS_SPECIFIED);
                        List<OrderDTO> orders = orderDAO.getOrderList();
                        //----------------------------------------------------
                        // Get the number of active borrowed books from member
                        int memberTotalActiveBorrows = 0;
                        if (orders != null) {
                            for (OrderDTO order : orders) {
                                orderItemDAO.clearOrderItemList();
                                orderItemDAO.getOrderItemsFromOrderID(order.getId());
                                List<OrderItemDTO> orderItems = orderItemDAO.getOrderItemList();
                                if (orderItems != null) {
                                    for (OrderItemDTO orderItem : orderItems) {
                                        for (int activeItemStatus : activeItemStatuses) {
                                            if (orderItem.getLendStatus() == activeItemStatus) {
                                                // found an active item
                                                memberTotalActiveBorrows++;
                                                // check if this active item is current book being viewed
                                                if (orderItem.getBookID().equals(bookObj.getId())) {
                                                    request.setAttribute(ATTR_MEMBER_BOOK_BORROW_STATUS, STATUS_BORROWED);
                                                }
                                                break; //stop checking active status of current item
                                            }
                                        } // end traverse active statuses
                                        // check if this item is the current book is being reserved
                                        // (reserves can be in any orders)
                                        if ((orderItem.getBookID().equals(bookObj.getId()))
                                                && (orderItem.getLendStatus() == ITEM_RESERVED)) {
                                            request.setAttribute(ATTR_MEMBER_BOOK_BORROW_STATUS, STATUS_RESERVED);
                                        }
                                    } // end traverse items
                                }
                            } // end traverse orders
                        }
                        session.setAttribute(ATTR_MEMBER_TOTAL_ACTIVE_BORROWS, memberTotalActiveBorrows);
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
