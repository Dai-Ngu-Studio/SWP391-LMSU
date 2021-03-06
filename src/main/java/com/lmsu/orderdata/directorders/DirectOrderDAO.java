package com.lmsu.orderdata.directorders;

import com.lmsu.utils.DBHelpers;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.*;

public class DirectOrderDAO implements Serializable {
    //isReturnOrder - true: return to library; false: dỉrect method
    private Connection conn;

    public DirectOrderDAO() {
    }

    public DirectOrderDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean addDirectOrder(int orderID, Timestamp scheduledTime, boolean isReturnOrder)
            throws SQLException, NamingException {
        PreparedStatement stm = null;
        try {
            if (conn != null) {
                String sql = "INSERT INTO [DirectOrder] " +
                        "([orderID], [scheduledTime], [isReturnOrder]) " +
                        "VALUES(?, ?, ?) ";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, orderID);
                stm.setTimestamp(2, scheduledTime);
                stm.setBoolean(3, isReturnOrder);
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

    public DirectOrderDTO getDirectOrderFromOrderID(int orderID)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT [orderID], [librarianID], [scheduledTime], [isReturnOrder] " +
                        "FROM [DirectOrder] " +
                        "WHERE [orderID] = ? ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, orderID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    int orderIDVal = rs.getInt("orderID");
                    String librarianID = rs.getString("librarianID");
                    Timestamp scheduledTime = rs.getTimestamp("scheduledTime");
                    boolean isReturnOrder = rs.getBoolean("isReturnOrder");
                    DirectOrderDTO dto = new DirectOrderDTO();
                    dto.setOrderID(orderIDVal);
                    dto.setLibrarianID(librarianID);
                    dto.setScheduledTime(scheduledTime);
                    dto.setReturnOrder(isReturnOrder);
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

    public boolean updateLibrarianOfOrder(int orderID, String librarianID, boolean useInBatch)
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
                String sql = "UPDATE [DirectOrder] " +
                        "SET [librarianID] = ? " +
                        "WHERE [orderID] = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, librarianID);
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
}
