package com.lmsu.controller;

import com.lmsu.books.BookDAO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(name = "UpdateBookServlet", value = "/UpdateBookServlet")
public class UpdateBookServlet extends HttpServlet {
    private final String SEARCH_PAGE = "bookmanagement.jsp";

    //private final String SEARCH_CONTROLLER = "SearchTitleServlet";
    static final Logger LOGGER = Logger.getLogger(UpdateBookServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        String url = SEARCH_PAGE;

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

        String SearchValue = request.getParameter("lastSearchValue");
        boolean check = false;

        try{
            BigDecimal priceDecimal = new BigDecimal(price);
            int quantityNum = Integer.parseInt(quantity);
            BookDAO dao = new BookDAO();
            boolean result = dao.updateBook(bookID,title,authorID,subjectID,publisher,publishDate,description,priceDecimal,quantityNum,ISBN_ten,ISBN_thirteen);
            if(result){
                url = "DispatchServlet" +
                        "?btAction=SearchBook" +
                        "&txtSearchValue=" + SearchValue;
            }
        } catch (SQLException ex){
            LOGGER.error(ex.getMessage());
            log("UpdateBookServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex){
            LOGGER.error(ex.getMessage());
            log("UpdateBookServlet _ Naming: " + ex.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request,response);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }
}
