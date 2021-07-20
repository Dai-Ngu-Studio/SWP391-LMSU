package com.lmsu.orderdata.orderitems;

import com.lmsu.utils.DBHelpers;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class OrderItemDAO implements Serializable {

    private Connection conn;
    private List<OrderItemDTO> orderItemList;

    private final int ITEM_CANCELLED = -1;
    private final int ITEM_PENDING = 0;
    private final int ITEM_APPROVED = 1;
    private final int ITEM_RECEIVED = 2;
    private final int ITEM_RETURN_SCHEDULED = 3;
    private final int ITEM_RETURNED = 4;
    private final int ITEM_OVERDUE = 5;
    private final int ITEM_OVERDUE_RETURN_SCHEDULED = 6;
    private final int ITEM_OVERDUE_RETURNED = 7;
    private final int ITEM_REJECTED = 8;
    private final int ITEM_LOST = 9;
    private final int ITEM_RESERVED = 10;
    private final int ITEM_RESERVED_INACTIVE = 11;
    // book quantity become >0, member can now checkout the book -> need to set old ITEM_RESERVED to INACTIVE
    // because the old reserve status is no longer a valid fact, we shouldn't take it in account
    // remember to do this in CHECKOUT process by iterating over past orderItems of that book id

    private final int PENALTY_NONE = 0;
    private final int PENALTY_UNPAID = 1;
    private final int PENALTY_PAID = 2;

    private final int DAYS_TO_DEADLINE = 14;

    public List<OrderItemDTO> getOrderItemList() {
        return this.orderItemList;
    }

    public OrderItemDAO() {
    }

    public OrderItemDAO(Connection conn) {
        this.conn = conn;
    }

    public void clearOrderItemList() {
        if (this.orderItemList != null) {
            this.orderItemList.clear();
        }
    }

    public boolean addOrderItems(List<OrderItemDTO> orderItems)
            throws SQLException, NamingException {
        PreparedStatement stm = null;
        try {
            if (conn != null) {
                String sql = "INSERT INTO [OrderItems] " +
                        "([orderID], [bookID], [lendStatus], [returnDeadline], [lendDate], " +
                        "[penaltyAmount], [penaltyStatus]) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?) ";
                stm = conn.prepareStatement(sql);
                for (OrderItemDTO orderItem : orderItems) {
                    stm.setInt(1, orderItem.getOrderID());
                    stm.setString(2, orderItem.getBookID());
                    stm.setInt(3, orderItem.getLendStatus());
                    stm.setDate(4, orderItem.getReturnDeadline());
                    stm.setDate(5, orderItem.getLendDate());
                    stm.setBigDecimal(6, orderItem.getPenaltyAmount());
                    stm.setInt(7, orderItem.getPenaltyStatus());
                    stm.addBatch();
                    stm.clearParameters();
                }
                int[] rows = stm.executeBatch();
                boolean addFailed = false;
                for (int row : rows) {
                    if (row == 0) addFailed = true;
                }
                if (!addFailed) return true;
            }
        } finally {
            if (stm != null) stm.close();
        }
        return false;
    }

    public boolean returnBook(String orderItemID, int lendStatus, Date returnDate) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "UPDATE [OrderItems] " +
                        "SET [lendStatus] = ?, [returnDate] = ? " +
                        "WHERE [id] = ? ";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setInt(1, lendStatus);
                stm.setDate(2, returnDate);
                stm.setString(3, orderItemID);
                //4. Execute Query and get rows affected
                int rows = stm.executeUpdate();
                //5. Process result
                if (rows > 0) return true;
            }
        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    public OrderItemDTO getOrderItemByID(int orderItemID) throws SQLException, NamingException {

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT [id], [orderID], [bookID], [lendStatus], [returnDeadline], [lendDate], [returnDate], " +
                        "[penaltyAmount], [penaltyStatus] " +
                        "FROM [OrderItems] " +
                        "WHERE [id] = ?";

                stm = con.prepareStatement(sql);
                stm.setInt(1, orderItemID);

                rs = stm.executeQuery();
                if (rs.next()) {
                    OrderItemDTO dto = new OrderItemDTO();
                    dto.setId(rs.getInt("id"));
                    dto.setOrderID(rs.getInt("orderID"));
                    dto.setBookID(rs.getString("bookID"));
                    dto.setLendStatus(rs.getInt("lendStatus"));
                    dto.setReturnDeadline(rs.getDate("returnDeadline"));
                    dto.setLendDate(rs.getDate("lendDate"));
                    dto.setReturnDate(rs.getDate("returnDate"));
                    dto.setPenaltyAmount(rs.getBigDecimal("penaltyAmount"));
                    dto.setPenaltyStatus(rs.getInt("penaltyStatus"));
                    return dto;
                }
            }

        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return null;
    }

    public void getItemsFromBookID(String bookID, List<Integer> lendStatuses)
            throws SQLException, NamingException {

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT [id], [orderID], [bookID], [lendStatus], " +
                        "[returnDeadline], [lendDate], [returnDate], [penaltyAmount], [penaltyStatus] " +
                        "FROM [OrderItems] " +
                        "WHERE [bookID] = ? ";

                if (lendStatuses != null) {
                    sql += " AND ( ";
                    ListIterator<Integer> statusItr = lendStatuses.listIterator();
                    while (statusItr.hasNext()) {
                        if (statusItr.hasPrevious()) {
                            sql += " OR ";
                        }
                        sql += " [lendStatus] = " + statusItr.next() + " ";
                    }
                    sql += " ) ";
                }

                stm = con.prepareStatement(sql);
                stm.setString(1, bookID);
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (this.orderItemList == null) {
                        this.orderItemList = new ArrayList<OrderItemDTO>();
                    }
                    OrderItemDTO dto = new OrderItemDTO();
                    dto.setId(rs.getInt("id"));
                    dto.setOrderID(rs.getInt("orderID"));
                    dto.setBookID(rs.getString("bookID"));
                    dto.setLendStatus(rs.getInt("lendStatus"));
                    dto.setReturnDeadline(rs.getDate("returnDeadline"));
                    dto.setLendDate(rs.getDate("lendDate"));
                    dto.setReturnDate(rs.getDate("returnDate"));
                    dto.setPenaltyAmount(rs.getBigDecimal("penaltyAmount"));
                    dto.setPenaltyStatus(rs.getInt("penaltyStatus"));
                    this.orderItemList.add(dto);
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }

    public void getOrderItemsFromOrderID(int orderID)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT [id], [orderID], [bookID], [lendStatus], " +
                        "[returnDeadline], [lendDate], [returnDate], [penaltyAmount], [penaltyStatus] " +
                        "FROM [OrderItems] " +
                        "WHERE [orderID] = ? ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, orderID);
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (this.orderItemList == null) {
                        this.orderItemList = new ArrayList<>();
                    }
                    OrderItemDTO dto = new OrderItemDTO();
                    dto.setId(rs.getInt("id"));
                    dto.setOrderID(rs.getInt("orderID"));
                    dto.setBookID(rs.getString("bookID"));
                    dto.setLendStatus(rs.getInt("lendStatus"));
                    dto.setReturnDeadline(rs.getDate("returnDeadline"));
                    dto.setLendDate(rs.getDate("lendDate"));
                    dto.setReturnDate(rs.getDate("returnDate"));
                    dto.setPenaltyAmount(rs.getBigDecimal("penaltyAmount"));
                    dto.setPenaltyStatus(rs.getInt("penaltyStatus"));
                    this.orderItemList.add(dto);
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }

    /**
     * @param id         id of order item
     * @param lendStatus status of order item
     * @param useInBatch specify if a consistent connection is to be used instead,
     *                   the connection must be specified in the constructor beforehand
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public boolean updateOrderItemStatus(int id, int lendStatus, boolean useInBatch)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            if (useInBatch) {
                con = conn;
            } else {
                con = DBHelpers.makeConnection();
            }
            if (con != null) {
                String sql = "UPDATE [OrderItems] " +
                        "SET [lendStatus] = ? " +
                        "WHERE [id] = ? ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, lendStatus);
                stm.setInt(2, id);
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) stm.close();
            if (!useInBatch) {
                if (con != null) con.close();
            }
        }
        return false;
    }

    /**
     * Only use for approval, using for any other case will cause faulty logic
     *
     * @param orderID
     * @param lendStatus
     * @param useInBatch
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public boolean updateOrderItemStatusOfOrder(int orderID, int lendStatus, boolean useInBatch)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            if (useInBatch) {
                con = conn;
            } else {
                con = DBHelpers.makeConnection();
            }
            if (con != null) {
                String sql = "UPDATE [OrderItems] " +
                        "SET [lendStatus] = ? " +
                        "WHERE [orderID] = ? " +
                        "AND [lendStatus] != " + ITEM_RESERVED + " " +
                        "AND [lendStatus] != " + ITEM_RESERVED_INACTIVE + " ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, lendStatus);
                stm.setInt(2, orderID);
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) stm.close();
            if (!useInBatch) {
                if (con != null) con.close();
            }
        }
        return false;
    }

    public boolean updateOrderItemReturnOrderIDAndStatus(int id, int returnOrderID, int lendStatus)
            throws SQLException, NamingException {
        PreparedStatement stm = null;

        try {
            if (conn != null) {
                String sql = "UPDATE [OrderItems] " +
                        "SET [returnOrderID] = ?, " +
                        "[lendStatus] = ? " +
                        "WHERE [id] = ? ";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, returnOrderID);
                stm.setInt(2, lendStatus);
                stm.setInt(3, id);
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) stm.close();
        }
        return false;
    }

    /**
     * @param id              id of order item
     * @param date            a java.sql.Date type object
     * @param receiveOrReturn specify if the date parameter is a receive/ return date;
     *                        false: receive;
     *                        true: return;
     * @param useInBatch      specify if a consistent connection is to be used instead,
     *                        the connection must be specified in the constructor beforehand
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public boolean updateOrderItemDate(int id, Date date, boolean receiveOrReturn, boolean useInBatch)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            if (useInBatch) {
                con = conn;
            } else {
                con = DBHelpers.makeConnection();
            }
            if (con != null) {
                String sql = "UPDATE [OrderItems] ";
                if (!receiveOrReturn) {
                    sql += " SET [lendDate] = ? ";
                } else {
                    sql += " SET [returnDate] = ? ";
                }
                sql += " WHERE [id] = ? ";
                stm = con.prepareStatement(sql);
                stm.setDate(1, date);
                stm.setInt(2, id);
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) stm.close();
            if (!useInBatch) {
                if (con != null) con.close();
            }
        }
        return false;
    }

    public void getPenalizedOrderItems()
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT [id], [orderID], [bookID], [lendStatus], " +
                        "[returnDeadline], [lendDate], [returnDate], [penaltyAmount], [penaltyStatus] " +
                        "FROM [OrderItems] " +
                        "WHERE ([penaltyStatus] = " + PENALTY_UNPAID + ") " +
                        "OR ([penaltyStatus] = " + PENALTY_PAID + ") ";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (this.orderItemList == null) {
                        this.orderItemList = new ArrayList<>();
                    }
                    OrderItemDTO dto = new OrderItemDTO();
                    dto.setId(rs.getInt("id"));
                    dto.setOrderID(rs.getInt("orderID"));
                    dto.setBookID(rs.getString("bookID"));
                    dto.setLendStatus(rs.getInt("lendStatus"));
                    dto.setReturnDeadline(rs.getDate("returnDeadline"));
                    dto.setLendDate(rs.getDate("lendDate"));
                    dto.setReturnDate(rs.getDate("returnDate"));
                    dto.setPenaltyAmount(rs.getBigDecimal("penaltyAmount"));
                    dto.setPenaltyStatus(rs.getInt("penaltyStatus"));
                    this.orderItemList.add(dto);
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }

    public boolean updateOrderItemPenaltyStatus(int id, int penaltyStatus)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "UPDATE [OrderItems] " +
                        "SET [penaltyStatus] = ? " +
                        "WHERE [id] = ? ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, penaltyStatus);
                stm.setInt(2, id);
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (con != null) con.close();
            if (stm != null) stm.close();
        }
        return false;
    }

    public boolean updateOrderItemDeadline(int id, Date newDeadline)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "UPDATE [OrderItems] " +
                        "SET [returnDeadline] = ? " +
                        "WHERE [id] = ? ";
                stm = con.prepareStatement(sql);
                stm.setDate(1, newDeadline);
                stm.setInt(2, id);
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (con != null) con.close();
            if (stm != null) stm.close();
        }
        return false;
    }
}