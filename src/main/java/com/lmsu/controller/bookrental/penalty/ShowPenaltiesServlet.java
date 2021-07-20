package com.lmsu.controller.bookrental.penalty;

import com.lmsu.bean.orderdata.OrderItemObj;
import com.lmsu.books.BookDAO;
import com.lmsu.books.BookDTO;
import com.lmsu.orderdata.orderitems.OrderItemDAO;
import com.lmsu.orderdata.orderitems.OrderItemDTO;
import com.lmsu.orderdata.orders.OrderDAO;
import com.lmsu.orderdata.orders.OrderDTO;
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

@WebServlet(name = "ShowPenaltiesServlet", value = "/ShowPenaltiesServlet")
public class ShowPenaltiesServlet extends HttpServlet {

    private final String SHOW_PENALTY_PAGE = "penaltymanagement.jsp";
    static final Logger LOGGER = Logger.getLogger(ShowPenaltiesServlet.class);

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
            List<OrderItemObj> penalizedItemObjs = new ArrayList<OrderItemObj>();
            if (penalizedItems!=null) {
                for (OrderItemDTO penalizedItem : penalizedItems) {
                    BookDTO book = bookDAO.getBookById(penalizedItem.getBookID());
                    OrderDTO order = orderDAO.getOrderFromID(penalizedItem.getOrderID());
                    UserDTO user = userDAO.getUserByID(order.getMemberID());
                    OrderItemObj penalizedItemObj = new OrderItemObj();

                    penalizedItemObj.setId(penalizedItem.getId());
                    penalizedItemObj.setOrderID(penalizedItem.getOrderID());
                    penalizedItemObj.setMemberID(user.getId());
                    penalizedItemObj.setMemberName(user.getName());
                    penalizedItemObj.setBookID(penalizedItem.getBookID());
                    penalizedItemObj.setTitle(book.getTitle());
                    penalizedItemObj.setLendStatus(penalizedItem.getLendStatus());
                    penalizedItemObj.setReturnDeadline(penalizedItem.getReturnDeadline());
                    penalizedItemObj.setLendDate(penalizedItem.getLendDate());
                    penalizedItemObj.setReturnDate(penalizedItem.getReturnDate());
                    penalizedItemObj.setPenaltyAmount(penalizedItem.getPenaltyAmount());
                    penalizedItemObj.setPenaltyStatus(penalizedItem.getPenaltyStatus());
                    penalizedItemObjs.add(penalizedItemObj);
                }
            }
            request.setAttribute(ATTR_PENALTY_LIST, penalizedItemObjs);
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
