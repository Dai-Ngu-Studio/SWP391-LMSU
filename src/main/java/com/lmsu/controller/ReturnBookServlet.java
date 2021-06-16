package com.lmsu.controller;

import com.lmsu.orderdata.orderitems.OrderItemDAO;
import com.lmsu.orderdata.orderitems.OrderItemDTO;
import com.lmsu.renewalrequests.RenewalRequestDAO;
import com.lmsu.users.UserDTO;
import com.lmsu.utils.DateHelpers;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(name = "ReturnBookServlet", value = "/ReturnBookServlet")
public class ReturnBookServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(ReturnBookServlet.class);
    private static final String USER_SETTING_CONTROLLER = "ShowProfileServlet";
    private static final String USER_PAGE = "index.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = USER_PAGE;
        //String bookID = request.getParameter("bookPk");
        HttpSession session = request.getSession();
        String orderItemsID = request.getParameter("orderItemsPk");
        UserDTO user_dto = (UserDTO) session.getAttribute("LOGIN_USER");


        try {
            OrderItemDAO orderItems_dao = new OrderItemDAO();
            if(session != null){
                OrderItemDTO orderItem_dto = orderItems_dao.searchItemsByMemberID(user_dto.getId());
                if(orderItem_dto != null){
                    Date currentTime = DateHelpers.getCurrentDate();
                    //current time after deadline
                    if(currentTime.compareTo(orderItem_dto.getReturnDeadline()) > 0){
                        boolean result = orderItems_dao.returnBook(orderItemsID, 6);
                        if(result){
                            url = USER_SETTING_CONTROLLER;
                        }
                    }
                    //current time before deadline
                    else{
                        boolean result = orderItems_dao.returnBook(orderItemsID, 3);
                        if(result){
                            url = USER_SETTING_CONTROLLER;
                        }
                    }
                }
            }


        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("ReturnBookServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("ReturnBookServlet _ Naming: " + ex.getMessage());
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
