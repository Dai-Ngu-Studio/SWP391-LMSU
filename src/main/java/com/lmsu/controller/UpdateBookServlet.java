package com.lmsu.controller;

import com.lmsu.books.BookDAO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@WebServlet(name = "UpdateBookServlet", value = "/UpdateBookServlet")
public class UpdateBookServlet extends HttpServlet {
    private final String SEARCH_PAGE = "bookmanagement.jsp";
    //private final String SEARCH_CONTROLLER = "SearchTitleServlet";
    static final Logger LOGGER = Logger.getLogger(LoginServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        String url = SEARCH_PAGE;

        String title = request.getParameter("txtTitle");
        String authorID = request.getParameter("txtAuthorID");


        String SearchValue = request.getParameter("lastSearchVal");
        boolean check = false;

        try{
//
//            if(check){
//
//            }
//            else {
//                BookDAO dao = new BookDAO();
//                boolean result = dao.updateBook("",title,authorID,"","","",""
//                        ,BigDecimal.p 0,0,"", "");
//
//                if(result){
//                    url = "DispatchServlet" +
//                            "?btAction=Search" +
//                            "&txtSearchValue=" + SearchValue;
//                }
//            }
//
//        } catch (SQLException ex){
//            LOGGER.error(ex.getMessage());
//            log("UpdateBookServlet _ SQL: " + ex.getMessage());
//        } catch (NamingException ex){
//            LOGGER.error(ex.getMessage());
//            log("UpdateBookServlet _ Naming: " + ex.getMessage());
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
