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
    private final int ORDER_RESERVE_ONLY = 6;
    private final int ORDER_RETURN_SCHEDULED = 7;
    private final int ORDER_RETURN_RETURNED = 8;

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

    public void clearOrderList() {
        if (this.orderList != null) {
            this.orderList.clear();
        }
    }

    public int addOrder(String memberID, boolean lendMethod, int activeStatus)
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
                stm.setInt(3, activeStatus);
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

    public boolean updateOrder(int orderID, int activeStatus, boolean useInBatch)
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
                String sql = "UPDATE [Orders] " +
                        "SET [activeStatus] = ? " +
                        "WHERE [id] = ? ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, activeStatus);
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
    /**
     * @param lendMethod     0 to get direct orders;
     *                       1 to get delivery orders;
     *                       any other integers to get both type of orders;
     * @param activeStatuses a list of integers representing the order statuses; set null if not interested in
     *                       querying specific statuses;
     * @throws SQLException
     * @throws NamingException
     */
    public void viewOrders(int lendMethod, List<Integer> activeStatuses) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT [id], [memberID], [orderDate], [lendMethod], [activeStatus] " +
                        "FROM [Orders] ";
                switch (lendMethod) {
                    case 0:
                        sql += "WHERE [lendMethod] = 0 ";
                        break;
                    case 1:
                        sql += "WHERE [lendMethod] = 1 ";
                        break;
                    default:
                        sql += "WHERE 1 = 1 ";
                        break;
                }
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

    /**
     * @param lendMethod     0 to get direct orders;
     *                       1 to get delivery orders;
     *                       any other integers to get both type of orders;
     * @param activeStatuses a list of integers representing the order statuses; set null if not interested in
     *                       querying specific statuses;
     * @throws SQLException
     * @throws NamingException
     */
    public void getOrdersFromMember(int lendMethod, List<Integer> activeStatuses) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT [id], [memberID], [orderDate], [lendMethod], [activeStatus] " +
                        "FROM [Orders] ";
                switch (lendMethod) {
                    case 0:
                        sql += "WHERE [lendMethod] = 0 ";
                        break;
                    case 1:
                        sql += "WHERE [lendMethod] = 1 ";
                        break;
                    default:
                        sql += "WHERE 1 = 1 ";
                        break;
                }
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

    public OrderDTO getOrderFromID(int orderID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT [id], [memberID], [orderDate], [lendMethod], [activeStatus] " +
                        "FROM [Orders] " +
                        "WHERE [id] = ? ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, orderID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    OrderDTO order = new OrderDTO();
                    order.setId(rs.getInt("id"));
                    order.setMemberID(rs.getString("memberID"));
                    order.setOrderDate(rs.getDate("orderDate"));
                    order.setLendMethod(rs.getBoolean("lendMethod"));
                    order.setActiveStatus(rs.getInt("activeStatus"));
                    return order;
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return null;
    }
}
