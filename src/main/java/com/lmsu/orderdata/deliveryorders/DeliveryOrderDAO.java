package com.lmsu.orderdata.deliveryorders;

import com.lmsu.utils.DBHelpers;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.*;

public class DeliveryOrderDAO implements Serializable {

    private Connection conn;

    public DeliveryOrderDAO() {
    }

    public DeliveryOrderDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean addDeliveryOrder(int orderID, String phoneNumber, String deliveryAddress1, String deliveryAddress2,
                                    String city, String district, String ward, String receiverName, String cityName, String districtName, String wardName)
            throws SQLException, NamingException {
        PreparedStatement stm = null;
        try {
            if (conn != null) {
                String sql = "INSERT INTO [DeliveryOrder] " +
                        "([orderID], [phoneNumber], [deliveryAddress1], [deliveryAddress2], " +
                        "[city], [district], [ward], [receiverName], [cityName], [districtName], [wardName]) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, orderID);
                stm.setString(2, phoneNumber);
                stm.setString(3, deliveryAddress1);
                stm.setString(4, deliveryAddress2);
                stm.setString(5, city);
                stm.setString(6, district);
                stm.setString(7, ward);
                stm.setString(8, receiverName);
                stm.setString(9, cityName);
                stm.setString(10, districtName);
                stm.setString(11, wardName);
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

    public DeliveryOrderDTO getDeliveryOrderFromOrderID(int orderID)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT [orderID], [managerID], [deliverer], [scheduledDeliveryTime], [receiverName], [phoneNumber]," +
                        "[deliveryAddress1], [deliveryAddress2], [city], [district], [ward], [cityName], [districtName], [wardName], " +
                        "[trackingCode] " +
                        "FROM [DeliveryOrder] " +
                        "WHERE [orderID] = ? ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, orderID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    int orderIDVal = rs.getInt("orderID");
                    String managerID = rs.getString("managerID");
                    String deliverer = rs.getString("deliverer");
                    Timestamp scheduledDeliveryTime = rs.getTimestamp("scheduledDeliveryTime");
                    String receiverName = rs.getString("receiverName");
                    String phoneNumber = rs.getString("phoneNumber");
                    String deliveryAddress1 = rs.getString("deliveryAddress1");
                    String deliveryAddress2 = rs.getString("deliveryAddress2");
                    String city = rs.getString("city");
                    String district = rs.getString("district");
                    String ward = rs.getString("ward");
                    String cityName = rs.getString("cityName");
                    String districtName = rs.getString("districtName");
                    String wardName = rs.getString("wardName");
                    String trackingCode = rs.getString("trackingCode");
                    DeliveryOrderDTO dto = new DeliveryOrderDTO();
                    dto.setOrderID(orderIDVal);
                    dto.setManagerID(managerID);
                    dto.setDeliverer(deliverer);
                    dto.setScheduledDeliveryTime(scheduledDeliveryTime);
                    dto.setReceiverName(receiverName);
                    dto.setPhoneNumber(phoneNumber);
                    dto.setDeliveryAddress1(deliveryAddress1);
                    dto.setDeliveryAddress2(deliveryAddress2);
                    dto.setCity(city);
                    dto.setDistrict(district);
                    dto.setWard(ward);
                    dto.setCityName(cityName);
                    dto.setDistrictName(districtName);
                    dto.setWardName(wardName);
                    dto.setTrackingCode(trackingCode);
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

    public boolean updateManagerOfOrder(int orderID, String managerID)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "UPDATE [DeliveryOrder] " +
                        "SET [managerID] = ? " +
                        "WHERE [orderID] = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, managerID);
                stm.setInt(2, orderID);
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

    public boolean updateTrackingCodeOfOrder(int orderID, String trackingCode)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "UPDATE [DeliveryOrder] " +
                        "SET [trackingCode] = ? " +
                        "WHERE [orderID] = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, trackingCode);
                stm.setInt(2, orderID);
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

    public boolean updateExpectedTimeOfOrder(int orderID, Date expectedTime)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "UPDATE [DeliveryOrder] " +
                        "SET [scheduledDeliveryTime] = ? " +
                        "WHERE [orderID] = ? ";
                stm = con.prepareStatement(sql);
                stm.setDate(1, expectedTime);
                stm.setInt(2, orderID);
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
