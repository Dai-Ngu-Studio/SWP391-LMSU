package com.lmsu.orderdata.deliveryorders;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeliveryOrderDAO implements Serializable {
    //isReturnOrder - true: return to library; false: delivery method
    private Connection conn;

    public DeliveryOrderDAO() {
    }

    public DeliveryOrderDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean addDeliveryOrder(int orderID, String phoneNumber, String deliveryAddress1, String deliveryAddress2,
                                    String city, String district, String ward, boolean isReturnOrder)
            throws SQLException, NamingException {
        PreparedStatement stm = null;
        try {
            if (conn != null) {
                String sql = "INSERT INTO [DeliveryOrder] " +
                        "([orderID], [phoneNumber], [deliveryAddress1], [deliveryAddress2], " +
                        "[city], [district], [ward], [isReturnOrder]) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?) ";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, orderID);
                stm.setString(2, phoneNumber);
                stm.setString(3, deliveryAddress1);
                stm.setString(4, deliveryAddress2);
                stm.setString(5, city);
                stm.setString(6, district);
                stm.setString(7, ward);
                stm.setBoolean(8, isReturnOrder);
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
