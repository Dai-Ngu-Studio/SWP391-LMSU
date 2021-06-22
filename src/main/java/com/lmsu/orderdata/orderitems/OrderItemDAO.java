package com.lmsu.orderdata.orderitems;

import com.lmsu.utils.DBHelpers;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.*;
import java.time.LocalDate;
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
                        "([orderID], [memberID], [bookID], [lendStatus], [returnDeadline], [lendDate]) " +
                        "VALUES(?, ?, ?, ?, CURRENT_TIMESTAMP + 14, ?) ";
                stm = conn.prepareStatement(sql);
                for (OrderItemDTO orderItem : orderItems) {
                    stm.setInt(1, orderItem.getOrderID());
                    stm.setString(2, orderItem.getMemberID());
                    stm.setString(3, orderItem.getBookID());
                    stm.setInt(4, orderItem.getLendStatus());
                    stm.setDate(5, orderItem.getLendDate());
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
                String sql = "SELECT [id], [orderID], [memberID], [bookID], [lendStatus], [returnDeadline], [lendDate], [returnDate] " +
                        "FROM [OrderItems] " +
                        "WHERE [id] = ?";

                stm = con.prepareStatement(sql);
                stm.setInt(1, orderItemID);

                rs = stm.executeQuery();
                if (rs.next()) {
                    OrderItemDTO dto = new OrderItemDTO();
                    dto.setId(rs.getInt("id"));
                    dto.setOrderID(rs.getInt("orderID"));
                    dto.setMemberID(rs.getString("memberID"));
                    dto.setBookID(rs.getString("bookID"));
                    dto.setLendStatus(rs.getInt("lendStatus"));
                    dto.setReturnDeadline(rs.getDate("returnDeadline"));
                    dto.setLendDate(rs.getDate("lendDate"));
                    dto.setReturnDate(rs.getDate("returnDate"));
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

    public OrderItemDTO getMemberItemFromBookID(String bookID, String memberID) throws SQLException, NamingException {

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT [id], [orderID], [memberID], [bookID], [lendStatus], " +
                        "[returnDeadline], [lendDate], [returnDate] " +
                        "FROM [OrderItems] " +
                        "WHERE [bookID] = ? " +
                        "AND [memberID] = ? ";

                stm = con.prepareStatement(sql);
                stm.setString(1, bookID);
                stm.setString(2, memberID);

                rs = stm.executeQuery();
                if (rs.next()) {
                    OrderItemDTO dto = new OrderItemDTO();
                    dto.setId(rs.getInt("id"));
                    dto.setOrderID(rs.getInt("orderID"));
                    dto.setMemberID(rs.getString("memberID"));
                    dto.setBookID(rs.getString("bookID"));
                    dto.setLendStatus(rs.getInt("lendStatus"));
                    dto.setReturnDeadline(rs.getDate("returnDeadline"));
                    dto.setLendDate(rs.getDate("lendDate"));
                    dto.setReturnDate(rs.getDate("returnDate"));
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

    public void getOrderItemsFromMember(String memberID, List<Integer> lendStatuses)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT [id], [orderID], [memberID], [bookID], [lendStatus], " +
                        "[returnDeadline], [lendDate], [returnDate] " +
                        "FROM [OrderItems] " +
                        "WHERE [memberID] = ? ";
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
                stm.setString(1, memberID);
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (this.orderItemList == null) {
                        this.orderItemList = new ArrayList<>();
                    }
                    OrderItemDTO dto = new OrderItemDTO();
                    dto.setId(rs.getInt("id"));
                    dto.setOrderID(rs.getInt("orderID"));
                    dto.setMemberID(rs.getString("memberID"));
                    dto.setBookID(rs.getString("bookID"));
                    dto.setLendStatus(rs.getInt("lendStatus"));
                    dto.setReturnDeadline(rs.getDate("returnDeadline"));
                    dto.setLendDate(rs.getDate("lendDate"));
                    dto.setReturnDate(rs.getDate("returnDate"));
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
                String sql = "SELECT [id], [orderID], [memberID], [bookID], [lendStatus], " +
                        "[returnDeadline], [lendDate], [returnDate] " +
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
                    dto.setMemberID(rs.getString("memberID"));
                    dto.setBookID(rs.getString("bookID"));
                    dto.setLendStatus(rs.getInt("lendStatus"));
                    dto.setReturnDeadline(rs.getDate("returnDeadline"));
                    dto.setLendDate(rs.getDate("lendDate"));
                    dto.setReturnDate(rs.getDate("returnDate"));
                    this.orderItemList.add(dto);
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }
}
