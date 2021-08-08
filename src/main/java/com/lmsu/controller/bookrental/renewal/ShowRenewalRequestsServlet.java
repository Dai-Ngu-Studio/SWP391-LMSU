package com.lmsu.controller.bookrental.renewal;

import com.lmsu.books.BookDAO;
import com.lmsu.orderdata.orderitems.OrderItemDAO;
import com.lmsu.orderdata.orderitems.OrderItemDTO;
import com.lmsu.orderdata.orders.OrderDAO;
import com.lmsu.orderdata.orders.OrderDTO;
import com.lmsu.renewalrequests.RenewalRequestDAO;
import com.lmsu.renewalrequests.RenewalRequestDTO;
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
import java.util.List;

@WebServlet(name = "ShowRenewalRequestsServlet", value = "/ShowRenewalRequestsServlet")
public class ShowRenewalRequestsServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(ShowRenewalRequestsServlet.class);
    private final String RENEWAL_MANAGEMENT_PAGE = "renewalmanagement.jsp";

    private final int RENEWAL_CANCELLED = -1;
    private final int RENEWAL_PENDING = 0;
    private final int RENEWAL_APPROVED = 1;
    private final int RENEWAL_REJECTED = 2;

    private final String ATTR_RENEWAL_LIST = "RENEWAL_LIST";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = RENEWAL_MANAGEMENT_PAGE;

        try {
            RenewalRequestDAO renewalRequestDAO = new RenewalRequestDAO();
            renewalRequestDAO.viewRenewalRequests();
            List<RenewalRequestDTO> renewals = renewalRequestDAO.getRenewalList();
            if (renewals != null) {
                BookDAO bookDAO = new BookDAO();
                OrderDAO orderDAO = new OrderDAO();
                OrderItemDAO orderItemDAO = new OrderItemDAO();
                UserDAO userDAO = new UserDAO();
                for (RenewalRequestDTO renewal : renewals) {
                    OrderItemDTO orderItem = orderItemDAO.getOrderItemByID(renewal.getItemID());
                    OrderDTO order = orderDAO.getOrderFromID(orderItem.getOrderID());
                    order.setMember(userDAO.getUserByID(order.getMemberID()));
                    orderItem.setOrder(order);
                    orderItem.setBook(bookDAO.getBookById(orderItem.getBookID()));
                    renewal.setOrderItem(orderItem);
                    renewal.setLibrarian(userDAO.getUserByID(renewal.getLibrarianID()));
                }
            }
            request.setAttribute(ATTR_RENEWAL_LIST, renewals);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("ShowOrdersServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("ShowOrdersServlet _ Naming: " + ex.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
