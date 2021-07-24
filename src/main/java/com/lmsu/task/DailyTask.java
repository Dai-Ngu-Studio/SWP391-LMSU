package com.lmsu.task;

import com.lmsu.announcement.AnnouncementDAO;
import com.lmsu.books.BookDAO;
import com.lmsu.books.BookDTO;
import com.lmsu.orderdata.directorders.DirectOrderDAO;
import com.lmsu.orderdata.directorders.DirectOrderDTO;
import com.lmsu.orderdata.orderitems.OrderItemDAO;
import com.lmsu.orderdata.orderitems.OrderItemDTO;
import com.lmsu.orderdata.orders.OrderDAO;
import com.lmsu.orderdata.orders.OrderDTO;
import com.lmsu.users.UserDAO;
import com.lmsu.utils.DateHelpers;
import com.lmsu.utils.EmailHelpers;
import org.apache.log4j.Logger;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.naming.NamingException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class DailyTask extends TimerTask implements Serializable {

    private static final String EMAIL = "***REMOVED***";
    private static final String PASSWORD = "***REMOVED***";
    static final Logger LOGGER = Logger.getLogger(DailyTask.class);

    public void notifyTenDaysLeft() {
        try {
            System.out.println("===================== Notify Ten Days Left ===================");
            UserDAO userDAO = new UserDAO();
            EmailHelpers emailHelpers = new EmailHelpers();
            AnnouncementDAO announcementDAO = new AnnouncementDAO();

            //get return date
            Date returnDate = announcementDAO.getReturnDate();

            if (returnDate != null) {
                //get today
                Date today = new Date(System.currentTimeMillis());

                //get days between today -> return date
                long daysBetween = ChronoUnit.DAYS.between(today.toLocalDate(), returnDate.toLocalDate());

                if (daysBetween == 10) {
                    Map<String, List<String>> map = userDAO.getUserWithBorrowedBooks();
                    if (map != null) {
                        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                            /**
                             * @user[0] id
                             * @user[1] name
                             * @user[2] email
                             */
                            String[] user = entry.getKey().split(", ");

                            System.out.println("SSLEmail Start");
                            Properties props = new Properties();
                            props.put("mail.smtp.host", "smtp.gmail.com");
                            props.put("mail.smtp.socketFactory.port", "465");
                            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                            props.put("mail.smtp.auth", "true");
                            props.put("mail.smtp.port", "465");

                            Authenticator auth = new Authenticator() {
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(EMAIL, PASSWORD);
                                }
                            };

                            Session session = Session.getDefaultInstance(props, auth);
                            System.out.println("Session created");

                            String subject = "[INFORMATION AND LIBRARY CENTER]_NOTIFY DUE DATE TO RETURN BOOKS";

                            String body = "Dear " + user[1] + "<br>"
                                    + "<br>"
                                    + "Information and Library Center remind you to return books before " + returnDate + "<br>"
                                    + "<br>"
                                    + String.join("<br>", entry.getValue()) + "<br>"
                                    + "<br>"
                                    + "If you believe there are any mistakes, please reply to this email.<br>"
                                    + "<br>"
                                    + "Thank you.<br>"
                                    + "Sincerely,<br>"
                                    + "Information and Library Center.";

                            emailHelpers.sendEmail(session, user[2], subject, body);
                        }//end for each user
                    }//end if exist list user with borrowed books
                }//end if days between = 10
            }//end if return date exist
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            System.err.println("DailyTask _ SQL: " + e.getMessage());
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            System.err.println("DailyTask _ Naming: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage());
            System.err.println("DailyTask _ UnsupportedEncoding: " + e.getMessage());
        } catch (MessagingException e) {
            LOGGER.error(e.getMessage());
            System.err.println("DailyTask _ Messaging: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            System.err.println("DailyTask _ Exception: " + e.getMessage() + " (Announcement have not initialize)");
        }
    }

    // Check for overdue, lost items
    public void changeStatusToOverdue() {
        final boolean CONNECTION_NO_BATCH = false;
        final int ORDER_OVERDUE = 4;
        final int ITEM_OVERDUE = 5;
        final int ITEM_LOST = 9;
        final int PENALTY_UNPAID = 1;
        final BigDecimal MONEY_PENALTY_BASE = new BigDecimal(5000);
        try {
            // Get current date
            Date currentDateSQL = DateHelpers.getCurrentDate();
            LocalDate currentDate = currentDateSQL.toLocalDate();
            // Instantiate DAOs
            BookDAO bookDAO = new BookDAO();
            OrderDAO orderDAO = new OrderDAO();
            OrderItemDAO orderItemDAO = new OrderItemDAO();
            // Get received and overdue items (items that are not returned or scheduled for return)
            orderItemDAO.clearOrderItemList();
            orderItemDAO.getReceivedAndOverdueOrderItems();
            List<OrderItemDTO> orderItems = orderItemDAO.getOrderItemList();
            // Check each item
            if (orderItems != null) {
                for (OrderItemDTO orderItem : orderItems) {
                    Date returnDeadlineSQL = orderItem.getReturnDeadline();
                    if (returnDeadlineSQL != null) {
                        int orderItemId = orderItem.getId();
                        LocalDate returnDeadline = returnDeadlineSQL.toLocalDate();
                        long daysBetween = ChronoUnit.DAYS.between(returnDeadline, currentDate);
                        // Mark item as lost, set order status to overdue, set penalty to full price
                        if (daysBetween >= 14) {
                            orderItemDAO.updateOrderItemStatus(orderItemId, ITEM_LOST, CONNECTION_NO_BATCH);
                            orderDAO.updateOrder(orderItem.getOrderID(), ORDER_OVERDUE, CONNECTION_NO_BATCH);
                            BookDTO book = bookDAO.getBookById(orderItem.getBookID());
                            if (book != null) {
                                orderItemDAO.updateOrderItemPenaltyStatus(orderItemId, PENALTY_UNPAID);
                                orderItemDAO.updateOrderItemPenaltyAmount(orderItemId, book.getPrice());
                            }
                        } else
                            // Mark item as overdue, set order status to overdue, add penalty for each day (5k/day)
                            if (daysBetween >= 1) {
                                orderItemDAO.updateOrderItemStatus(orderItemId, ITEM_OVERDUE, CONNECTION_NO_BATCH);
                                orderDAO.updateOrder(orderItemId, ORDER_OVERDUE, CONNECTION_NO_BATCH);
                                BigDecimal decimalDaysBetween = new BigDecimal(daysBetween);
                                BigDecimal penaltyAmount = decimalDaysBetween.multiply(MONEY_PENALTY_BASE);
                                orderItemDAO.updateOrderItemPenaltyStatus(orderItemId, PENALTY_UNPAID);
                                orderItemDAO.updateOrderItemPenaltyAmount(orderItemId, penaltyAmount);
                            } // more than X days
                    } // returnDeadline exists
                } // traverse orderItems
            } // orderItems exist
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            System.err.println("DailyTask _ changeStatusToOverdue _ SQL: " + e.getMessage());
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            System.err.println("DailyTask _ changeStatusToOverdue _ Naming: " + e.getMessage());
        }
    }

    // Check for direct orders not picked up at scheduled time after 1 day
    // Check for scheduled return that members didn't show up
    public void cancelDirectOrder() {
        final boolean CONNECTION_NO_BATCH = false;
        final int METHOD_DIRECT = 0;
        final int ORDER_CANCELLED = -1;
        final int ORDER_PENDING = 0;
        final int ORDER_RETURN_SCHEDULED = 7;
        final int ITEM_CANCELLED = -1;
        final int ITEM_RECEIVED = 2;
        final int ITEM_RETURN_SCHEDULED = 3;
        final int ITEM_OVERDUE = 5;
        final int ITEM_OVERDUE_RETURN_SCHEDULED = 6;
        try {
            // Get current date
            Date currentDateSQL = DateHelpers.getCurrentDate();
            LocalDate currentDate = currentDateSQL.toLocalDate();
            // Instantiate DAOs
            OrderDAO orderDAO = new OrderDAO();
            OrderItemDAO orderItemDAO = new OrderItemDAO();
            DirectOrderDAO directOrderDAO = new DirectOrderDAO();
            // Get pending direct orders
            orderDAO.clearOrderList();
            orderDAO.viewOrders(METHOD_DIRECT, new ArrayList<Integer>(Arrays.asList(ORDER_PENDING, ORDER_RETURN_SCHEDULED)));
            List<OrderDTO> orders = orderDAO.getOrderList();
            if (orders != null) {
                for (OrderDTO order : orders) {
                    int orderID = order.getId();
                    DirectOrderDTO directOrder = directOrderDAO.getDirectOrderFromOrderID(orderID);
                    if (directOrder != null) {
                        Timestamp scheduledTime = directOrder.getScheduledTime();
                        if (scheduledTime != null) {
                            LocalDate scheduledDate = scheduledTime.toLocalDateTime().toLocalDate();
                            long daysBetween = ChronoUnit.DAYS.between(scheduledDate, currentDate);
                            if (daysBetween >= 1) {
                                // For scheduled return, check the SCHEDULED ITEMS only (of returnOrderID)
                                // Revert the item status back to its previous state (before scheduling return)
                                if (directOrder.isReturnOrder()) {
                                    // The orderID is actually returnOrderID in this case
                                    orderItemDAO.clearOrderItemList();
                                    orderDAO.updateOrder(orderID, ORDER_CANCELLED, CONNECTION_NO_BATCH);
                                    orderItemDAO.getOrderItemsFromReturnOrderID(orderID);
                                    List<OrderItemDTO> orderItems = orderItemDAO.getOrderItemList();
                                    if (orderItems != null) {
                                        for (OrderItemDTO orderItem : orderItems) {
                                            int lendStatus = orderItem.getLendStatus();
                                            // Item was previously not overdue
                                            if (lendStatus == ITEM_RETURN_SCHEDULED) {
                                                orderItemDAO.updateOrderItemStatus(orderItem.getId(), ITEM_RECEIVED,
                                                        CONNECTION_NO_BATCH);
                                            } else
                                                // Item was previously overdue
                                                if (lendStatus == ITEM_OVERDUE_RETURN_SCHEDULED) {
                                                    orderItemDAO.updateOrderItemStatus(orderItem.getId(), ITEM_OVERDUE,
                                                            CONNECTION_NO_BATCH);
                                                }
                                        } // traverse orderItems
                                    } // orderItems exist
                                } else {
                                    // Cancel the order, cancel the items (of orderID)
                                    orderDAO.updateOrder(orderID, ORDER_CANCELLED, CONNECTION_NO_BATCH);
                                    orderItemDAO.updateOrderItemStatusOfOrder(orderID, ITEM_CANCELLED,
                                            CONNECTION_NO_BATCH);
                                }
                            } // more than 1 day
                        } // scheduledTime exist s
                    } // directOrder exists
                } // traverse orders
            } // orders exist
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            System.err.println("DailyTask _ cancelDirectOrder _ SQL: " + e.getMessage());
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            System.err.println("DailyTask _ cancelDirectOrder _ Naming: " + e.getMessage());
        }
    }

    // Check if any reserved books are now available in stock and notify users
    public void checkQuantityForReservedBooks() {
        // to-do
    }

    public void notifyPenalty() {
        try {
            System.out.println("===================== Notify Overdue =====================");
            UserDAO userDAO = new UserDAO();
            EmailHelpers emailHelpers = new EmailHelpers();
            AnnouncementDAO announcementDAO = new AnnouncementDAO();

            //get return date
            Date returnDate = announcementDAO.getReturnDate();

            if (returnDate != null) {
                //get today
                Date today = new Date(System.currentTimeMillis());

                //get days between today -> return date
                long daysBetween = ChronoUnit.DAYS.between(today.toLocalDate(), returnDate.toLocalDate());

                if (daysBetween == -1) {
                    Map<String, List<String>> map = userDAO.getUserOverdue();
                    if (map != null) {
                        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                            /**
                             * @user[0] id
                             * @user[1] name
                             * @user[2] email
                             */
                            String[] user = entry.getKey().split(", ");

                            System.out.println("SSLEmail Start");
                            Properties props = new Properties();
                            props.put("mail.smtp.host", "smtp.gmail.com");
                            props.put("mail.smtp.socketFactory.port", "465");
                            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                            props.put("mail.smtp.auth", "true");
                            props.put("mail.smtp.port", "465");

                            Authenticator auth = new Authenticator() {
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(EMAIL, PASSWORD);
                                }
                            };

                            Session session = Session.getDefaultInstance(props, auth);
                            System.out.println("Session created");

                            String subject = "[INFORMATION AND LIBRARY CENTER]_NOTIFY OVERDUE BORROW BOOKS AND PAY PENALTY";

                            String body = "Dear " + user[1] + "<br>"
                                    + "<br>"
                                    + "Information and Library Center are pleased that you had chosen our library service as a mean for learning resources." + "<br>"
                                    + "<br>"
                                    + "However, our system has detected that there are overdue books in your current book rental." +
                                    "We regrettably have to inform you that you have been penalized for the late return of those books." + "<br>"
                                    + "<br>"
                                    + "Please return the overdue books to our library and settle the penalty as soon as your earliest possible date." + "<br>"
                                    + "<br>"
                                    + String.join("<br>", entry.getValue()) + "<br>"
                                    + "<br>"
                                    + "If you believe there are any mistakes, please reply to this email.<br>"
                                    + "<br>"
                                    + "Thank you.<br>"
                                    + "Sincerely,<br>"
                                    + "Information and Library Center.";

                            emailHelpers.sendEmail(session, user[2], subject, body);
                        }//end for each user
                    }//end if exist list user with borrowed books
                }//end if days between = -1
            }//end if return date exist
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            System.err.println("DailyTask _ SQL: " + e.getMessage());
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            System.err.println("DailyTask _ Naming: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage());
            System.err.println("DailyTask _ UnsupportedEncoding: " + e.getMessage());
        } catch (MessagingException e) {
            LOGGER.error(e.getMessage());
            System.err.println("DailyTask _ Messaging: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            System.err.println("DailyTask _ Exception: " + e.getMessage() + " (Announcement have not initialize)");
        }
    }

    @Override
    public void run() {
        notifyTenDaysLeft();
        cancelDirectOrder();
        changeStatusToOverdue();
        notifyPenalty();
    }
}
