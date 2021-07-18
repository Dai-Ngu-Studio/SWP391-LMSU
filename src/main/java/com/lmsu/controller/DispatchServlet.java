package com.lmsu.controller;

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
    private static final String UPDATE_BOOK_CONTROLLER = "UpdateBookServlet";
    private static final String DELETE_BOOK_CONTROLLER = "DeleteBookServlet";
    private static final String SEARCH_BOOK_CONTROLLER = "SearchTitleServlet";
    private static final String ADD_BOOK_CONTROLLER = "AddBookServlet";
    private static final String SEARCH_CATALOG_CONTROLLER = "SearchBookCatalogServlet";
    private static final String SHOW_AUTHOR_BOOK_CONTROLLER = "ShowAuthorBookServlet";
    private static final String CHANGE_PASSWORD_CONTROLLER = "ChangePasswordServlet";
    private static final String CHANGE_PHONE_CONTROLLER = "ChangePhoneServlet";
    private static final String VIEW_BOOK_DETAILS_CONTROLLER = "ViewBookDetailsServlet";
    private static final String SEARCH_USER_CONTROLLER = "SearchUserServlet";
    private static final String ADD_USER_CONTROLLER = "AddUserServlet";
    private static final String UPDATE_USER_CONTROLLER = "";
    private static final String DELETE_USER_CONTROLLER = "DeleteUserServlet";
    private static final String SEARCH_STAFF_CONTROLLER = "SearchStaffServlet";
    private static final String ADD_STAFF_CONTROLLER = "";
    private static final String UPDATE_STAFF_CONTROLLER = "";
    private static final String DELETE_STAFF_CONTROLLER = "";
    private static final String UPDATE_AUTHOR_CONTROLLER = "UpdateAuthorServlet";
    private static final String DELETE_AUTHOR_CONTROLLER = "DeleteAuthorServlet";
    private static final String SEARCH_AUTHOR_CONTROLLER = "SearchAuthorNameServlet";
    private static final String ADD_AUTHOR_CONTROLLER = "AddAuthorServlet";
    private static final String RENEWAL_REQUEST_CONTROLLER = "RenewRequestServlet";
    private static final String UDPATE_USER_CONTROLLER = "UpdateUserServlet";

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
                url = UPDATE_BOOK_CONTROLLER;
            } else if (button.equals("Delete Book")) {
                url = DELETE_BOOK_CONTROLLER;
            } else if (button.equals("SearchBook")) {
                url = SEARCH_BOOK_CONTROLLER;
            } else if (button.equals("AddBook")) {
                url = ADD_BOOK_CONTROLLER;
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
            } else if (button.equals("Search User")) {
                url = SEARCH_USER_CONTROLLER;
            } else if (button.equals("Add User")) {
                url = ADD_USER_CONTROLLER;
            } else if (button.equals("Delete User")) {
                url = DELETE_USER_CONTROLLER;
            } else if (button.equals("Search Staff")) {
                url = SEARCH_STAFF_CONTROLLER;
            } else if (button.equals("Update Author")) {
                url = UPDATE_AUTHOR_CONTROLLER;
            } else if (button.equals("Delete Author")) {
                url = DELETE_AUTHOR_CONTROLLER;
            } else if (button.equals("SearchAuthor")) {
                url = SEARCH_AUTHOR_CONTROLLER;
            } else if (button.equals("AddAuthor")) {
                url = ADD_AUTHOR_CONTROLLER;
            } else if (button.equals("SearchStaff")) {
                url = SEARCH_STAFF_CONTROLLER;
            } else if (button.equals("Renew Book")) {
                url = RENEWAL_REQUEST_CONTROLLER;
            } else if (button.equals("Update User")) {
                url = UDPATE_USER_CONTROLLER;
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
