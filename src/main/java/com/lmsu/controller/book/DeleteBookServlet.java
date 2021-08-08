package com.lmsu.controller.book;

import com.lmsu.books.BookDAO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DeleteBookServlet", value = "/DeleteBookServlet")
public class DeleteBookServlet extends HttpServlet {
    private final String SEARCH_PAGE = "bookmanagement.jsp";
    private final String SEARCH_CONTROLLER = "SearchTitleServlet";
    private final String SHOW_BOOK_CONTROLLER="ShowBookServlet";
    private final String SEARCH_INVALID_BOOK_CONTROLLER = "SearchInvalidBook";

    static final Logger LOGGER = Logger.getLogger(DeleteBookServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String isShowInvalid = request.getParameter("showInvalidList") != null ? request.getParameter("showInvalidList") : "";

        String url = SEARCH_PAGE;

        String id = request.getParameter("pk");
        String searchVal = request.getParameter("txtSearchValue");
        try{
            BookDAO dao = new BookDAO();
            boolean result = dao.deleteBook(id);
            if (result){
                if (isShowInvalid.equals("True")) {
                    url = SEARCH_INVALID_BOOK_CONTROLLER;
                } else if (searchVal == null || searchVal.trim().isEmpty()) {
                    url = SHOW_BOOK_CONTROLLER;
                } else {
                    url = SEARCH_CONTROLLER;
                }
            }
//            if(result) {
//                url = "DispatchServlet" +
//                        "?btAction=SearchBook" +
//                        "&txtSearchValue=" + searchVal;
//            }
        } catch (SQLException ex){
            LOGGER.error(ex.getMessage());
            log("DeleteBookServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex){
            LOGGER.error(ex.getMessage());
            log("DeleteBookServlet _ Naming: " + ex.getMessage());
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
