package com.lmsu.controller.member;

import com.lmsu.authors.AuthorDAO;
import com.lmsu.authors.AuthorDTO;
import com.lmsu.bean.member.AuthorObj;
import com.lmsu.bean.member.BookObj;
import com.lmsu.bean.member.CommentObj;
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
import java.util.List;

@WebServlet(name = "ViewBookDetailsServlet", value = "/ViewBookDetailsServlet")
public class ViewBookDetailsServlet extends HttpServlet {

    private static final String BOOK_CATALOG_CONTROLLER = "ShowBookCatalogServlet";
    private static final String BOOK_DETAILS_PAGE = "bookdetails.jsp";
    static final Logger LOGGER = Logger.getLogger(ViewBookDetailsServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

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
                // Create Beans
                AuthorObj authorObj = new AuthorObj(authorDTO.getAuthorID(),
                        authorDTO.getAuthorName(), authorDTO.getAuthorBio());
                // Subject name not yet implemented
                BookObj bookObj = new BookObj(bookDTO.getBookID(), bookDTO.getTitle(), authorDTO.getAuthorName(),
                        "TEMP", bookDTO.getPublisher(), bookDTO.getPublicationDate(),
                        bookDTO.getDescription(), bookDTO.getQuantity(), bookDTO.getAvgRating(),
                        bookDTO.getIsbnTen(), bookDTO.getIsbnThirteen(), bookDTO.getCoverPath());
                request.setAttribute("BOOK_OBJECT", bookObj);

                //Get orderItems
                if(session != null){
                    UserDTO user_dto = (UserDTO) session.getAttribute("LOGIN_USER");
                    OrderItemDAO orderItemDAO = new OrderItemDAO();
                    OrderItemDTO orderItemDTO = orderItemDAO.getItemsByBookID(bookID, user_dto.getId());
                    request.setAttribute("ORDER_ITEMS", orderItemDTO);
                }

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
                                        commentDTO.getBookID(), commentDTO.getTextComment(),
                                        commentDTO.getRating(), commentDTO.getEditorID(),
                                        editorName,
                                        commentDTO.isEdited());
                        commentObjList.add(commentObj);
                        numberOfComment++;
                    }
                    request.setAttribute("COMMENT_LIST", commentObjList);
                }
                request.setAttribute("COMMENT_AMOUNT", numberOfComment);
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
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
