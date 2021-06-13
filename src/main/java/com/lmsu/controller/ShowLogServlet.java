package com.lmsu.controller;

import com.lmsu.books.BookDAO;
import com.lmsu.books.BookDTO;
import com.lmsu.importlog.ImportLogDAO;
import com.lmsu.importlog.ImportLogDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ShowLogServlet", value = "/ShowLogServlet")
public class ShowLogServlet extends HttpServlet {
    static final Logger LOGGER = Logger.getLogger(ShowLogServlet.class);
    private final String LOG_MANAGEMENT_PAGE = "logmanagement.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String url = LOG_MANAGEMENT_PAGE;
        try {
            List<ImportLogDTO> searchResultReceived = (List<ImportLogDTO>) request.getAttribute("SEARCH_RESULT");
            if (searchResultReceived != null) {
                request.setAttribute("LOG_LIST", searchResultReceived);
            } else {
                ImportLogDAO dao = new ImportLogDAO();
                dao.viewImportList();
                List<ImportLogDTO> result = dao.getImportList();

                request.setAttribute("LOG_LIST", result);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            log("ShowLogServlet _ SQL: " + e.getMessage());
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            log("ShowLogServlet _ Naming: " + e.getMessage());
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
