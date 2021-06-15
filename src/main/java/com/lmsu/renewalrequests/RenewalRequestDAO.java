package com.lmsu.renewalrequests;

import com.lmsu.utils.DBHelpers;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.*;

public class RenewalRequestDAO implements Serializable {

    public boolean addRenewal(String id, String itemID, String librarianID, String reason, Date requestedExtendDate,
                              boolean approvalStatus) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelpers.makeConnection();
            if(con != null){
                String sql = "INSERT INTO " +
                        "RenewalRequests([id], [itemID], [librarianID], [reason], [requestedExtendDate], [approvalStatus]) " +
                        "VALUES(?, ?, ?, ?, ?, ?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, id);
                stm.setString(2, itemID);
                stm.setString(3, librarianID);
                stm.setString(4, reason);
                stm.setDate(5, requestedExtendDate);
                stm.setBoolean(6, approvalStatus);

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
}
