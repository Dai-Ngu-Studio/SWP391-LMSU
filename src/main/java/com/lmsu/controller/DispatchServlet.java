package com.lmsu.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;

@WebServlet(name = "DispatchServlet", value = "/DispatchServlet")
public class DispatchServlet extends HttpServlet {

    public static final String LOGIN_PAGE = "login.html";
    public static final String LOGIN_CONTROLLER = "LoginServlet";
    public static final String LOGOUT_CONTROLLER = "LogoutServlet";
    public static final String STARTUP_CONTROLLER = "StartupServlet";
    public static final String UPDATE_CONTROLLER = "UpdateBookServlet";
    public static final String DELETE_CONTROLLER = "DeleteBookServlet";
    public static final String SEARCH_CONTROLLER = "SearchTitleServlet";
    public static final String ADD_CONTROLLER = "AddBookServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String button = request.getParameter("btAction");
        String url = LOGIN_PAGE;

        try {
            if (button == null) {
                url = STARTUP_CONTROLLER;
            } else if (button.equals("Login")) {
                url = LOGIN_CONTROLLER;
            } else if (button.equals("Logout")) {
                url = LOGOUT_CONTROLLER;
            } else if (button.equals("Update")) {
                url = UPDATE_CONTROLLER;
            } else if (button.equals("Delete Book")) {
                url = DELETE_CONTROLLER;
            } else if (button.equals("SearchBook")) {
                url = SEARCH_CONTROLLER;
            } else if (button.equals("AddBook")) {
                url = ADD_CONTROLLER;
            }
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            //response.sendRedirect(url);
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
