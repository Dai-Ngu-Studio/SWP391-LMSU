package com.lmsu.controller;

import com.google.gson.internal.bind.util.ISO8601Utils;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;

@WebServlet(name = "DispatchServlet", value = "/DispatchServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class DispatchServlet extends HttpServlet {

    private static final String LOGIN_PAGE = "login.html";
    private static final String LOGIN_CONTROLLER = "LoginServlet";
    private static final String LOGOUT_CONTROLLER = "LogoutServlet";
    private static final String STARTUP_CONTROLLER = "StartupServlet";
    private static final String UPDATE_CONTROLLER = "UpdateBookServlet";
    private static final String DELETE_CONTROLLER = "DeleteBookServlet";
    private static final String SEARCH_CONTROLLER = "SearchTitleServlet";
    private static final String ADD_CONTROLLER = "AddBookServlet";
    private static final String SEARCH_CATALOG_CONTROLLER = "SearchBookCatalogServlet";
    private static final String SHOW_AUTHOR_BOOK_CONTROLLER = "ShowAuthorBookServlet";
    private static final String CHANGE_PASSWORD_CONTROLLER = "ChangePasswordServlet";
    private static final String CHANGE_PHONE_CONTROLLER = "ChangePhoneServlet";
    private static final String VIEW_BOOK_DETAILS_CONTROLLER = "ViewBookDetailsServlet";
    private static final String SEARCH_MEMBER_CONTROLLER = "SearchMemberServlet";

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
            } else if (button.equals("Update Book")) {
                url = UPDATE_CONTROLLER;
            } else if (button.equals("Delete Book")) {
                url = DELETE_CONTROLLER;
            } else if (button.equals("SearchBook")) {
                url = SEARCH_CONTROLLER;
            } else if (button.equals("AddBook")) {
                url = ADD_CONTROLLER;
            } else if (button.equals("SearchBookCatalog")) {
                url = SEARCH_CATALOG_CONTROLLER;
            } else if (button.equals("See More Books of This Author")) {
                url = SHOW_AUTHOR_BOOK_CONTROLLER;
            } else if (button.equals("Change Password")) {
                url = CHANGE_PASSWORD_CONTROLLER;
            } else if (button.equals("Change Phone Number")) {
                url = CHANGE_PHONE_CONTROLLER;
            } else if (button.equals("View Details")) {
                url = VIEW_BOOK_DETAILS_CONTROLLER;
            } else if (button.equals("SearchMember")){
                url = SEARCH_MEMBER_CONTROLLER;
            }
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
