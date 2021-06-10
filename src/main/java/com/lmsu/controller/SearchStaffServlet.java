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
import java.util.List;

@WebServlet(name = "SearchStaffServlet", value = "/SearchStaffServlet")
public class SearchStaffServlet extends HttpServlet {

    private static final String ERROR_PAGE = "error.jsp";
    private static final String SHOW_STAFF_CONTROLLER = "ShowStaffServlet";
    static final Logger LOGGER = Logger.getLogger(SearchStaffServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String searchValue = request.getParameter("txtSearchValue");
        String url = ERROR_PAGE;

        try {
            if (searchValue.trim().length() > 0) {
                UserDAO dao = new UserDAO();
                dao.searchMemberByName(searchValue);
                List<UserDTO> list = dao.getListAccount();
                if (!list.isEmpty()) {
                    request.setAttribute("SEARCH_RESULT", list);
                    url = SHOW_STAFF_CONTROLLER;
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            log("SearchStaffServlet _ SQL: " + e.getMessage());
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            log("SearchStaffServlet _ Naming: " + e.getMessage());
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
