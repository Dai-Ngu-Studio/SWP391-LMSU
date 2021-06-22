package com.lmsu.controller.member.cart;

import com.google.gson.Gson;
import com.lmsu.utils.DateHelpers;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "CheckoutDirectValidationServlet", value = "/CheckoutDirectValidationServlet")
public class CheckoutDirectValidationServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(CheckoutDirectValidationServlet.class);
    private final static String VIEW_CART_PAGE = "viewCart.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String txtPickupDate = request.getParameter("txtPickupDate");
        String txtPickupTime = request.getParameter("txtPickupTime");
        Map<String, Pair<String, String>> errors = new HashMap<>();
        // Map<Error, Pair<Input, Message>>

        try {

            if (txtPickupDate != null) {
                if (!txtPickupDate.isEmpty()) {
                    LocalDate currentDate = DateHelpers.getCurrentDate().toLocalDate();
                    LocalDate pickupDate = LocalDate.parse(txtPickupDate);
                    if (pickupDate.compareTo(currentDate) >= 0) {
                        LocalDate maximumDate = currentDate.plusDays(7);
                        if (pickupDate.compareTo(maximumDate) <= 0) {
                            if ((pickupDate.getDayOfWeek() == DayOfWeek.SATURDAY)
                                    || (pickupDate.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                                errors.put("errorPickupDate",
                                        new Pair<String, String>("txtPickupDate",
                                                "The library isn't open on Saturday or Sunday. " +
                                                        "Please choose a different date."));
                                errors.put("errorPickupTime",
                                        new Pair<String, String>("txtPickupTime",
                                                "Please choose a valid date."));
                            } else {
                                if (txtPickupTime != null) {
                                    if (!txtPickupTime.isEmpty()) {
                                        LocalTime pickupTime = LocalTime.parse(txtPickupTime);
                                        LocalTime currentTime = DateHelpers.getCurrentTime().toLocalTime();
                                        if (pickupDate.compareTo(currentDate) == 0) {
                                            if (pickupTime.compareTo(currentTime) < 0) {
                                                errors.put("errorPickupTime",
                                                        new Pair<String, String>("txtPickupTime",
                                                                "This time slot has expired. " +
                                                                        "Please choose a later time slot."));
                                            } // check if pickup time is before current time
                                        } // check if pickup date is same day as current date
                                    } // end if pickup time is chosen
                                }
                            }
                        } // end check if pickup date is within the 7 next days
                        else {
                            errors.put("errorPickupDate",
                                    new Pair<String, String>("txtPickupDate",
                                            "Your pick-up must be within 7 days from now."));
                            errors.put("errorPickupTime",
                                    new Pair<String, String>("txtPickupTime",
                                            "Please choose a valid date."));
                        } // pickup date is after the next 7 days
                    } // end check if pickup date before current date
                    else {
                        errors.put("errorPickupDate",
                                new Pair<String, String>("txtPickupDate",
                                        "Your pick-up must be within 7 days from now."));
                        errors.put("errorPickupTime",
                                new Pair<String, String>("txtPickupTime",
                                        "Please choose a valid date."));
                    } // pickup date is before current date
                } // end if pickup date is chosen
                else {
                    errors.put("errorPickupDate",
                            new Pair<String, String>("txtPickupDate",
                                    "Your pick-up must be within 7 days from now."));
                    errors.put("errorPickupTime",
                            new Pair<String, String>("txtPickupTime",
                                    "Please choose a valid date."));
                } // no pickup date chosen
            } // end if date param existed
            if (txtPickupTime != null) {
                if (txtPickupTime.isEmpty()) {
                    errors.put("errorPickupTime",
                            new Pair<String, String>("txtPickupTime",
                                    "Please choose a time slot to pick-up your books."));
                }
            } // end if time param existed
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("CheckoutDirectValidationServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("CheckoutDirectValidationServlet _ Naming: " + ex.getMessage());
        } finally {
            String json = new Gson().toJson(errors);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
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
