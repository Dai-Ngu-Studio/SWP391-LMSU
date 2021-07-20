package com.lmsu.controller.member.cart;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.lmsu.bean.book.BookObj;
import com.lmsu.bean.member.CartObj;
import com.lmsu.bean.member.ReturnCartObj;
import com.lmsu.services.GhnApis;
import com.lmsu.users.UserDTO;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Date;
import java.util.Map;

@WebServlet(name = "ReviewOrderServlet", value = "/ReviewOrderServlet")
public class ReviewOrderServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(ReviewOrderServlet.class);
    private final String REVIEW_ORDER_PAGE = "revieworder.jsp";
    private final String REVIEW_RETURN_ORDER_PAGE = "reviewreturnorder.jsp";
    private final String CHECKOUT_PAGE = "orderform.jsp";

    private final String PARAM_BTACTION = "btAction";
    private final String PARAM_PICKUPDATE = "txtPickupDate";
    private final String PARAM_PICKUPTIME = "txtPickupTime";

    private final String PARAM_RECEIVERNAME = "txtReceiverName";
    private final String PARAM_PHONENUMBER = "txtPhoneNumber";
    private final String PARAM_ADDRESSONE = "txtAddressOne";
    private final String PARAM_ADDRESSTWO = "txtAddressTwo";
    private final String PARAM_CITY = "txtCity";
    private final String PARAM_DISTRICT = "txtDistrict";
    private final String PARAM_WARD = "txtWard";

    private final String BTACTION_DIRECT = "DirectOrder";
    private final String BTACTION_DELIVERY = "DeliveryOrder";
    private final String BTACTION_RETURN = "ReturnOrder";

    private final String ATTR_MEMBER_CART = "MEMBER_CART";
    private final String ATTR_RETURN_CART = "RETURN_CART";
    private final String ATTR_LOGIN_USER = "LOGIN_USER";
    private final String ATTR_CHECKOUT_METHOD = "CHECKOUT_METHOD";

    private final String ATTR_CHECKOUT_PICKUPDATE = "CHECKOUT_PICKUP_DATE";
    private final String ATTR_CHECKOUT_PICKUPTIME = "CHECKOUT_PICKUP_TIME";

    private final String ATTR_CHECKOUT_RECEIVERNAME = "CHECKOUT_RECEIVERNAME";
    private final String ATTR_CHECKOUT_PHONENUMBER = "CHECKOUT_PHONENUMBER";
    private final String ATTR_CHECKOUT_ADDRESSONE = "CHECKOUT_ADDRESSONE";
    private final String ATTR_CHECKOUT_ADDRESSTWO = "CHECKOUT_ADDRESSTWO";
    private final String ATTR_CHECKOUT_CITY = "CHECKOUT_CITY";
    private final String ATTR_CHECKOUT_DISTRICT = "CHECKOUT_DISTRICT";
    private final String ATTR_CHECKOUT_WARD = "CHECKOUT_WARD";
    private final String ATTR_CHECKOUT_EXPECTEDTIME = "CHECKOUT_EXPECTEDTIME";
    private final String ATTR_CHECKOUT_FEE = "CHECKOUT_FEE";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = CHECKOUT_PAGE;
        String btMethod = request.getParameter(PARAM_BTACTION);

        try {
            // 1. Check if session existed
            HttpSession session = request.getSession(false);
            if (session != null) {
                // 2. Check if cart existed
                boolean returnBook = false;
                Object tmp = session.getAttribute("RETURN_BOOK");
                if (tmp != null) {
                    returnBook = (boolean) tmp;
                }
                CartObj cartObj = (CartObj) session.getAttribute(ATTR_MEMBER_CART);
                ReturnCartObj returnObj = (ReturnCartObj) session.getAttribute(ATTR_RETURN_CART);
                if (returnBook) {
                    if (returnObj != null) {
                        // 3.1 Check if items existed
                        if (returnObj.getReturnItems() != null) {
                            if (btMethod.equals(BTACTION_RETURN)) {
                                String receiverName = request.getParameter(PARAM_RECEIVERNAME);
                                String phoneNumber = request.getParameter(PARAM_PHONENUMBER);
                                String deliveryAddressOne = request.getParameter(PARAM_ADDRESSONE);
                                String deliveryAddressTwo = request.getParameter(PARAM_ADDRESSTWO);
                                String city = request.getParameter(PARAM_CITY);
                                String district = request.getParameter(PARAM_DISTRICT);
                                String ward = request.getParameter(PARAM_WARD);
                                session.setAttribute(ATTR_CHECKOUT_RECEIVERNAME, receiverName);
                                session.setAttribute(ATTR_CHECKOUT_PHONENUMBER, phoneNumber);
                                session.setAttribute(ATTR_CHECKOUT_ADDRESSONE, deliveryAddressOne);
                                session.setAttribute(ATTR_CHECKOUT_ADDRESSTWO, deliveryAddressTwo);
                                session.setAttribute(ATTR_CHECKOUT_CITY, city);
                                session.setAttribute(ATTR_CHECKOUT_DISTRICT, district);
                                session.setAttribute(ATTR_CHECKOUT_WARD, ward);
                                session.setAttribute(ATTR_CHECKOUT_METHOD, true);
                                url = REVIEW_RETURN_ORDER_PAGE;
                                session.removeAttribute("RETURN_BOOK");
                            }
                        }
                    }
                } else {
                    if (cartObj != null) {
                        // 3.2 Check if items existed
                        if (cartObj.getItems() != null) {
                            //Map<String, BookObj> cartItems = cartObj.getItems();
                            UserDTO userDTO = (UserDTO) session.getAttribute(ATTR_LOGIN_USER);
                            if (userDTO != null) {
                                if (btMethod.equals(BTACTION_DIRECT)) {
                                    String txtPickupDate = request.getParameter(PARAM_PICKUPDATE);
                                    String txtPickupTime = request.getParameter(PARAM_PICKUPTIME);
                                    session.setAttribute(ATTR_CHECKOUT_PICKUPDATE, txtPickupDate);
                                    session.setAttribute(ATTR_CHECKOUT_PICKUPTIME, txtPickupTime);
                                    session.setAttribute(ATTR_CHECKOUT_METHOD, false);
                                    url = REVIEW_ORDER_PAGE;
                                } else if (btMethod.equals(BTACTION_DELIVERY)) {
                                    String receiverName = request.getParameter(PARAM_RECEIVERNAME);
                                    String phoneNumber = request.getParameter(PARAM_PHONENUMBER);
                                    String deliveryAddressOne = request.getParameter(PARAM_ADDRESSONE);
                                    String deliveryAddressTwo = request.getParameter(PARAM_ADDRESSTWO);
                                    String city = request.getParameter(PARAM_CITY);
                                    String district = request.getParameter(PARAM_DISTRICT);
                                    String ward = request.getParameter(PARAM_WARD);
                                    session.setAttribute(ATTR_CHECKOUT_RECEIVERNAME, receiverName);
                                    session.setAttribute(ATTR_CHECKOUT_PHONENUMBER, phoneNumber);
                                    session.setAttribute(ATTR_CHECKOUT_ADDRESSONE, deliveryAddressOne);
                                    session.setAttribute(ATTR_CHECKOUT_ADDRESSTWO, deliveryAddressTwo);
                                    session.setAttribute(ATTR_CHECKOUT_CITY, city);
                                    session.setAttribute(ATTR_CHECKOUT_DISTRICT, district);
                                    session.setAttribute(ATTR_CHECKOUT_WARD, ward);
                                    session.setAttribute(ATTR_CHECKOUT_METHOD, true);
                                    Date expectedDeliveryTime = GhnApis.calculateExpectedDeliveryTime(district, ward);
                                    String jsonFee = GhnApis.calculateFee(district, ward, cartObj.getCartQuantity());
                                    Object fee = new Gson().fromJson(jsonFee, Object.class);
                                    Object data = ((LinkedTreeMap) fee).get("data");
                                    double totalFee = (double) ((LinkedTreeMap) data).get("total");
                                    String txtTotalFee = String.valueOf(totalFee).split("\\.")[0];
                                    String reversed = new StringBuilder().append(txtTotalFee).reverse().toString();
                                    reversed = reversed.substring(0, 3) + "," + reversed.substring(3);
                                    txtTotalFee = new StringBuilder().append(reversed).reverse().toString();
                                    session.setAttribute(ATTR_CHECKOUT_EXPECTEDTIME, expectedDeliveryTime);
                                    session.setAttribute(ATTR_CHECKOUT_FEE, txtTotalFee);
                                    url = REVIEW_ORDER_PAGE;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            log("ReviewOrderServlet _ Exception: " + ex.getMessage());
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
