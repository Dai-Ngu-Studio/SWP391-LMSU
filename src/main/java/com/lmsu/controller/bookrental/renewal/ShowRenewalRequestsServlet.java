package com.lmsu.controller.bookrental.renewal;

import com.lmsu.bean.orderdata.OrderItemObj;
import com.lmsu.bean.renewal.RenewalRequestObj;
import com.lmsu.books.BookDAO;
import com.lmsu.books.BookDTO;
import com.lmsu.orderdata.orderitems.OrderItemDAO;
import com.lmsu.orderdata.orderitems.OrderItemDTO;
import com.lmsu.orderdata.orders.OrderDAO;
import com.lmsu.orderdata.orders.OrderDTO;
import com.lmsu.renewalrequests.RenewalRequestDAO;
import com.lmsu.renewalrequests.RenewalRequestDTO;
import com.lmsu.users.UserDAO;
import com.lmsu.users.UserDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
            List<RenewalRequestObj> renewalObjs = new ArrayList<>();
            if (renewals != null) {
                BookDAO bookDAO = new BookDAO();
                OrderDAO orderDAO = new OrderDAO();
                OrderItemDAO orderItemDAO = new OrderItemDAO();
                UserDAO userDAO = new UserDAO();
                for (RenewalRequestDTO renewal : renewals) {
                    RenewalRequestObj renewalRequestObj = new RenewalRequestObj();
                    renewalRequestObj.setRenewalID(renewal.getRenewalID());
                    renewalRequestObj.setReason(renewal.getReason());
                    renewalRequestObj.setRequestedExtendDate(renewal.getRequestedExtendDate());
                    renewalRequestObj.setApprovalStatus(renewal.getApprovalStatus());
                    OrderItemDTO orderItem = orderItemDAO.getOrderItemByID(renewal.getItemID());
                    OrderDTO order = orderDAO.getOrderFromID(orderItem.getOrderID());
                    OrderItemObj orderItemObj = new OrderItemObj();
                    UserDTO member = userDAO.getUserByID(order.getMemberID());
                    UserDTO librarian = userDAO.getUserByID(renewal.getLibrarianID());
                    BookDTO book = bookDAO.getBookById(orderItem.getBookID());
                    orderItemObj.setMemberID(order.getMemberID());
                    orderItemObj.setMemberName(member.getName());
                    orderItemObj.setBookID(orderItem.getBookID());
                    orderItemObj.setTitle(book.getTitle());
                    renewalRequestObj.setItem(orderItemObj);
                    if (librarian != null) {
                        renewalRequestObj.setLibrarian(librarian);
                    }
                    renewalObjs.add(renewalRequestObj);
                }
            }
            request.setAttribute(ATTR_RENEWAL_LIST, renewalObjs);
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
