package com.lmsu.renewalrequests;

import com.lmsu.orderdata.orderitems.OrderItemDTO;
import com.lmsu.utils.DBHelpers;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class RenewalRequestDAO implements Serializable {

    private final int RENEWAL_CANCELLED = -1;
    private final int RENEWAL_PENDING = 0;
    private final int RENEWAL_APPROVED = 1;
    private final int RENEWAL_REJECTED = 2;

    private List<RenewalRequestDTO> renewalList;

    public List<RenewalRequestDTO> getRenewalList() {
        return this.renewalList;
    }

    public boolean addRenewal(int itemID, String reason, String requestedExtendDate, int approvalStatus)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO " +
                        "RenewalRequests([itemID], [reason], [requestedExtendDate], [approvalStatus]) " +
                        "VALUES(?, ?, ?, ?)";
                stm = con.prepareStatement(sql);
                stm.setInt(1, itemID);
                stm.setString(2, reason);
                stm.setString(3, requestedExtendDate);
                stm.setInt(4, approvalStatus);

                int row = stm.executeUpdate();
                if (row > 0)
                    return true;
            }
        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    public RenewalRequestDTO getRenewalByID(int renewalID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT [id], [itemID], [librarianID], [reason], [requestedExtendDate], [approvalStatus] " +
                        "FROM [RenewalRequests] " +
                        "WHERE [id] LIKE ? ";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setInt(1, renewalID);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                if (rs.next()) {
                    RenewalRequestDTO dto = new RenewalRequestDTO();
                    dto.setRenewalID(rs.getInt("id"));
                    dto.setItemID(rs.getInt("itemID"));
                    dto.setLibrarianID(rs.getString("librarianID"));
                    dto.setReason(rs.getString("reason"));
                    dto.setRequestedExtendDate(rs.getDate("requestedExtendDate"));
                    dto.setApprovalStatus(rs.getInt("approvalStatus"));
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

    public RenewalRequestDTO getRenewalByItemID(int itemId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT [id], [itemID], [librarianID], [reason], [requestedExtendDate], [approvalStatus] " +
                        "FROM [RenewalRequests] " +
                        "WHERE [itemID] LIKE ? " +
                        "AND [approvalStatus] = 0";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setInt(1, itemId);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                if (rs.next()) {
                    RenewalRequestDTO dto = new RenewalRequestDTO();
                    dto.setRenewalID(rs.getInt("id"));
                    dto.setItemID(rs.getInt("itemID"));
                    dto.setLibrarianID(rs.getString("librarianID"));
                    dto.setReason(rs.getString("reason"));
                    dto.setRequestedExtendDate(rs.getDate("requestedExtendDate"));
                    dto.setApprovalStatus(rs.getInt("approvalStatus"));
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
                    int renewalID = rs.getInt("id");
                    int itemID = rs.getInt("itemID");
                    String librarianID = rs.getString("librarianID");
                    String reason = rs.getString("reason");
                    Date requestedExtendDate = rs.getDate("requestedExtendDate");
                    int approvalStatus = rs.getInt("approvalStatus");
                    RenewalRequestDTO dto = new RenewalRequestDTO();
                    dto.setRenewalID(renewalID);
                    dto.setItemID(itemID);
                    dto.setLibrarianID(librarianID);
                    dto.setReason(reason);
                    dto.setRequestedExtendDate(requestedExtendDate);
                    dto.setApprovalStatus(approvalStatus);
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

    public int countRenewalRequestByItemID(int itemID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT COUNT (*) AS countRenewal " +
                        "FROM [RenewalRequests] " +
                        "WHERE [itemID] = ? " +
                        "AND (([approvalStatus] = 0) OR ([approvalStatus] = 1)) ";

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

    public boolean updateRenewalRequestStatus(int renewalID, int approvalStatus) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "UPDATE [RenewalRequests] " +
                        "SET [approvalStatus] = ? " +
                        "WHERE [id] = ? ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, approvalStatus);
                stm.setInt(2, renewalID);
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    public boolean updateLibrarianOfRenewalRequest(int renewalID, String librarianID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "UPDATE [RenewalRequests] " +
                        "SET [librarianID] = ? " +
                        "WHERE [id] = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, librarianID);
                stm.setInt(2, renewalID);
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }
}
