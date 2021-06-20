package com.lmsu.renewalrequests;

import com.lmsu.orderdata.orderitems.OrderItemDTO;
import com.lmsu.utils.DBHelpers;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

public class RenewalRequestDAO implements Serializable {
    private List<RenewalRequestDTO> renewalList;

    public List<RenewalRequestDTO> getRenewalList(){
        return this.renewalList;
    }

    public boolean addRenewal(String id, int itemID, String reason, String requestedExtendDate
    ) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelpers.makeConnection();
            if(con != null){
                String sql = "INSERT INTO " +
                        "RenewalRequests([id], [itemID], [reason], [requestedExtendDate]) " +
                        "VALUES(?, ?, ?, ?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, id);
                stm.setInt(2, itemID);
                //stm.setString(3, librarianID);
                stm.setString(3, reason);
                stm.setString(4, requestedExtendDate);
                //stm.setBoolean(6, approvalStatus);

                int row = stm.executeUpdate();
                if(row > 0)
                    return true;
            }

        } finally {
            if(stm != null) stm.close();
            if(con != null) con.close();
        }

        return false;
    }

    public boolean checkRenewalId(String renewalId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT [id] " +
                        "FROM [RenewalRequests] " +
                        "WHERE [id] LIKE ? ";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, renewalId);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                if (rs.next()) return true;
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    public void viewRenewalRequests()
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT [id], [itemID], [librarianID], [reason], " +
                        "[requestedExtendDate], [approvalStatus] " +
                        "FROM [RenewalRequests] ";

                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String renewalID = rs.getString("id");
                    int itemID = rs.getInt("itemID");
                    String librarianID = rs.getString("librarianID");
                    String reason = rs.getString("reason");
                    Date requestedExtendDate = rs.getDate("requestedExtendDate");
                    boolean approvalStatus = rs.getBoolean("approvalStatus");
                    RenewalRequestDTO dto = new RenewalRequestDTO(renewalID, itemID, librarianID,
                            reason, requestedExtendDate, approvalStatus);
                    if (this.renewalList == null) {
                        this.renewalList = new ArrayList<>();
                    }
                    this.renewalList.add(dto);
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }

    public int countRenewalRequestByItemID(int itemID) throws SQLException, NamingException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT COUNT (*) AS countRenewal " +
                        "FROM [RenewalRequests] " +
                        "WHERE itemID = ?";

                stm = con.prepareStatement(sql);
                stm.setInt(1, itemID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return rs.getInt("countRenewal");
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return -1;
    }
}
