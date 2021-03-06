package com.lmsu.controller.staff;

import com.lmsu.users.UserDAO;
import com.lmsu.users.UserDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ShowStaffServlet", value = "/ShowStaffServlet")
public class ShowStaffServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(ShowStaffServlet.class);
    private static final String STAFF_MANAGEMENT_PAGE = "staffmanagement.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String url = STAFF_MANAGEMENT_PAGE;

        try {
            List<UserDTO> searchResultReceived = (List<UserDTO>) request.getAttribute("SEARCH_RESULT");
            if (searchResultReceived != null) {
                request.setAttribute("STAFF_LIST", searchResultReceived);
            } else {
                UserDAO dao = new UserDAO();
                dao.viewStaffList();
                List<UserDTO> list = dao.getListAccount();
                request.setAttribute("STAFF_LIST", list);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            log("ShowStaffServlet _ SQL: " + e.getMessage());
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            log("ShowStaffServlet _ Naming: " + e.getMessage());
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
