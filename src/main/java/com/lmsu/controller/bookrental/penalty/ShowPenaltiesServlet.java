package com.lmsu.controller.bookrental.penalty;

import com.lmsu.books.BookDAO;
import com.lmsu.books.BookDTO;
import com.lmsu.orderdata.orderitems.OrderItemDAO;
import com.lmsu.orderdata.orderitems.OrderItemDTO;
import com.lmsu.orderdata.orders.OrderDAO;
import com.lmsu.orderdata.orders.OrderDTO;
import com.lmsu.users.UserDAO;
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

@WebServlet(name = "ShowPenaltiesServlet", value = "/ShowPenaltiesServlet")
public class ShowPenaltiesServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(ShowPenaltiesServlet.class);
    private final String SHOW_PENALTY_PAGE = "penaltymanagement.jsp";
    private final String ATTR_PENALTY_LIST = "PENALTY_LIST";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = SHOW_PENALTY_PAGE;
        try {
            OrderDAO orderDAO = new OrderDAO();
            OrderItemDAO orderItemDAO = new OrderItemDAO();
            BookDAO bookDAO = new BookDAO();
            UserDAO userDAO = new UserDAO();
            orderItemDAO.getPenalizedOrderItems();
            List<OrderItemDTO> penalizedItems = orderItemDAO.getOrderItemList();
            if (penalizedItems != null) {
                for (OrderItemDTO penalizedItem : penalizedItems) {
                    BookDTO book = bookDAO.getBookById(penalizedItem.getBookID());
                    OrderDTO order = orderDAO.getOrderFromID(penalizedItem.getOrderID());
                    order.setMember(userDAO.getUserByID(order.getMemberID()));
                    penalizedItem.setBook(book);
                    penalizedItem.setOrder(order);
                }
            }
            request.setAttribute(ATTR_PENALTY_LIST, penalizedItems);
            url = SHOW_PENALTY_PAGE;
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("ShowPenaltiesServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("ShowPenaltiesServlet _ Naming: " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        processRequest(request, response);
    }
}
