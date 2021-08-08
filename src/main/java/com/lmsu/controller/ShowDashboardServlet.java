package com.lmsu.controller;

import com.lmsu.books.BookDAO;
import com.lmsu.orderdata.orderitems.OrderItemDAO;
import com.lmsu.users.UserDAO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet(name = "ShowDashboardServlet", value = "/ShowDashboardServlet")
public class ShowDashboardServlet extends HttpServlet {
    private final String DASHBOARD_PAGE = "dashboard.jsp";
    static final Logger LOGGER = Logger.getLogger(ShowDashboardServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = DASHBOARD_PAGE;

        try {
            BookDAO bookDAO = new BookDAO();
            UserDAO userDAO = new UserDAO();
            OrderItemDAO orderItemDAO = new OrderItemDAO();
            request.setAttribute("TOTAL_BOOKS", bookDAO.getTotalBooksQuantity());
            request.setAttribute("TODAY_RETURNED", orderItemDAO.getTodayReturned());
            request.setAttribute("TODAY_BORROWED", orderItemDAO.getTodayBorrowed());
            request.setAttribute("TOTAL_ACTIVE_USER", userDAO.totalNumberOfUsers());
            Map<String, String> mapBorrowed = orderItemDAO.getBorrowedLastYear();
            Map<String, String> mapReturned = orderItemDAO.getReturnedLastYear();
            for (int i = 1; i <= 12; i++) {
                if (mapBorrowed.containsKey(Integer.toString(i)) == false) {
                    mapBorrowed.put(Integer.toString(i), "0");
                }
                if (mapReturned.containsKey(Integer.toString(i)) == false) {
                    mapReturned.put(Integer.toString(i), "0");
                }
            }
            request.setAttribute("MAP_BORROWED", mapBorrowed);
            request.setAttribute("MAP_RETURNED", mapReturned);

        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("ContactServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("ContactServlet _ Naming: " + ex.getMessage());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            log("ContactServlet _ Exception: " + ex.getMessage());
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
