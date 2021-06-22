package com.lmsu.controller.author;

import com.google.gson.Gson;
import com.lmsu.authors.AuthorDAO;
import com.lmsu.authors.AuthorDTO;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "AutoSuggestAuthorServlet", value = "/AutoSuggestAuthorServlet")
public class AutoSuggestAuthorServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        AuthorDAO dao = new AuthorDAO();

        try {
            //System.out.println("called");
            //System.out.println(request.getParameter("query"));
            String q = request.getParameter("query");
            ArrayList<AuthorDTO> o = dao.getListAuthor(q);

            //convert java object to JSON format, and returned as JSON formatted string
            Gson gson = new Gson();
            String json = gson.toJson(o);
            System.out.println(json);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
    }
    //code-fold
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
