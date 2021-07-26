package com.lmsu.controller.usersettings;

import com.google.gson.Gson;
import com.lmsu.authors.AuthorDAO;
import com.lmsu.authors.AuthorDTO;
import com.lmsu.controller.member.cart.CheckoutDeliveryValidationServlet;
import com.lmsu.orderdata.orderitems.OrderItemDAO;
import com.lmsu.orderdata.orderitems.OrderItemDTO;
import com.lmsu.utils.DateHelpers;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "RenewalTimeValidationServlet", value = "/RenewalTimeValidationServlet")
public class RenewalTimeValidationServlet extends HttpServlet {

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        OrderItemDAO orderItemDAO = new OrderItemDAO();
        String extendDate = request.getParameter("txtExtendDate");


        try {
            String q = request.getParameter("orderItemID");
            OrderItemDTO orderItemDTO = orderItemDAO.getOrderItemByID(Integer.parseInt(q));
            LocalDate currentDate = LocalDate.parse(extendDate);
            LocalDate itemDeadline = orderItemDTO.getReturnDeadline().toLocalDate();

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            if (currentDate != null){
                if (currentDate.compareTo(itemDeadline) >= 0) {
                    if (ChronoUnit.DAYS.between(itemDeadline, currentDate) > 7) {
                        response.getWriter().write("invalid");
                    } else {
                        response.getWriter().write("true");
                    }
                }
                else {
                    response.getWriter().write("false");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
    }


}
