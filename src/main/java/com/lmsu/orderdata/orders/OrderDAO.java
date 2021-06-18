package com.lmsu.orderdata.orders;

import com.lmsu.utils.DBHelpers;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class OrderDAO implements Serializable {

    private final int ORDER_CANCELLED = -1;
    private final int ORDER_PENDING = 0;
    private final int ORDER_APPROVED = 1;
    private final int ORDER_RECEIVED = 2;
    private final int ORDER_RETURNED = 3;
    private final int ORDER_OVERDUE = 4;
    private final int ORDER_REJECTED = 5;

    private Connection conn;
    private List<OrderDTO> orderList;

    public List<OrderDTO> getOrderList() {
        return this.orderList;
    }

    public OrderDAO() {
    }

    public OrderDAO(Connection conn) {
        this.conn = conn;
    }

    public int addOrder(String memberID, boolean lendMethod)
            throws SQLException, NamingException {
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            if (conn != null) {
                String sql = "INSERT INTO [Orders] " +
                        "([memberID], [orderDate], [lendMethod], [activeStatus]) " +
                        "VALUES(?, CURRENT_TIMESTAMP, ?, ?) ";
                stm = conn.prepareStatement(sql, new String[]{"id"});
                stm.setString(1, memberID);
                stm.setBoolean(2, lendMethod);
                stm.setInt(3, ORDER_PENDING);
                int row = stm.executeUpdate();
                if (row > 0) {
                    rs = stm.getGeneratedKeys();
                    if (rs.next()) {
                        int orderID = rs.getInt(1);
                        return orderID;
                    }
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
        }
        return -1;
    }

    public void viewOrders(boolean lendMethod, List<Integer> activeStatuses) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT [id], [memberID], [orderDate], [lendMethod], [activeStatus] " +
                        "FROM [Orders] " +
                        "WHERE [lendMethod] = ? ";
                if (activeStatuses != null) {
                    sql += " AND ( ";
                    ListIterator<Integer> statusItr = activeStatuses.listIterator();
                    while (statusItr.hasNext()) {
                        if (statusItr.hasPrevious()) {
                            sql += " OR ";
                        }
                        sql += " [activeStatus] = " + statusItr.next() + " ";
                    }
                    sql += " ) ";
                }
                stm = con.prepareStatement(sql);
                stm.setBoolean(1, lendMethod);
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (this.orderList == null) {
                        this.orderList = new ArrayList<>();
                    }
                    OrderDTO dto = new OrderDTO();
                    dto.setId(rs.getInt("id"));
                    dto.setMemberID(rs.getString("memberID"));
                    dto.setOrderDate(rs.getDate("orderDate"));
                    dto.setLendMethod(rs.getBoolean("lendMethod"));
                    dto.setActiveStatus(rs.getInt("activeStatus"));
                    this.orderList.add(dto);
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }
}
