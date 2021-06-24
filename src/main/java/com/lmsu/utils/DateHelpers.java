package com.lmsu.utils;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class DateHelpers implements Serializable {

    public static java.sql.Date getCurrentDate() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT CAST(CURRENT_TIMESTAMP AS DATE) AS [currentDate] ";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return rs.getDate("currentDate");
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (rs != null) con.close();
        }
        return null;
    }

    public static java.sql.Time getCurrentTime() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT CAST(CURRENT_TIMESTAMP AS TIME) AS [currentTime] ";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return rs.getTime("currentTime");
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (rs != null) con.close();
        }
        return null;
    }

    public static java.sql.Date getDeadlineDate(java.sql.Date date, long days) {
        LocalDate locDate = date.toLocalDate();
        locDate = locDate.plusDays(days);
        return java.sql.Date.valueOf(locDate);
    }
}
