package com.lmsu.controller.usersettings;

import com.lmsu.orderdata.orderitems.OrderItemDAO;
import com.lmsu.orderdata.orderitems.OrderItemDTO;
import lombok.SneakyThrows;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
