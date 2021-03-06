package com.lmsu.controller.log;

import com.lmsu.importlog.ImportLogDAO;
import com.lmsu.importlog.ImportLogDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
                LinkedHashMap<Date, List<ImportLogDTO>> logMap = new LinkedHashMap<>();
                for (ImportLogDTO x : result) {
                    if (logMap.containsKey(x.getDateTaken())) {
                        logMap.get(x.getDateTaken()).add(x);
                    } else {
                        List<ImportLogDTO> newList = new ArrayList<>();
                        newList.add(x);
                        logMap.put(x.getDateTaken(), newList);
                    }

                }
                request.setAttribute("LOG_MAP_LIST", logMap);
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
