package com.lmsu.orderdata.orderitems;

import com.lmsu.utils.DBHelpers;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO implements Serializable {

    private Connection conn;
    private List<OrderItemDTO> orderItemList;

    public List<OrderItemDTO> getOrderItemList() {
        return this.orderItemList;
    }

    public OrderItemDAO() {
    }

    public OrderItemDAO(Connection conn) {
        this.conn = conn;
    }

    public void viewOrderItems() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT OrderItems.id, [title], [orderID], [bookID], [lendStatus], [returnDeadline], [lendDate], [returnDate] " +
                        "FROM OrderItems, Books " +
                        "WHERE OrderItems.bookID = Books.id";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int orderID = rs.getInt("orderID");
                    String memberID = rs.getString("memberID");
                    String bookID = rs.getString("bookID");
                    int lendStatus = rs.getInt("lendStatus");
                    Date returnDeadline = rs.getDate("returnDeadline");
                    Date lendDate = rs.getDate("lendDate");
                    Date returnDate = rs.getDate("returnDate");
                    OrderItemDTO dto = new OrderItemDTO(id, orderID, memberID, bookID, lendStatus, returnDeadline, lendDate, returnDate);

                    if (this.orderItemList == null) {
                        this.orderItemList = new ArrayList<OrderItemDTO>();
                    }
                    this.orderItemList.add(dto);
                }
            }

        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }

    public boolean addOrderItems(List<OrderItemDTO> orderItems)
            throws SQLException, NamingException {
        PreparedStatement stm = null;
        try {
            if (conn != null) {
                String sql = "INSERT INTO [OrderItems] " +
                        "([orderID], [memberID], [bookID], [lendStatus], [returnDeadline]) " +
                        "VALUES(?, ?, ?, ?, ?) ";
                stm = conn.prepareStatement(sql);
                for (OrderItemDTO orderItem : orderItems) {
                    stm.setInt(1, orderItem.getOrderID());
                    stm.setString(2, orderItem.getMemberID());
                    stm.setString(3, orderItem.getBookID());
                    stm.setInt(4, orderItem.getLendStatus());
                    stm.setDate(5, orderItem.getReturnDeadline());
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
}
