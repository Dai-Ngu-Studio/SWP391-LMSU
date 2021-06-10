package com.lmsu.controller;

import com.lmsu.users.UserDAO;
import com.lmsu.users.UserDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ChangePhoneServlet", value = "/ChangePhoneServlet")
public class ChangePhoneServlet extends HttpServlet {

    private static final String RESULT_PAGE = "usersettings.jsp";
    static final Logger LOGGER = Logger.getLogger(ChangePasswordServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pk = request.getParameter("pk");
        String phone = request.getParameter("txtPhone");
        HttpSession session = request.getSession();

        String url = RESULT_PAGE;
        try {
            UserDAO dao = new UserDAO();
            UserDTO dto = (UserDTO) session.getAttribute("LOGIN_USER");
            if(session != null){
                if(!phone.equals("")){
                    boolean result = dao.updatePhone(pk, phone);
                    if(result){
                        dto.setPhoneNumber(phone);
                    }
                }
            }
            url = RESULT_PAGE;
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("ChangePasswordServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("ChangePasswordServlet _ Naming: " + ex.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
