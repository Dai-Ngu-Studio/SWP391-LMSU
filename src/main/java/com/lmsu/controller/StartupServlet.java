package com.lmsu.controller;

import com.lmsu.users.UserDAO;
import com.lmsu.users.UserDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "StartupServlet", value = "/StartupServlet")
public class StartupServlet extends HttpServlet {

    private static final String LOGIN_PAGE = "login.html";
    private static final String INDEX_PAGE = "index.jsp";
    private static final String DASHBOARD_PAGE = "dashboard.jsp";
    static final Logger LOGGER = Logger.getLogger(StartupServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String url = LOGIN_PAGE;

        try {
            //1. Check cookie existed
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                System.out.println("Cookies found.");
                //2. Get username and password
                for (Cookie cookie : cookies) {
                    String email = cookie.getName() + "@fpt.edu.vn";
                    String password = cookie.getValue();
                    //3. Check if username & password is correct
                    UserDAO dao = new UserDAO();
                    UserDTO dto = dao.checkLogin(email, password);
                    if (dto != null) {
                        HttpSession session = request.getSession(true);
                        session.setAttribute("LOGIN_USER", dto);
                        if (dto.getRoleID().equals("1") || dto.getRoleID().equals("2") || dto.getRoleID().equals("3")) {
                            url = DASHBOARD_PAGE;
                        }
                        if (dto.getRoleID().equals("4")) {
                            url = INDEX_PAGE;
                        }
                    }//end if user valid
                }//end for
            }//end if cookies existed
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("StartupServlet _ SQL: ", ex.getCause());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("StartupServlet _ Naming: ", ex.getCause());
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
