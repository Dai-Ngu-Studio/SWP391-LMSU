package com.lmsu.orderdata.directorders;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DirectOrderDAO implements Serializable {
    private Connection conn;

    public DirectOrderDAO() {
    }

    public DirectOrderDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean addDirectOrder(int orderID, Timestamp scheduledTime)
            throws SQLException, NamingException {
        PreparedStatement stm = null;
        try {
            if (conn != null) {
                String sql = "INSERT INTO [DirectOrder] " +
                        "([orderID], [scheduledTime]) " +
                        "VALUES(?, ?) ";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, orderID);
                stm.setTimestamp(2, scheduledTime);
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
}
