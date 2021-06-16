package com.lmsu.orderdata.orders;

import com.lmsu.utils.DBHelpers;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.*;
import java.util.List;

public class OrderDAO implements Serializable {

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

    public int addOrder(String memberID, Date orderDate, boolean lendMethod)
            throws SQLException, NamingException {
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            if (conn != null) {
                String sql = "INSERT INTO [Orders] " +
                        "([memberID], [orderDate], [lendMethod], [activeStatus]) " +
                        "VALUES(?, ?, ?, ?) ";
                stm = conn.prepareStatement(sql, new String[]{"id"});
                stm.setString(1, memberID);
                stm.setDate(2, orderDate);
                stm.setBoolean(3, lendMethod);
                stm.setBoolean(4, true);
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
}
