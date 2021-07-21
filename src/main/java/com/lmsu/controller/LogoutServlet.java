package com.lmsu.controller;

import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import java.io.IOException;


@WebServlet(name = "LogoutServlet", value = "/LogoutServlet")
public class LogoutServlet extends HttpServlet {

    private static final String LOGIN_PAGE = "login.html";
    private static final String ERROR_PAGE = "error.html";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String url = ERROR_PAGE;

        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();

                url = "IndexServlet";
            }

//            Cookie[] cookies = request.getCookies();
//            if (cookies != null) {
//                String selector = "";
//
//                for (Cookie aCookie : cookies) {
//                    if (aCookie.getName().equals("selector")) {
//                        selector = aCookie.getValue();
//                    }
//                }
//
//                if (!selector.isEmpty()) {
//                    // delete token from database
//                    AuthencicateDAO dao = new AuthencicateDAO();
//                    AuthencicateDTO dto = authDao.findBySelector(selector);
//
//                    if (dto != null) {
//                        dao.removeCookie(token.getId());
//
//                        Cookie cookieSelector = new Cookie("selector", "");
//                        cookieSelector.setMaxAge(0);
//
//                        Cookie cookieValidator = new Cookie("validator", "");
//                        cookieValidator.setMaxAge(0);
//                        response.addCookie(cookieSelector);
//                        response.addCookie(cookieValidator);
//                    }
//                }
        } finally {
            response.sendRedirect(url);
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
